package com.rolandoislas.drcsimclient.net;

import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.util.Arrays;

/**
 * Created by Rolando on 12/21/2016.
 */
public class Codec {
	static String endDelimiter = "ewaffle";
	private static String startDelimiter = "swaffle";
	private static String commandDelimiter = "cwaffle";

	public static byte[] decode(byte[] packet) {
		if (new String(packet).contains(startDelimiter))
			packet = Arrays.copyOfRange(packet, startDelimiter.length(), packet.length);
		if (new String(packet).contains(endDelimiter))
			packet = Arrays.copyOfRange(packet, 0, packet.length - endDelimiter.length());
		return packet;
	}

	public static byte[] encodeCommand(String name, String data) {
		String encodedString = name + commandDelimiter + data;
		return encodedString.getBytes();
	}

	public static String[] decodeCommand(DatagramPacket packet) {
		return new String(packet.getData(), 0, packet.getLength()).split(commandDelimiter);
	}

	public static String encodeInput(Object o) {
		Object[] data = new Object[]{o, 0};
		Gson gson = new Gson();
		return gson.toJson(data);
	}
}
