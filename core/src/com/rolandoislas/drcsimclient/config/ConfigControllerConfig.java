package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ConfigControllerConfig extends Config {
	private Preferences config;
	public int joystickLeftX;
	public int joystickLeftY;
	public int joystickRightX;
	public int joystickRightY;

	ConfigControllerConfig(String name) {
		config = Gdx.app.getPreferences("com.rolandoislas.controller." + name.replaceAll(" ", ""));
	}

	public void load() {
		buttonA = config.getInteger(BUTTON_A, -1);
		buttonB = config.getInteger(BUTTON_B, -1);
		buttonX = config.getInteger(BUTTON_X, -1);
		buttonY = config.getInteger(BUTTON_Y, -1);
		buttonUp = config.getInteger(BUTTON_UP, -1);
		buttonDown = config.getInteger(BUTTON_DOWN, -1);
		buttonLeft = config.getInteger(BUTTON_LEFT, -1);
		buttonRight = config.getInteger(BUTTON_RIGHT, -1);
		buttonL = config.getInteger(BUTTON_L, -1);
		buttonR = config.getInteger(BUTTON_R, -1);
		buttonZL = config.getInteger(BUTTON_ZL, -1);
		buttonZR = config.getInteger(BUTTON_ZR, -1);
		buttonL3 = config.getInteger(BUTTON_L3, -1);
		buttonR3 = config.getInteger(BUTTON_R3, -1);
		buttonMinus = config.getInteger(BUTTON_MINUS, -1);
		buttonPlus = config.getInteger(BUTTON_PLUS, -1);
		buttonHome = config.getInteger(BUTTON_HOME, -1);
		joystickLeftX = config.getInteger(JOYSTICK_LEFT_X, -1);
		joystickLeftY = config.getInteger(JOYSTICK_LEFT_Y, -1);
		joystickRightX = config.getInteger(JOYSTICK_RIGHT_X, -1);
		joystickRightY = config.getInteger(JOYSTICK_RIGHT_Y, -1);
		config.flush();
	}

	@Override
	public void set(String item, int input) {
		config.putInteger(item, input);
		config.flush();
		load();
	}
	@Override
	public String get(String key) {
		return config.getString(key);
	}

}
