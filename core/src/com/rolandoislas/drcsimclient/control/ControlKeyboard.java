package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.Gdx;
import com.rolandoislas.drcsimclient.config.ConfigKeyboard;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.Codec;
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
		int buttonbits = 0;
		if (Gdx.input.isKeyPressed(config.buttonA))
			buttonbits |= Constants.BUTTON_A;
		if (Gdx.input.isKeyPressed(config.buttonB))
			buttonbits |= Constants.BUTTON_B;
		if (Gdx.input.isKeyPressed(config.buttonX))
			buttonbits |= Constants.BUTTON_X;
		if (Gdx.input.isKeyPressed(config.buttonY))
			buttonbits |= Constants.BUTTON_Y;
		if (Gdx.input.isKeyPressed(config.buttonUp))
			buttonbits |= Constants.BUTTON_UP;
		if (Gdx.input.isKeyPressed(config.buttonDown))
			buttonbits |= Constants.BUTTON_DOWN;
		if (Gdx.input.isKeyPressed(config.buttonLeft))
			buttonbits |= Constants.BUTTON_LEFT;
		if (Gdx.input.isKeyPressed(config.buttonRight))
			buttonbits |= Constants.BUTTON_RIGHT;
		if (Gdx.input.isKeyPressed(config.buttonL))
			buttonbits |= Constants.BUTTON_L;
		if (Gdx.input.isKeyPressed(config.buttonR))
			buttonbits |= Constants.BUTTON_R;
		if (Gdx.input.isKeyPressed(config.buttonZL))
			buttonbits |= Constants.BUTTON_ZL;
		if (Gdx.input.isKeyPressed(config.buttonZR))
			buttonbits |= Constants.BUTTON_ZR;
		if (Gdx.input.isKeyPressed(config.buttonMinus))
			buttonbits |= Constants.BUTTON_MINUS;
		if (Gdx.input.isKeyPressed(config.buttonPlus))
			buttonbits |= Constants.BUTTON_PLUS;
		if (Gdx.input.isKeyPressed(config.buttonHome))
			buttonbits |= Constants.BUTTON_HOME;
		sockets.sendButtonInput(buttonbits);
		// Extra
		int extraButtonBits = 0;
		if (Gdx.input.isKeyPressed(config.buttonL3))
			extraButtonBits |= Constants.BUTTON_L3;
		if (Gdx.input.isKeyPressed(config.buttonR3))
			extraButtonBits |= Constants.BUTTON_R3;
		sockets.sendExtraButtonInput(extraButtonBits);
		// Mic
		if (Gdx.input.isKeyPressed(config.micBlow))
			sockets.sendCommand(Constants.COMMAND_INPUT_MIC_BLOW, Codec.encodeInput(true));
		// Joystick
		// TODO get joystick input based on mouse capture
	}

	@Override
	public void vibrate(int milliseconds) {

	}
}
