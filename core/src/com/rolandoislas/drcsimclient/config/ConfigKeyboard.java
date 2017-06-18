package com.rolandoislas.drcsimclient.config;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ConfigKeyboard extends ConfigKeymap {
	public ConfigKeyboard() {
		super("keyboard");
	}

	@Override
	public void load() {
		buttonA = getInput(BUTTON_A, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.SPACE, 0);
		buttonB = getInput(BUTTON_B, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.E, 0);
		buttonX = getInput(BUTTON_X, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.D, 0);
		buttonY = getInput(BUTTON_Y, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.F, 0);
		buttonUp = getInput(BUTTON_UP, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.UP, 0);
		buttonDown = getInput(BUTTON_DOWN, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.DOWN, 0);
		buttonLeft = getInput(BUTTON_LEFT, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.LEFT, 0);
		buttonRight = getInput(BUTTON_RIGHT, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.RIGHT, 0);
		buttonL = getInput(BUTTON_L, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.Q, 0);
		buttonR = getInput(BUTTON_R, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.R, 0);
		buttonZL = getInput(BUTTON_ZL, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.NUM_1, 0);
		buttonZR = getInput(BUTTON_ZR, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.NUM_4, 0);
		buttonL3 = getInput(BUTTON_L3, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.A, 0);
		buttonR3 = getInput(BUTTON_R3, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.S, 0);
		buttonMinus = getInput(BUTTON_MINUS, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.X, 0);
		buttonPlus = getInput(BUTTON_PLUS, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.C, 0);
		buttonHome = getInput(BUTTON_HOME, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.Z, 0);
		joystickLeftX = getInput(JOYSTICK_LEFT_X, -1, -1, -1);
		joystickLeftY = getInput(JOYSTICK_LEFT_Y, -1, -1, -1);
		joystickRightX = getInput(JOYSTICK_RIGHT_X, -1, -1, -1);
		joystickRightY = getInput(JOYSTICK_RIGHT_Y, -1, -1, -1);
		micBlow = getInput(MIC_BLOW, ConfigKeymap.Input.TYPE_BUTTON, com.badlogic.gdx.Input.Keys.B, 0);
		save();
	}
}
