package com.rolandoislas.drcsimclient.net;

import com.google.common.primitives.Bytes;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Rolando on 12/21/2016.
 */
public class NetUtil {
	private byte[] buffer = new byte[0];
	private long timestamp = 0;
	private String packetDelimiter = "||||\n";
	private static final long TIMEOUT = 1000;

	public byte[] recv(Socket socket) throws DisconnectedException, ReadTimeoutException {
		try {
			BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
			if (timestamp == 0)
				timestamp = System.currentTimeMillis();
			while (!new String(buffer).contains(packetDelimiter)) {
				// Disconnected
				long time = System.currentTimeMillis() - timestamp;
				if (time >= TIMEOUT) {
					clear();
					throw new DisconnectedException();
				}
				// Timeout
				if (inStream.available() < 2)
					throw new ReadTimeoutException();
				timestamp = System.currentTimeMillis();
				// Read
				byte[] read = new byte[100000];
				int numRead = inStream.read(read);
				read = Arrays.copyOfRange(read, 0, numRead);
				// Combine saved and new bytes
				byte[] newBytes = new byte[buffer.length + read.length];
				System.arraycopy(buffer, 0, newBytes, 0, buffer.length);
				System.arraycopy(read, 0, newBytes, buffer.length, read.length);
				// Save
				buffer = newBytes;
			}
			int index = Bytes.indexOf(buffer, packetDelimiter.getBytes());
			byte[] packet = Arrays.copyOfRange(buffer, 0, index);
			buffer = Arrays.copyOfRange(buffer, index + packetDelimiter.length(), buffer.length);
			return decode(packet);
		}
		catch (IOException e) {
			Logger.exception(e);
			clear();
			throw new DisconnectedException();
		}
	}

	private byte[] decode(byte[] packet) {
		if (new String(packet).contains(packetDelimiter))
			packet = Arrays.copyOfRange(packet, 0, packet.length - packetDelimiter.length());
		return packet;
	}

	private void clear() {
		timestamp = 0;
		buffer = new byte[0];
	}

	public void resetTimeout() {
		timestamp = 0;
	}

	public static class DisconnectedException extends Exception {
	}

	public static class ReadTimeoutException extends Exception {
	}
}
