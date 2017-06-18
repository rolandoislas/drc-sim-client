package com.rolandoislas.drcsimclient.net;

import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.packet.CommandPacket;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.IOException;
import java.net.*;

/**
 * Created by Rolando on 12/21/2016.
 */
public class Sockets {
	private String ip;
	public Socket socketVid;
	public DatagramSocket socketCmd;
	public Socket socketAud;

	/**
	 * Sets the ip that will be used for connections to a server.
	 * @param ip server ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Connect all sockets.
	 * @throws Exception Exception with user error message on failure
	 */
	public void connect() throws Exception {
		try {
			this.socketVid = new Socket();
			this.socketVid.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_VID), 5000);
			this.socketAud = new Socket();
			this.socketAud.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_AUD), 5000);
			this.socketCmd = new DatagramSocket();
			this.socketCmd.setSoTimeout(1);
			sendCommand(Constants.COMMAND_REGISTER);
		}
		catch (UnknownHostException e) {
			throw new Exception("Unknown host.");
		}
		catch (ConnectException e) {
			throw new Exception("Could not connect to host.");
		}
		catch (IOException e) {
			Logger.exception(e);
			throw e;
		}
	}

	/**
	 * Attempts to reconnect to the server video socket. Logs exceptions.
	 */
	public void reconnectVideo() {
		try {
			this.socketVid = new Socket();
			this.socketVid.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_VID), 5000);
		}
		catch (Exception e) {
			Logger.exception(e);
		}
	}

	/**
	 * Attempts to reconnect to the server audio socket. Logs exceptions.
	 */
	public void reconnectAudio() {
		try {
			this.socketAud = new Socket();
			this.socketAud.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_AUD), 5000);
		}
		catch (Exception e) {
			Logger.exception(e);
		}
	}

	/**
	 * Send a header-only packet.
	 * @param id command id/type
	 */
	public void sendCommand(short id) {
		sendCommand(CommandPacket.create(id));
	}

	/**
	 * Send a raw byte packet.
	 * @param payload packet data
	 */
	public void sendCommand(byte[] payload) {
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(payload, payload.length, InetAddress.getByName(ip), Constants.PORT_SERVER_CMD);
		} catch (UnknownHostException e) {
			Logger.exception(e);
		}
		try {
			this.socketCmd.send(packet);
		} catch (IOException e) {
			Logger.exception(e);
		}
	}

	/**
	 * Send button input
	 * @param buttonBits int with button flags
	 */
	public void sendButtonInput(short buttonBits) {
		if (buttonBits == 0)
			return;
		sendCommand(CommandPacket.create_button(buttonBits));
	}

	public void sendJoystickInput(float knobPercentX, float knobPercentY) {
		sendJoystickInput(new float[] {knobPercentX, knobPercentY, 0, 0});
	}

	/**
	 * Send joystick input
	 * @param axes [left x, left y, right x, right y]
	 */
	public void sendJoystickInput(float[] axes) {
		boolean found = false;
		for (int axis = 0; axis < axes.length; axis++) {
			axes[axis] *= 100;
			if (axes[axis] < 0) {
				axes[axis] *= -1;
				if (axes[axis] > 20)
					found = true;
			}
			else if (axes[axis] > 0) {
				axes[axis] += 100;
				if (axes[axis] > 120)
					found = true;
			}
		}
		if (!found)
			return;
		sendCommand(CommandPacket.create_joystick((short) axes[0], (short) axes[1], (short) axes[2], (short) axes[3]));
	}

	/**
	 * Send touch screen input
	 * @param x x
	 * @param y y
	 */
	public void sendTouchScreenInput(short x, short y, short width, short height) {
		sendCommand(CommandPacket.create_touch(x, y, width, height));
	}

	public void dispose() {
		if (socketCmd != null && !socketCmd.isClosed()) {
			socketCmd.disconnect();
			socketCmd.close();
		}
		if (socketVid != null && !socketVid.isClosed()) {
			try {
				socketVid.close();
			} catch (IOException e) {
				Logger.exception(e);
			}
		}
		if (socketAud != null && !socketAud.isClosed()) {
			try {
				socketAud.close();
			} catch (IOException e) {
				Logger.exception(e);
			}
		}
	}

	public String getIp() {
		return ip;
	}

	/**
	 * Sends extra button input. L3R3, TV
	 * @param extraButtonBits button flags
	 */
	public void sendExtraButtonInput(short extraButtonBits) {
		if (extraButtonBits == 0)
			return;
		sendCommand(CommandPacket.create_button_extra(extraButtonBits));
	}

	/**
	 * Sends a command telling the server to send random audio data to the Wii U.
	 */
	public void sendMicBlow() {
		sendCommand(Constants.COMMAND_INPUT_MIC_BLOW);
	}
}
