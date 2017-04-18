package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ConfigKeyboard extends Config {
	private Preferences config;

	public ConfigKeyboard() {
		config = Gdx.app.getPreferences("com.rolandoislas.drcsimclient.config.keyboard");
	}

	public void load() {
		buttonA = config.getInteger(BUTTON_A, Input.Keys.SPACE);
		buttonB = config.getInteger(BUTTON_B, Input.Keys.E);
		buttonX = config.getInteger(BUTTON_X, Input.Keys.D);
		buttonY = config.getInteger(BUTTON_Y, Input.Keys.F);
		buttonUp = config.getInteger(BUTTON_UP, Input.Keys.UP);
		buttonDown = config.getInteger(BUTTON_DOWN, Input.Keys.DOWN);
		buttonLeft = config.getInteger(BUTTON_LEFT, Input.Keys.LEFT);
		buttonRight = config.getInteger(BUTTON_RIGHT, Input.Keys.RIGHT);
		buttonL = config.getInteger(BUTTON_L, Input.Keys.Q);
		buttonR = config.getInteger(BUTTON_R, Input.Keys.R);
		buttonZL = config.getInteger(BUTTON_ZL, Input.Keys.NUM_1);
		buttonZR = config.getInteger(BUTTON_ZR, Input.Keys.NUM_4);
		buttonL3 = config.getInteger(BUTTON_L3, Input.Keys.A);
		buttonR3 = config.getInteger(BUTTON_R3, Input.Keys.S);
		buttonMinus = config.getInteger(BUTTON_MINUS, Input.Keys.X);
		buttonPlus = config.getInteger(BUTTON_PLUS, Input.Keys.C);
		buttonHome = config.getInteger(BUTTON_HOME, Input.Keys.Z);
		micBlow = config.getInteger(MIC_BLOW, Input.Keys.B);
		config.putInteger(BUTTON_A, buttonA);
		config.putInteger(BUTTON_B, buttonB);
		config.putInteger(BUTTON_X, buttonX);
		config.putInteger(BUTTON_Y, buttonY);
		config.putInteger(BUTTON_UP, buttonUp);
		config.putInteger(BUTTON_DOWN, buttonDown);
		config.putInteger(BUTTON_LEFT, buttonLeft);
		config.putInteger(BUTTON_RIGHT, buttonRight);
		config.putInteger(BUTTON_L, buttonL);
		config.putInteger(BUTTON_R, buttonR);
		config.putInteger(BUTTON_ZL, buttonZL);
		config.putInteger(BUTTON_ZR, buttonZR);
		config.putInteger(BUTTON_L3, buttonL3);
		config.putInteger(BUTTON_R3, buttonR3);
		config.putInteger(BUTTON_MINUS, buttonMinus);
		config.putInteger(BUTTON_PLUS, buttonPlus);
		config.putInteger(BUTTON_HOME, buttonHome);
		config.putInteger(MIC_BLOW, micBlow);
		config.flush();
	}

	public String get(String key) {
		return config.getString(key);
	}

	public void set(String item, int input) {
		config.putInteger(item, input);
		config.flush();
		load();
	}
}
