package com.rolandoislas.drcsimclient;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rolandoislas.drcsimclient.stage.StageConnect;
import com.rolandoislas.drcsimclient.stage.StageControl;

public class Client extends ApplicationAdapter {
	private static Stage stage;

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
		stage.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
		create();
	}

	public static void setStage(Stage stage) {
		Client.stage = stage;
		Gdx.input.setInputProcessor(stage);
	}
}
