package com.rolandoislas.drcsimclient;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rolandoislas.drcsimclient.stage.StageControl;

public class Client extends ApplicationAdapter {
	private Stage stage;

	@Override
	public void create () {
		this.stage = new StageControl("eee-pc");
		Gdx.input.setInputProcessor(stage);
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
}
