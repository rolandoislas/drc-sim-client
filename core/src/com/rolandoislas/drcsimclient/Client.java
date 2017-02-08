package com.rolandoislas.drcsimclient;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.net.Sockets;
import com.rolandoislas.drcsimclient.stage.StageConnect;

public class Client extends ApplicationAdapter {
	private static Stage stage;
	public static Sockets sockets;
	public static Control[] controls;

	public Client(Control[] controls) {
		Client.controls = controls;
	}

	@Override
	public void create () {
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
		if (sockets != null)
			sockets.close();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public static void setStage(Stage stage) {
		if (Client.stage != null)
			Client.stage.dispose();
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}

	public static boolean connect(String ip) {
		// Sockets
		if (sockets != null)
			sockets.close();
		sockets = new Sockets();
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
