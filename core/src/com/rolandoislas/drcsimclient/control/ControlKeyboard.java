package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.Gdx;
import com.rolandoislas.drcsimclient.config.ConfigKeyboard;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.stage.StageControl;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ControlKeyboard implements Control {
	private ConfigKeyboard config;

	@Override
	public void init(StageControl stage) {
		config = new ConfigKeyboard();
		config.load();
	}

	@Override
	public void update() {
		// Check Buttons
		short buttonbits = 0;
		if (Gdx.input.isKeyPressed(config.buttonA.getInput()))
			buttonbits |= Constants.BUTTON_A;
		if (Gdx.input.isKeyPressed(config.buttonB.getInput()))
			buttonbits |= Constants.BUTTON_B;
		if (Gdx.input.isKeyPressed(config.buttonX.getInput()))
			buttonbits |= Constants.BUTTON_X;
		if (Gdx.input.isKeyPressed(config.buttonY.getInput()))
			buttonbits |= Constants.BUTTON_Y;
		if (Gdx.input.isKeyPressed(config.buttonUp.getInput()))
			buttonbits |= Constants.BUTTON_UP;
		if (Gdx.input.isKeyPressed(config.buttonDown.getInput()))
			buttonbits |= Constants.BUTTON_DOWN;
		if (Gdx.input.isKeyPressed(config.buttonLeft.getInput()))
			buttonbits |= Constants.BUTTON_LEFT;
		if (Gdx.input.isKeyPressed(config.buttonRight.getInput()))
			buttonbits |= Constants.BUTTON_RIGHT;
		if (Gdx.input.isKeyPressed(config.buttonL.getInput()))
			buttonbits |= Constants.BUTTON_L;
		if (Gdx.input.isKeyPressed(config.buttonR.getInput()))
			buttonbits |= Constants.BUTTON_R;
		if (Gdx.input.isKeyPressed(config.buttonZL.getInput()))
			buttonbits |= Constants.BUTTON_ZL;
		if (Gdx.input.isKeyPressed(config.buttonZR.getInput()))
			buttonbits |= Constants.BUTTON_ZR;
		if (Gdx.input.isKeyPressed(config.buttonMinus.getInput()))
			buttonbits |= Constants.BUTTON_MINUS;
		if (Gdx.input.isKeyPressed(config.buttonPlus.getInput()))
			buttonbits |= Constants.BUTTON_PLUS;
		if (Gdx.input.isKeyPressed(config.buttonHome.getInput()))
			buttonbits |= Constants.BUTTON_HOME;
		sockets.sendButtonInput(buttonbits);
		// Extra
		short extraButtonBits = 0;
		if (Gdx.input.isKeyPressed(config.buttonL3.getInput()))
			extraButtonBits |= Constants.BUTTON_L3;
		if (Gdx.input.isKeyPressed(config.buttonR3.getInput()))
			extraButtonBits |= Constants.BUTTON_R3;
		sockets.sendExtraButtonInput(extraButtonBits);
		// Mic
		if (Gdx.input.isKeyPressed(config.micBlow.getInput()))
			sockets.sendMicBlow();
		// Joystick
		// TODO get joystick input based on mouse capture
	}

	@Override
	public void vibrate(int milliseconds) {

	}
}
