package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rolandoislas.drcsimclient.audio.AudioThread;
import com.rolandoislas.drcsimclient.config.ConfigGeneral;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.graphics.VideoThread;
import com.rolandoislas.drcsimclient.net.CommandThread;
import com.rolandoislas.drcsimclient.util.logging.Logger;

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
		Gdx.input.setCatchBackKey(true);
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
		if (config.touchScreen == 1 && wiiScreen.isPressed())
			sockets.sendTouchScreenInput(Gdx.input.getX(), Gdx.input.getY());
		// Update controls
		for (Control control : controls)
			control.update();
	}

	private void checkNetworkCommands() {
		CommandThread.Command command = commandThread.getCommand();
		// Handle command
		if (command.isCommand(Constants.COMMAND_PONG)) {
			audioThread.resetTimeout();
			audioThread.resetTimeout();
		}
		else if (command.isCommand(Constants.COMMAND_VIBRATE))
			for (Control control : controls)
				control.vibrate(1000);
	}

	private void updateWiiVideoFrame() {
		if (!videoThread.isAlive())
			setStage(new StageConnect("Disconnected"));
		try {
			// Get and draw image
			byte[] data = videoThread.getImageData();
			if (data.length == 0)
				return;
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
