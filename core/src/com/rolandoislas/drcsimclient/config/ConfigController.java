package com.rolandoislas.drcsimclient.config;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ConfigController extends ConfigKeymap {
	public ConfigController(String controllerName) {
		super(cleanName(controllerName));
	}

	private static String cleanName(String controllerName) {
		controllerName = controllerName
				.replaceAll(" ", "_")
				.replaceAll("\\.", "")
				.replaceAll("-", "_")
				.toLowerCase();
		return "controller." + controllerName;
	}

	public void load() {
		buttonA = getInteger(BUTTON_A, -1);
		buttonB = getInteger(BUTTON_B, -1);
		buttonX = getInteger(BUTTON_X, -1);
		buttonY = getInteger(BUTTON_Y, -1);
		buttonUp = getInteger(BUTTON_UP, -1);
		buttonDown = getInteger(BUTTON_DOWN, -1);
		buttonLeft = getInteger(BUTTON_LEFT, -1);
		buttonRight = getInteger(BUTTON_RIGHT, -1);
		buttonL = getInteger(BUTTON_L, -1);
		buttonR = getInteger(BUTTON_R, -1);
		buttonZL = getInteger(BUTTON_ZL, -1);
		buttonZR = getInteger(BUTTON_ZR, -1);
		buttonL3 = getInteger(BUTTON_L3, -1);
		buttonR3 = getInteger(BUTTON_R3, -1);
		buttonMinus = getInteger(BUTTON_MINUS, -1);
		buttonPlus = getInteger(BUTTON_PLUS, -1);
		buttonHome = getInteger(BUTTON_HOME, -1);
		joystickLeftX = getInteger(JOYSTICK_LEFT_X, -1);
		joystickLeftY = getInteger(JOYSTICK_LEFT_Y, -1);
		joystickRightX = getInteger(JOYSTICK_RIGHT_X, -1);
		joystickRightY = getInteger(JOYSTICK_RIGHT_Y, -1);
		micBlow = getInteger(MIC_BLOW, -1);
		flush();
	}
}
