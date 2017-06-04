package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Input;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ConfigKeyboard extends ConfigKeymap {
	public ConfigKeyboard() {
		super("keyboard");
	}

	@Override
	public void load() {
		buttonA = getInteger(BUTTON_A, Input.Keys.SPACE);
		buttonB = getInteger(BUTTON_B, Input.Keys.E);
		buttonX = getInteger(BUTTON_X, Input.Keys.D);
		buttonY = getInteger(BUTTON_Y, Input.Keys.F);
		buttonUp = getInteger(BUTTON_UP, Input.Keys.UP);
		buttonDown = getInteger(BUTTON_DOWN, Input.Keys.DOWN);
		buttonLeft = getInteger(BUTTON_LEFT, Input.Keys.LEFT);
		buttonRight = getInteger(BUTTON_RIGHT, Input.Keys.RIGHT);
		buttonL = getInteger(BUTTON_L, Input.Keys.Q);
		buttonR = getInteger(BUTTON_R, Input.Keys.R);
		buttonZL = getInteger(BUTTON_ZL, Input.Keys.NUM_1);
		buttonZR = getInteger(BUTTON_ZR, Input.Keys.NUM_4);
		buttonL3 = getInteger(BUTTON_L3, Input.Keys.A);
		buttonR3 = getInteger(BUTTON_R3, Input.Keys.S);
		buttonMinus = getInteger(BUTTON_MINUS, Input.Keys.X);
		buttonPlus = getInteger(BUTTON_PLUS, Input.Keys.C);
		buttonHome = getInteger(BUTTON_HOME, Input.Keys.Z);
		micBlow = getInteger(MIC_BLOW, Input.Keys.B);
		putInteger(BUTTON_A, buttonA);
		putInteger(BUTTON_B, buttonB);
		putInteger(BUTTON_X, buttonX);
		putInteger(BUTTON_Y, buttonY);
		putInteger(BUTTON_UP, buttonUp);
		putInteger(BUTTON_DOWN, buttonDown);
		putInteger(BUTTON_LEFT, buttonLeft);
		putInteger(BUTTON_RIGHT, buttonRight);
		putInteger(BUTTON_L, buttonL);
		putInteger(BUTTON_R, buttonR);
		putInteger(BUTTON_ZL, buttonZL);
		putInteger(BUTTON_ZR, buttonZR);
		putInteger(BUTTON_L3, buttonL3);
		putInteger(BUTTON_R3, buttonR3);
		putInteger(BUTTON_MINUS, buttonMinus);
		putInteger(BUTTON_PLUS, buttonPlus);
		putInteger(BUTTON_HOME, buttonHome);
		putInteger(MIC_BLOW, micBlow);
		flush();
	}
}
