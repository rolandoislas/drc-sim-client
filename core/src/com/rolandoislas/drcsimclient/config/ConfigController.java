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
		buttonA = getInput(BUTTON_A, -1, -1, -1);
		buttonB = getInput(BUTTON_B, -1, -1, -1);
		buttonX = getInput(BUTTON_X, -1, -1, -1);
		buttonY = getInput(BUTTON_Y, -1, -1, -1);
		buttonUp = getInput(BUTTON_UP, -1, -1, -1);
		buttonDown = getInput(BUTTON_DOWN, -1, -1, -1);
		buttonLeft = getInput(BUTTON_LEFT, -1, -1, -1);
		buttonRight = getInput(BUTTON_RIGHT, -1, -1, -1);
		buttonL = getInput(BUTTON_L, -1, -1, -1);
		buttonR = getInput(BUTTON_R, -1, -1, -1);
		buttonZL = getInput(BUTTON_ZL, -1, -1, -1);
		buttonZR = getInput(BUTTON_ZR, -1, -1, -1);
		buttonL3 = getInput(BUTTON_L3, -1, -1, -1);
		buttonR3 = getInput(BUTTON_R3, -1, -1, -1);
		buttonMinus = getInput(BUTTON_MINUS, -1, -1, -1);
		buttonPlus = getInput(BUTTON_PLUS, -1, -1, -1);
		buttonHome = getInput(BUTTON_HOME, -1, -1, -1);
		joystickLeftX = getInput(JOYSTICK_LEFT_X, -1, -1, -1);
		joystickLeftY = getInput(JOYSTICK_LEFT_Y, -1, -1, -1);
		joystickRightX = getInput(JOYSTICK_RIGHT_X, -1, -1, -1);
		joystickRightY = getInput(JOYSTICK_RIGHT_Y, -1, -1, -1);
		micBlow = getInput(MIC_BLOW, -1, -1, -1);
		save();
	}
}
