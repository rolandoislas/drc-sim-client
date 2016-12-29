package com.rolandoislas.drcsimclient.net;

import com.badlogic.gdx.Gdx;
import com.google.common.primitives.Bytes;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Rolando on 12/21/2016.
 */
public class NetUtil {
	private static HashMap<String, byte[]> buffers = new HashMap<String, byte[]>();

	public static byte[] recv(Socket socket, String bufferId) throws IOException {
		BufferedInputStream inStream = new BufferedInputStream(socket.getInputStream());
		if (!buffers.containsKey(bufferId))
			buffers.put(bufferId, new byte[0]);
		while (!new String(buffers.get(bufferId)).contains(Codec.endDelimiter)) {
			// Timeout
			if (inStream.available() < 2)
				throw new IOException("Read timeout");
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

	public static void clearBuffer(String bufferId) {
		buffers.remove(bufferId);
	}
}
