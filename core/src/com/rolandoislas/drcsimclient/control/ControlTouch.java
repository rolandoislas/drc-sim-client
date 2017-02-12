package com.rolandoislas.drcsimclient.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.rolandoislas.drcsimclient.graphics.TextureUtil;
import com.rolandoislas.drcsimclient.data.Constants;
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

	@Override
	public void init(StageControl stage) {
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
		// Home Button
		buttonHome = new TextButton("H", buttonStyle);
		buttonHome.setPosition(Gdx.graphics.getWidth() / 2 - buttonWidth / 2, 10);
		stage.addActor(buttonHome);
		// Left Trigger
		float triggerWidth = Gdx.graphics.getWidth() * .1f;
		float triggerHeight = Gdx.graphics.getHeight() * .1f;
		buttonLeftTrigger = new Button(new Button.ButtonStyle());
		buttonLeftTrigger.setBounds(0, Gdx.graphics.getHeight() - triggerHeight, triggerWidth, triggerHeight);
		stage.addActor(buttonLeftTrigger);
		// Right Trigger
		buttonRightTrigger = new Button(new Button.ButtonStyle());
		buttonRightTrigger.setBounds(Gdx.graphics.getWidth() - triggerWidth,
				Gdx.graphics.getHeight() - triggerHeight, triggerWidth, triggerHeight);
		stage.addActor(buttonRightTrigger);
	}

	@Override
	public void update() {
		// Check touchpad (joystick) input
		if (touchpad.isTouched())
			sockets.sendJoystickInput(touchpad.getKnobPercentX(), touchpad.getKnobPercentY() * -1);
		// Check buttons
		int buttonBits = 0;
		if (buttonA.isPressed())
			buttonBits |= Constants.BUTTON_A;
		if (buttonB.isPressed())
			buttonBits |= Constants.BUTTON_B;
		if (buttonHome.isPressed())
			buttonBits |= Constants.BUTTON_HOME;
		if (buttonLeftTrigger.isPressed())
			buttonBits |= Constants.BUTTON_L;
		if (buttonRightTrigger.isPressed())
			buttonBits |= Constants.BUTTON_R;
		sockets.sendButtonInput(buttonBits);
	}
}
