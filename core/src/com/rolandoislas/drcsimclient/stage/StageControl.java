package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.audio.AudioThread;
import com.rolandoislas.drcsimclient.config.ConfigGeneral;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.graphics.VideoThread;
import com.rolandoislas.drcsimclient.net.CommandThread;
import com.rolandoislas.drcsimclient.net.packet.CommandPacket;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import static com.rolandoislas.drcsimclient.Client.*;

/**
 * Created by Rolando on 12/21/2016.
 */
public class StageControl extends Stage {
	private final AudioThread audioThread;
	private final VideoThread videoThread;
	private final CommandThread commandThread;
	private Texture wiiImage;
	private SpriteBatch spritebatch;
	private Button wiiScreen;
	private ConfigGeneral config;

	public StageControl() {
		// Config
		config = new ConfigGeneral();
		config.load();
		// Spritebatch
		spritebatch = new SpriteBatch();
		// Screen touchable
		wiiScreen = new Button(new Button.ButtonStyle());
		wiiScreen.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		wiiImage = new Texture("image/placeholder.png");
		addActor(wiiScreen);
		// Initialize controls
		for (Control control : controls)
			control.init(this);
		// Audio
		audioThread = new AudioThread();
		audioThread.start();
		// Video
		videoThread = new VideoThread();
		videoThread.start();
		// Command
		commandThread = new CommandThread();
		commandThread.start();
	}

	@Override
	public void resize(int width, int height) {
		spritebatch = new SpriteBatch();
		wiiScreen.setBounds(0, 0, width, height);
		this.getViewport().update(width, height);
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
		// Check touch/click screen input
		if (config.touchScreen && wiiScreen.isPressed())
			sockets.sendTouchScreenInput((short) Gdx.input.getX(), (short) Gdx.input.getY(),
					(short) Gdx.graphics.getWidth(), (short) Gdx.graphics.getHeight());
		// Update controls
		for (Control control : controls)
			control.update();
	}

	private void checkNetworkCommands() {
		if (!commandThread.isAlive())
			Client.setStage(new StageConnect("Disconnected"));
		CommandPacket command = commandThread.getCommand();
		if (command == null)
			return;
		Logger.debug("Received command id %d", command.header.type);
		switch (command.header.type) {
			case Constants.COMMAND_REGISTER:
				break;
			case Constants.COMMAND_PING:
				break;
			case Constants.COMMAND_PONG:
				commandThread.resetTimeout();
				break;
			case Constants.COMMAND_INPUT_VIBRATE:
				for (Control control : controls)
					if (config.vibrate)
						control.vibrate(1000);
				break;
			case Constants.COMMAND_INPUT_MIC_BLOW:
				break;
			case Constants.COMMAND_INPUT_BUTTON:
				break;
			case Constants.COMMAND_INPUT_JOYSTICK:
				break;
			case Constants.COMMAND_INPUT_TOUCH:
				break;
			case Constants.COMMAND_INPUT_BUTTON_EXTRA:
				break;
			default:
				Logger.debug("Unhandled command packet with id %d.", command.header.type);
				break;
		}
	}

	private void updateWiiVideoFrame() {
		// Get the frame
		byte[] data = videoThread.getImageData();
		if (data.length == 0)
			return;
		// Save the frame
		if (Client.args.storeFrames) {
			if (Constants.PATH_FRAMES.mkdirs())
				Logger.debug("Created frame log directory");
			String fileName = new File(Constants.PATH_FRAMES, "frame%d.jpg").getAbsolutePath();
			int frameNumber = 0;
			File outFile;
			do {
				outFile = new File(String.format(Locale.US, fileName, ++frameNumber));
			} while (outFile.exists() && frameNumber <= Client.args.storeFramesAmount);
			if (!outFile.exists() && frameNumber <= Client.args.storeFramesAmount) {
				try {
					FileOutputStream fileOutputStream = new FileOutputStream(outFile);
					fileOutputStream.write(data);
					fileOutputStream.close();
				} catch (FileNotFoundException e) {
					Logger.exception(e);
				} catch (IOException e) {
					Logger.exception(e);
				}
			}
		}
		// Draw the frame
		try {
			Pixmap pixmap = new Pixmap(data, 0, data.length);
			if (wiiImage != null)
				wiiImage.dispose();
			wiiImage = new Texture(pixmap);
			pixmap.dispose();
		} catch (GdxRuntimeException e) {
			Logger.info("Received incompatible image data from server.");
			Logger.exception(e);
			wiiImage = new Texture("image/placeholder.png");
		}
		// Become the frame!
	}

	@Override
	public void dispose() {
		super.dispose();
		wiiImage.dispose();
		spritebatch.dispose();
		audioThread.dispose();
		videoThread.dispose();
		commandThread.dispose();
		sockets.dispose();
	}
}
