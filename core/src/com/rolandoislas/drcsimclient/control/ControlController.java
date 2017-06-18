package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.rolandoislas.drcsimclient.config.ConfigController;
import com.rolandoislas.drcsimclient.config.ConfigKeymap;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.stage.StageControl;

import java.util.HashMap;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ControlController implements Control {

	private HashMap<Controller, ConfigController> configs = new HashMap<Controller, ConfigController>();

	@Override
	public void init(StageControl stage) {
		for (Controller controller : Controllers.getControllers()) {
			ConfigController config = new ConfigController(controller.getName());
			config.load();
			configs.put(controller, config);
		}
	}

	@Override
	public void update() {
		short buttonBits = 0;
		short extraButtonBits = 0;
		float[] axes = {0, 0, 0, 0};
		boolean micBlow = false;
		for (HashMap.Entry<Controller, ConfigController> entry : configs.entrySet()) {
			Controller controller = entry.getKey();
			ConfigController config = entry.getValue();
			// Check buttons
			if (isPressed(controller, config.buttonA))
				buttonBits |= Constants.BUTTON_A;
			if (isPressed(controller, config.buttonB))
				buttonBits |= Constants.BUTTON_B;
			if (isPressed(controller, config.buttonX))
				buttonBits |= Constants.BUTTON_X;
			if (isPressed(controller, config.buttonY))
				buttonBits |= Constants.BUTTON_Y;
			if (isPressed(controller, config.buttonUp))
				buttonBits |= Constants.BUTTON_UP;
			if (isPressed(controller, config.buttonDown))
				buttonBits |= Constants.BUTTON_DOWN;
			if (isPressed(controller, config.buttonLeft))
				buttonBits |= Constants.BUTTON_LEFT;
			if (isPressed(controller, config.buttonRight))
				buttonBits |= Constants.BUTTON_RIGHT;
			if (isPressed(controller, config.buttonL))
				buttonBits |= Constants.BUTTON_L;
			if (isPressed(controller, config.buttonR))
				buttonBits |= Constants.BUTTON_R;
			if (isPressed(controller, config.buttonZL))
				buttonBits |= Constants.BUTTON_ZL;
			if (isPressed(controller, config.buttonZR))
				buttonBits |= Constants.BUTTON_ZR;
			if (isPressed(controller, config.buttonL3))
				buttonBits |= Constants.BUTTON_L3;
			if (isPressed(controller, config.buttonR3))
				buttonBits |= Constants.BUTTON_R3;
			if (isPressed(controller, config.buttonMinus))
				buttonBits |= Constants.BUTTON_MINUS;
			if (isPressed(controller, config.buttonPlus))
				buttonBits |= Constants.BUTTON_PLUS;
			if (isPressed(controller, config.buttonHome))
				buttonBits |= Constants.BUTTON_HOME;
			// Microphone
			if (isPressed(controller, config.micBlow))
				micBlow = true;
			// Check joystick
			axes[0] = getJoystickInput(controller, config.joystickLeftX);
			axes[1] = getJoystickInput(controller, config.joystickLeftY) * -1;
			axes[2] = getJoystickInput(controller, config.joystickRightX);
			axes[3] = getJoystickInput(controller, config.joystickRightY) * -1;
		}
		sockets.sendButtonInput(buttonBits);
		sockets.sendExtraButtonInput(extraButtonBits);
		sockets.sendJoystickInput(axes);
		if (micBlow)
			sockets.sendMicBlow();
	}

	private float getJoystickInput(Controller controller, ConfigKeymap.Input input) {
		if (input.getType() == ConfigKeymap.Input.TYPE_AXIS)
			return controller.getAxis(input.getInput());
		else
			return 0;
	}

	private boolean isPressed(Controller controller, ConfigKeymap.Input input) {
		switch (input.getType()) {
			case ConfigKeymap.Input.TYPE_AXIS:
				return input.getExtra() == 0 ? controller.getAxis(input.getInput()) < -.2 :
						controller.getAxis(input.getInput()) > .2;
			case ConfigKeymap.Input.TYPE_POV:
				return controller.getPov(input.getInput()).ordinal() == input.getExtra();
			case ConfigKeymap.Input.TYPE_BUTTON:
				return controller.getButton(input.getInput());
		}
		return false;
	}

	@Override
	public void vibrate(int milliseconds) {

	}
}
