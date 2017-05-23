package com.rolandoislas.drcsimclient.net;

import com.google.gson.Gson;

import java.net.DatagramPacket;
import java.util.Arrays;

/**
 * Created by Rolando on 12/21/2016.
 */
public class Codec {
	static String packetDelimiter = "||||\n";
	private static String commandDelimiter = "cwaffle";

	public static byte[] decode(byte[] packet) {
		String startDelimiter = "swaffle";
		if (new String(packet).contains(startDelimiter))
			packet = Arrays.copyOfRange(packet, startDelimiter.length(), packet.length);
		if (new String(packet).contains(packetDelimiter))
			packet = Arrays.copyOfRange(packet, 0, packet.length - packetDelimiter.length());
		return packet;
	}

	public static byte[] encodeCommand(String name, String data) {
		String encodedString = name + commandDelimiter + data;
		return encodedString.getBytes();
	}

	/**
	 * Parses a command packet
	 * @param packet command packet
	 * @return Returns a string array with the command as the first entry and extra data as the second.
	 * The second entry will be an empty string on no extra data.
	 * Both entries will be empty strings if the packet starts with null data.
	 */
	public static String[] decodeCommand(DatagramPacket packet) {
		if (packet.getData()[0] == 0x0)
			return new String[]{"", ""};
		String[] command = new String(packet.getData(), 0, packet.getLength()).split(commandDelimiter);
		if (command.length == 1)
			command = new String[]{command[0], ""};
		return command;
	}

	public static String encodeInput(Object o) {
		Object[] data = new Object[]{o, 0};
		Gson gson = new Gson();
		return gson.toJson(data);
	}
}
