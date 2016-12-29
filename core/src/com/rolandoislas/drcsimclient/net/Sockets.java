package com.rolandoislas.drcsimclient.net;

import com.rolandoislas.drcsimclient.data.Constants;

import java.io.IOException;
import java.net.*;

/**
 * Created by Rolando on 12/21/2016.
 */
public class Sockets {
	private String ip;
	public Socket socketVid;
	private DatagramSocket socketCmd;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void connect() {
		try {
			this.socketVid = new Socket(InetAddress.getByName(ip), Constants.PORT_SERVER_VID);
			this.socketCmd = new DatagramSocket();
			sendCommand(Constants.COMMAND_REGISTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendCommand(String name) {
		sendCommand(name, "");
	}

	public void sendCommand(String name, String data) {
		byte[] payload = Codec.encodeCommand(name, data);
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(payload, payload.length, InetAddress.getByName(ip), Constants.PORT_SERVER_CMD);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			this.socketCmd.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
