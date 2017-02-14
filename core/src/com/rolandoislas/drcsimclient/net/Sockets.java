package com.rolandoislas.drcsimclient.net;

import com.badlogic.gdx.Gdx;
import com.rolandoislas.drcsimclient.data.Constants;

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

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void connect() throws Exception {
		try {
			this.socketVid = new Socket();
			this.socketVid.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_VID), 1500);
			this.socketAud = new Socket();
			this.socketAud.connect(new InetSocketAddress(InetAddress.getByName(ip), Constants.PORT_SERVER_AUD), 1500);
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
			e.printStackTrace();
			throw e;
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

	/**
	 * Send button input
	 * @param buttonBits int with button flags
	 */
	public void sendButtonInput(int buttonBits) {
		if (buttonBits == 0)
			return;
		sendCommand(Constants.COMMAND_INPUT_BUTTON, Codec.encodeInput(buttonBits));
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
		for (float axis : axes)
			if (Math.abs(axis) > 0.2)
				found = true;
		if (!found)
			return;
		sendCommand(Constants.COMMAND_INPUT_JOYSTICK, Codec.encodeInput(axes));
	}

	/**
	 * Send touch screen input
	 * @param screenX x
	 * @param screenY y
	 */
	public void sendTouchScreenInput(int screenX, int screenY) {
		int[] touchCoords = new int[]{screenX, screenY};
		int[] screenSize = new int[]{Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		sendCommand(Constants.COMMAND_INPUT_TOUCH, Codec.encodeInput(new int[][] {touchCoords, screenSize}));
	}

	public void dispose() {
		if (socketCmd != null) {
			socketCmd.disconnect();
			socketCmd.close();
		}
		if (socketVid != null) {
			try {
				socketVid.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socketAud != null) {
			try {
				socketAud.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		NetUtil.clear();
	}

	public String getIp() {
		return ip;
	}
}
