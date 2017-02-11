package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rolandoislas.drcsimclient.Util.AudioUtil;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.Codec;
import com.rolandoislas.drcsimclient.net.NetUtil;

import java.io.IOException;
import java.net.DatagramPacket;

import static com.rolandoislas.drcsimclient.Client.*;

/**
 * Created by Rolando on 12/21/2016.
 */
public class StageControl extends Stage {
	private final AudioUtil audio;
	private Texture wiiImage;
	private SpriteBatch spritebatch;
	private Button wiiScreen;

	public StageControl() {
		Gdx.input.setCatchBackKey(true);
		// Spritebatch
		spritebatch = new SpriteBatch();
		// Screen touchable
		wiiScreen = new Button(new Button.ButtonStyle());
		wiiScreen.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		wiiImage = new Texture("image/placeholder.png");
		addActor(wiiScreen);
		// Audio
		audio = new AudioUtil();
		// Initialize controls
		for (Control control : controls)
			control.init(this);
	}

	@Override
	public void onBackButtonPressed() {
		setStage(new StageConnect());
	}

	@Override
	public void draw() {
		spritebatch.begin();
		if (wiiImage != null)
			spritebatch.draw(wiiImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spritebatch.end();
		super.draw();
	}

	@Override
	public void act() {
		super.act();
		// Check commands
		checkNetworkCommands();
		// Update wii video frame
		updateWiiVideoFrame();
		// Update wii audio
		updateWiiAudio();
		// Check touch/click screen input
		if (wiiScreen.isPressed())
			sockets.sendTouchScreenInput(Gdx.input.getX(), Gdx.input.getY());
		// Update controls
		for (Control control : controls)
			control.update();
	}

	private void updateWiiAudio() {
		try {
			byte[] packet = NetUtil.recv(sockets.socketAud, "audio");
			//audio.addData(packet);
		} catch (IOException e) {
			checkDisconnect(e);
		}
		//audio.update();
	}

	private void checkDisconnect(IOException e) {
		if (e.getMessage().contains("Read timeout"))
			return;
		else if (e.getMessage().contains("Disconnected")) {
			setStage(new StageConnect("Disconnected"));
			return;
		}
		e.printStackTrace();
	}

	private void checkNetworkCommands() {
		byte[] buf = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try {
			sockets.socketCmd.receive(packet);
		} catch (IOException ignore) {}
		String[] command = Codec.decodeCommand(packet);
		// Handle command
		if (command[0].equals(Constants.COMMAND_PONG))
			NetUtil.resetTimeout();
		else if (command[0].equals(Constants.COMMAND_VIBRATE) && false)
			Gdx.input.vibrate(1000);
	}

	private void updateWiiVideoFrame() {
		try {
			// Get and draw image
			byte[] data = NetUtil.recv(sockets.socketVid, "video");
			Pixmap pixmap = new Pixmap(data, 0, data.length);
			if (wiiImage != null)
				wiiImage.dispose();
			wiiImage = new Texture(pixmap);
			pixmap.dispose();
		} catch (IOException e) {
			checkDisconnect(e);
		} catch (GdxRuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		wiiImage.dispose();
		spritebatch.dispose();
		audio.dispose();
	}
}
