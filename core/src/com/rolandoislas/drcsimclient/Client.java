package com.rolandoislas.drcsimclient;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rolandoislas.drcsimclient.audio.Audio;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.net.Sockets;
import com.rolandoislas.drcsimclient.stage.Stage;
import com.rolandoislas.drcsimclient.stage.StageConnect;
import com.rolandoislas.drcsimclient.stage.StageControl;

public class Client extends ApplicationAdapter {
	public static Audio audio;
	private static Stage stage;
	public static Sockets sockets;
	public static Control[] controls;

	public Client(Control[] controls, Audio audio) {
		Client.controls = controls;
		Client.audio = audio;
	}

	@Override
	public void create () {
		sockets = new Sockets();
		stage = new Stage();
		setStage(new StageConnect());
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
	}

	@Override
	public void resume() {
		super.resume();
		if (stage instanceof StageControl)
			setStage(new StageControl());
	}

	public static void setStage(Stage stage) {
		Client.stage.dispose();
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	public static boolean connect(String ip) {
		sockets.dispose();
		sockets.setIp(ip);
		try {
			sockets.connect();
		} catch (Exception e) {
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
