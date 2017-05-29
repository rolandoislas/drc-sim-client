package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.rolandoislas.drcsimclient.config.ConfigTouch;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.graphics.TextureUtil;
import com.rolandoislas.drcsimclient.stage.StageControl;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ControlTouch implements Control {
	private Touchpad touchpad;
	private Button buttonA;
	private TextButton buttonB;
	private TextButton buttonHome;
	private Button buttonLeftTrigger;
	private Button buttonRightTrigger;
	private ConfigTouch config;
	private TextButton buttonX;
	private TextButton buttonY;
	private TextButton buttonMinus;
	private TextButton buttonPlus;

	@Override
	public void init(StageControl stage) {
		config = new ConfigTouch();
		config.load();
		// Touchpad
		float x = Gdx.graphics.getWidth() * (100f / 2560);
		float y = Gdx.graphics.getHeight() * (100f / 1440);
		float touchpadWidth = Gdx.graphics.getWidth() * (250f / 2560);
		float touchpadHeight = Gdx.graphics.getHeight() * (250f / 1440);
		Skin touchpadSkin = new Skin();
		touchpadSkin.add("background", new Texture("image/touchpad-background.png"));
		touchpadSkin.add("knob",
				TextureUtil.resizeTexture("image/touchpad-knob.png", touchpadWidth / 2,
						touchpadHeight / 2));
		Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
		touchpadStyle.background = touchpadSkin.getDrawable("background");
		touchpadStyle.knob = touchpadSkin.getDrawable("knob");
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(x, y, touchpadWidth, touchpadHeight);
		stage.addActor(touchpad);
		// A Button
		float buttonWidth = touchpadWidth / 2;
		float buttonHeight = touchpadHeight / 2;
		Skin buttonSkin = new Skin();
		buttonSkin.add("up",
				TextureUtil.resizeTexture("image/button-up.png", buttonWidth, buttonHeight));
		buttonSkin.add("down",
				TextureUtil.resizeTexture("image/button-down.png", buttonWidth, buttonHeight));
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("up");
		buttonStyle.down = buttonSkin.getDrawable("down");
		buttonStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		buttonA = new TextButton("A", buttonStyle);
		buttonA.setPosition(Gdx.graphics.getWidth() - x - buttonWidth, y + buttonHeight);
		stage.addActor(buttonA);
		// B Button
		buttonB = new TextButton("B", buttonStyle);
		buttonB.setPosition(Gdx.graphics.getWidth() - x - 2 * buttonWidth, y);
		stage.addActor(buttonB);
		// X Button
		buttonX = new TextButton("X", buttonStyle);
		buttonX.setPosition(buttonA.getX() - buttonWidth, buttonA.getY() + buttonHeight);
		stage.addActor(buttonX);
		// Y Button
		buttonY = new TextButton("Y", buttonStyle);
		buttonY.setPosition(buttonB.getX() - buttonWidth, buttonB.getY() + buttonHeight);
		stage.addActor(buttonY);
		// Home Button
		buttonHome = new TextButton("H", buttonStyle);
		buttonHome.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, 10);
		stage.addActor(buttonHome);
		// Minus Button
		buttonMinus = new TextButton("-", buttonStyle);
		buttonMinus.setPosition(buttonHome.getX() - buttonWidth, buttonHome.getY());
		stage.addActor(buttonMinus);
		// Plus Button
		buttonPlus = new TextButton("+", buttonStyle);
		buttonPlus.setPosition(buttonHome.getX() + buttonWidth, buttonHome.getY());
		stage.addActor(buttonPlus);
		// TODO D-pad
		// Left Trigger
		float triggerWidth = Gdx.graphics.getWidth() * .1f;
		float triggerHeight = Gdx.graphics.getHeight() * .1f;
		if (config.triggersVisible == 1)
			buttonLeftTrigger = new TextButton("L", buttonStyle);
		else
			buttonLeftTrigger = new Button(new Button.ButtonStyle());
		buttonLeftTrigger.setBounds(0, Gdx.graphics.getHeight() - triggerHeight, triggerWidth, triggerHeight);
		stage.addActor(buttonLeftTrigger);
		// Right Trigger
		if (config.triggersVisible == 1)
			buttonRightTrigger = new TextButton("R", buttonStyle);
		else
			buttonRightTrigger = new Button(new Button.ButtonStyle());
		buttonRightTrigger.setBounds(Gdx.graphics.getWidth() - triggerWidth,
				Gdx.graphics.getHeight() - triggerHeight, triggerWidth, triggerHeight);
		stage.addActor(buttonRightTrigger);
	}

	@Override
	public void update() {
		// Check touchpad (joystick) input
		if (touchpad.isTouched())
			sockets.sendJoystickInput(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
		// Check buttons
		short buttonBits = 0;
		if (buttonA.isPressed())
			buttonBits |= Constants.BUTTON_A;
		if (buttonB.isPressed())
			buttonBits |= Constants.BUTTON_B;
		if (buttonX.isPressed())
			buttonBits |= Constants.BUTTON_X;
		if (buttonY.isPressed())
			buttonBits |= Constants.BUTTON_Y;
		if (buttonHome.isPressed())
			buttonBits |= Constants.BUTTON_HOME;
		if (buttonMinus.isPressed())
			buttonBits |= Constants.BUTTON_MINUS;
		if (buttonPlus.isPressed())
			buttonBits |= Constants.BUTTON_PLUS;
		if (buttonLeftTrigger.isPressed())
			buttonBits |= Constants.BUTTON_L;
		if (buttonRightTrigger.isPressed())
			buttonBits |= Constants.BUTTON_R;
		sockets.sendButtonInput(buttonBits);
	}

	@Override
	public void vibrate(int milliseconds) {
		Gdx.input.vibrate(milliseconds);
	}
}
