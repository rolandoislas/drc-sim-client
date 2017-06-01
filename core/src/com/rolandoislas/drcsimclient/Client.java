package com.rolandoislas.drcsimclient;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rolandoislas.drcsimclient.audio.Audio;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.data.ArgumentParser;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.Sockets;
import com.rolandoislas.drcsimclient.stage.Stage;
import com.rolandoislas.drcsimclient.stage.StageConnect;
import com.rolandoislas.drcsimclient.stage.StageLoad;
import com.rolandoislas.drcsimclient.util.logging.Logger;

public class Client extends ApplicationAdapter {
	public static ArgumentParser args;
	public static Audio audio;
	private static Stage stage;
	public static Sockets sockets;
	public static Control[] controls;

	public Client(Control[] controls, Audio audio, ArgumentParser argumentParser) {
		Client.controls = controls;
		Client.audio = audio;
		Client.args = argumentParser;
		Logger.info("Starting %s version %s", Constants.NAME, Constants.VERSION);
	}

	public Client(Control[] controls, Audio audio) {
		this(controls, audio, new ArgumentParser());
	}

	@Override
	public void create () {
		sockets = new Sockets();
		stage = new Stage();
		setStage(new StageLoad());
	}

	@Override
	public void render () {
		super.render();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		stage.dispose();
		sockets.dispose();
	}

	@Override
	public void pause() {
		super.pause();
		if (Gdx.app.getType() == Application.ApplicationType.Android)
			setStage(new StageConnect());
	}

	@Override
	public void resume() {
		super.resume();
	}

	public static void setStage(Stage stage) {
		Logger.debug("Setting stage to %s", stage.getClass().getSimpleName());
		try {
			Client.stage.dispose();
		}
		catch (IllegalArgumentException e) {
			Logger.exception(e);
		}
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * Attempts to connect to a server at a given IP.
	 * @param ip ip or hostname
	 * @return empty string or error message
	 */
	public static String connect(String ip) {
		sockets.dispose();
		sockets.setIp(ip);
		try {
			sockets.connect();
		} catch (Exception e) {
			Logger.info("Failed to connect to host \"%1$s\"", ip);
			Logger.info(e.getMessage());
			Logger.exception(e);
			return e.getMessage();
		}
		return "";
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Client.stage.resize(width, height);
	}
}
