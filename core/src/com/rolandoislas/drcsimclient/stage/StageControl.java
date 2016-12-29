package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.Codec;
import com.rolandoislas.drcsimclient.net.NetUtil;
import com.rolandoislas.drcsimclient.net.Sockets;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Rolando on 12/21/2016.
 */
public class StageControl extends Stage {
	private final Sockets sockets;
	private final Touchpad touchpad;
	private final Button buttonA;
	private final TextButton buttonB;
	private final SpriteBatch spritebatch;
	private final Button wiiScreen;
	private Texture wiiImage;
	private int activePointers;

	public StageControl(String ip) {
		// Sockets
		sockets = new Sockets();
		sockets.setIp(ip);
		sockets.connect();
		// Spritebatch
		spritebatch = new SpriteBatch();
		// Screen touchable
		wiiScreen = new Button(new Button.ButtonStyle());
		wiiScreen.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		addActor(wiiScreen);
		// Touchpad
		float x = Gdx.graphics.getWidth() * (100f / 2560);
		float y = Gdx.graphics.getHeight() * (100f / 1440);
		float touchpadWidth = Gdx.graphics.getWidth() * (250f / 2560);
		float touchpadHeight = Gdx.graphics.getHeight() * (250f / 1440);
		Skin touchpadSkin = new Skin();
		touchpadSkin.add("background", new Texture("image/touchpad-background.png"));
		touchpadSkin.add("knob", resizeTexture("image/touchpad-knob.png", touchpadWidth / 2,
				touchpadHeight / 2));
		Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
		touchpadStyle.background = touchpadSkin.getDrawable("background");
		touchpadStyle.knob = touchpadSkin.getDrawable("knob");
		touchpad = new Touchpad(10, touchpadStyle);
		touchpad.setBounds(x, y, touchpadWidth, touchpadHeight);
		addActor(touchpad);
		// A Button
		float buttonWidth = touchpadWidth / 2;
		float buttonHeight = touchpadHeight / 2;
		Skin buttonSkin = new Skin();
		buttonSkin.add("up", resizeTexture("image/button-up.png", buttonWidth, buttonHeight));
		buttonSkin.add("down", resizeTexture("image/button-down.png", buttonWidth, buttonHeight));
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = buttonSkin.getDrawable("up");
		buttonStyle.down = buttonSkin.getDrawable("down");
		buttonStyle.font = new BitmapFont(); // TODO freetype font
		buttonA = new TextButton("A", buttonStyle);
		buttonA.setPosition(Gdx.graphics.getWidth() - x - buttonWidth, y + buttonHeight);
		addActor(buttonA);
		// B Button
		buttonB = new TextButton("B", buttonStyle);
		buttonB.setPosition(Gdx.graphics.getWidth() - x - 2 * buttonWidth, y);
		addActor(buttonB);
	}

	private Texture resizeTexture(String internalPath, float width, float height) {
		return resizeTexture(internalPath, (int)width, (int)height);
	}

	private Texture resizeTexture(String internalPath, int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		Pixmap origPixmap = new Pixmap(Gdx.files.internal(internalPath));
		pixmap.drawPixmap(origPixmap, 0, 0, origPixmap.getWidth(), origPixmap.getHeight(),
				0, 0, width, height);
		return new Texture(pixmap);
	}

	@Override
	public void draw() {
		spritebatch.begin();
		if (wiiImage != null)
			spritebatch.draw(wiiImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		spritebatch.end();
		super.draw();
	}

	@Override
	public void act() {
		super.act();
		// Update wii video frame
		updateWiiVideoFrame();
		// Check touch screen input
		if (wiiScreen.isPressed()) // TODO allow touch input while pressing a button
			sendTouchScreenInput(Gdx.input.getX(), Gdx.input.getY());
		// Check touchpad input
		if (touchpad.isTouched())
			sendTouchpadInput(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
		// Check buttons
		int buttonBits = 0;
		if (buttonA.isPressed())
			buttonBits |= Constants.BUTTON_A;
		if (buttonB.isPressed())
			buttonBits |= Constants.BUTTON_B;
		sendButtonInput(buttonBits);
	}

	private void sendButtonInput(int buttonBits) {
		sockets.sendCommand(Constants.COMMAND_INPUT_BUTTON, Codec.encodeInput(buttonBits));
	}

	private void sendTouchpadInput(float knobPercentX, float knobPercentY) {
		float[] axes = {0, 0, 0, 0};
		axes[0] = knobPercentX;
		axes[1] = knobPercentY * -1;
		sockets.sendCommand(Constants.COMMAND_INPUT_JOYSTICK, Codec.encodeInput(axes));
	}

	private void updateWiiVideoFrame() {
		try {
			// Get and draw image
			byte[] data = NetUtil.recv(this.sockets.socketVid, "video");
			Pixmap pixmap = new Pixmap(data, 0, data.length);
			if (wiiImage != null)
				wiiImage.dispose();
			wiiImage = new Texture(pixmap);
			pixmap.dispose();
		} catch (IOException e) {
			if (!e.getMessage().contains("Read timeout"))
				e.printStackTrace();
		} catch (GdxRuntimeException e) {
			e.printStackTrace();
		}
	}

	private void sendTouchScreenInput(int screenX, int screenY) {
		int[] touchCoords = new int[]{screenX, screenY};
		int[] screenSize = new int[]{Gdx.graphics.getWidth(), Gdx.graphics.getHeight()};
		sockets.sendCommand(Constants.COMMAND_INPUT_TOUCH, Codec.encodeInput(new int[][] {touchCoords, screenSize}));
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		activePointers++;
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		activePointers--;
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
