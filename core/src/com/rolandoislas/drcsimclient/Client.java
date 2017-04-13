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
		Logger.info("Starting %1$s version %2$s", Constants.NAME, Constants.VERSION);
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
		Logger.debug("Setting stage to %1$s", stage.getClass().getSimpleName());
		Client.stage.dispose();
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	public static boolean connect(String ip, boolean setStageOnFailure) {
		sockets.dispose();
		sockets.setIp(ip);
		try {
			sockets.connect();
		} catch (Exception e) {
			Logger.info("Failed to connect to host \"%1$s\"", ip);
			Logger.info(e.getMessage());
			Logger.exception(e);
			if (setStageOnFailure)
				setStage(new StageConnect(e.getMessage()));
			return false;
		}
		return true;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		try {
			setStage(Client.stage.getClass().newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
