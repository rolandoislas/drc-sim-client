package com.rolandoislas.drcsimclient.net;

import com.google.common.primitives.Bytes;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 12/21/2016.
 */
public class NetUtil {
	private static HashMap<String, byte[]> buffers = new HashMap<String, byte[]>();
	private static HashMap<String, Long> timestamps = new HashMap<String, Long>();
	private static boolean pingSent = false;

	public static byte[] recv(Socket socket, String bufferId) throws IOException {
		try {
			return _recv(socket, bufferId);
		} catch (NullPointerException ignore) {
			throw new IOException("Disconnected");
		}
	}

	private static byte[] _recv(Socket socket, String bufferId) throws IOException {
		BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
		if (!buffers.containsKey(bufferId))
			buffers.put(bufferId, new byte[0]);
		if (!timestamps.containsKey(bufferId))
			timestamps.put(bufferId, System.currentTimeMillis());
		while (!new String(buffers.get(bufferId)).contains(Codec.endDelimiter)) {
			// Disconnected
			long time = System.currentTimeMillis() - timestamps.get(bufferId);
			if (time >= 10000) {
				Logger.debug("Disconnected from server");
				clear();
				throw new IOException("Disconnected");
			}
			if (time >= 5000 && !pingSent) {
				Logger.debug("Sending PING command to server");
				sockets.sendCommand(Constants.COMMAND_PING);
				Client.connect(sockets.getIp());
				pingSent = true;
			}
			// Timeout
			if (inStream.available() < 2)
				throw new IOException("Read timeout");
			timestamps.put(bufferId, System.currentTimeMillis());
			// Read
			byte[] read = new byte[100000];
			int numRead = inStream.read(read);
			read = Arrays.copyOfRange(read, 0, numRead);
			// Combine saved and new bytes
			byte[] newBytes = new byte[buffers.get(bufferId).length + read.length];
			System.arraycopy(buffers.get(bufferId), 0, newBytes, 0, buffers.get(bufferId).length);
			System.arraycopy(read, 0, newBytes, buffers.get(bufferId).length, read.length);
			// Save
			buffers.put(bufferId, newBytes);
		}
		int index = Bytes.indexOf(buffers.get(bufferId), Codec.endDelimiter.getBytes());
		byte[] packet = Arrays.copyOfRange(buffers.get(bufferId), 0, index);
		buffers.put(bufferId, Arrays.copyOfRange(buffers.get(bufferId), index + Codec.endDelimiter.length(),
				buffers.get(bufferId).length));
		return Codec.decode(packet);
	}

	static void clear() {
		timestamps.clear();
		buffers.clear();
		pingSent = false;
	}

	public static void resetTimeout() {
		timestamps.clear();
		pingSent = false;
	}
}
