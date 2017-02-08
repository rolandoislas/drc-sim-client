package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;

/**
 * Created by Rolando on 2/6/2017.
 */
public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage {
	@Override
	public boolean keyDown(int keyCode) {
		// Back Button
		if (keyCode == Input.Keys.BACK || keyCode == Input.Keys.ESCAPE)
			this.onBackButtonPressed();
		// Fullscreen Button
		if (Gdx.input.isKeyPressed(Input.Keys.F11))
			toggleFullscreen();
		return super.keyDown(keyCode);
	}

	private void toggleFullscreen() {
		if (Gdx.graphics.isFullscreen()) {
			Gdx.graphics.setWindowedMode(1280, 720);
			return;
		}
		Graphics.DisplayMode largest = null;
		for (Graphics.DisplayMode displayMode : Gdx.graphics.getDisplayModes()) {
			if (largest == null)
				largest = displayMode;
			if (displayMode.width > largest.width && displayMode.height > largest.height)
				largest = displayMode;
		}
		Gdx.graphics.setFullscreenMode(largest);

	}

	public void onBackButtonPressed() {}
}
