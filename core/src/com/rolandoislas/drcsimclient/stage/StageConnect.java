package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.graphics.TextUtil;
import com.rolandoislas.drcsimclient.util.PreferencesUtil;
import com.rolandoislas.drcsimclient.util.logging.Logger;

/**
 * Created by Rolando on 12/29/2016.
 */
public class StageConnect extends Stage {
	private final Preferences lastHostPreferences;
	private final TextField textfield;
	private final Label labelError;
	private boolean connectOnAct = false;

	public StageConnect(String message) {
		float marginX = Gdx.graphics.getWidth() * 10f / 720f;
		float marginY = Gdx.graphics.getHeight() * 10f / 720f;
		// Textfield
		Skin textFieldSkin = new Skin();
		textFieldSkin.add("cursor", new Texture("image/textfield-cursor.png"));
		textFieldSkin.add("selection", new Texture("image/textfield-selection.png"));
		TextField.TextFieldStyle textfieldStyle = new TextField.TextFieldStyle();
		textfieldStyle.font = TextUtil.generateScaledFont(2);
		textfieldStyle.fontColor = new Color(1, 1, 1, 1);
		textfieldStyle.cursor = textFieldSkin.getDrawable("cursor");
		textfieldStyle.selection = textFieldSkin.getDrawable("selection");
		textfield = new TextField("", textfieldStyle);
		textfield.setBounds(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .6f,
				Gdx.graphics.getWidth() * .7f, Gdx.graphics.getHeight() * .1f);
		textfield.setMessageText("hostname or ip");
		lastHostPreferences = PreferencesUtil.get("general");
		textfield.setText(lastHostPreferences.getString("lastHost"));
		addActor(textfield);
		// Connect Button
		TextButton.TextButtonStyle connectButtonStyle = new TextButton.TextButtonStyle();
		connectButtonStyle.font = TextUtil.generateScaledFont(1.5f);
		if (textfield.getText().equals(""))
			connectButtonStyle.fontColor = new Color(1, 1, 1, 1);
		else
			connectButtonStyle.fontColor = new Color(.7f, .7f, .7f, 1);
		TextButton connectButton = new TextButton("Connect", connectButtonStyle);
		connectButton.setBounds(textfield.getX() + textfield.getWidth() * .1f,
				textfield.getY() - textfield.getHeight() - textfield.getHeight() * .5f,
				connectButton.getWidth(), connectButton.getHeight());
		connectButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				connect();
			}
		});
		addActor(connectButton);
		// Search Button
		TextButton searchButton = new TextButton("Search", connectButtonStyle);
		searchButton.setBounds(connectButton.getX() + connectButton.getWidth() + marginX * 6,
				connectButton.getY(), searchButton.getWidth(), searchButton.getHeight());
		searchButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Client.setStage(new StageSearch());
			}
		});
		addActor(searchButton);
		// Error Message
		Label.LabelStyle errorLabelStyle = new Label.LabelStyle();
		errorLabelStyle.font = TextUtil.generateScaledFont(1);
		errorLabelStyle.fontColor = textfieldStyle.fontColor;
		labelError = new Label(message, errorLabelStyle);
		float iconSize = Gdx.graphics.getWidth() * .1f;
		labelError.setBounds(iconSize, 10, Gdx.graphics.getWidth() - iconSize * 2,
				Gdx.graphics.getHeight() * .1f);
		labelError.setAlignment(Align.center);
		addActor(labelError);
		// Set the error label text to an empty string after a few seconds
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3500);
				} catch (InterruptedException e) {
					Logger.exception(e);
				}
				labelError.setText("");
			}
		}).start();
		// Settings Button
		Skin settingsButtonSkin = new Skin();
		settingsButtonSkin.add("image", new Texture("image/settings-icon.png"));
		Button.ButtonStyle settingsButtonStyle = new Button.ButtonStyle();
		settingsButtonStyle.up = settingsButtonSkin.getDrawable("image");
		settingsButtonStyle.down = connectButtonStyle.up;
		settingsButtonStyle.checked = connectButtonStyle.up;
		Button settingsButton = new Button(settingsButtonStyle);
		settingsButton.setSize(iconSize, iconSize);
		settingsButton.setPosition(Gdx.graphics.getWidth() - settingsButton.getWidth() - marginX, marginY);
		settingsButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Client.setStage(new StageSettings());
			}
		});
		addActor(settingsButton);
		// Title
		Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
		titleLabelStyle.font = TextUtil.generateScaledFont(2);
		Label title = new Label(Constants.NAME, titleLabelStyle);
		title.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
		title.setPosition(0, Gdx.graphics.getHeight() - title.getHeight() - marginY);
		title.setAlignment(Align.center);
		addActor(title);
		// Info Button
		Skin infoButtonSkin = new Skin();
		infoButtonSkin.add("image", new Texture("image/icon-512.png"));
		Button.ButtonStyle infoButtonStyle = new Button.ButtonStyle();
		infoButtonStyle.up = infoButtonSkin.getDrawable("image");
		infoButtonStyle.down = infoButtonStyle.up;
		infoButtonStyle.checked = infoButtonStyle.up;
		Button infoButton = new Button(infoButtonStyle);
		infoButton.setBounds(marginX, 0, iconSize, iconSize);
		infoButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Client.setStage(new StageInfo());
			}
		});
		addActor(infoButton);
	}

	StageConnect(String hostname, boolean connectOnAct) {
		this();
		this.connectOnAct = connectOnAct;
		textfield.setText(hostname);
	}

	@Override
	public void act() {
		// Check IP CLI arg
		if (!Client.args.ip.isEmpty() || connectOnAct)
			connect();
	}

	private void connect() {
		Gdx.input.setOnscreenKeyboardVisible(false);
		lastHostPreferences.putString("lastHost", textfield.getText());
		lastHostPreferences.flush();
		labelError.setText("Connecting");
		final String ip = Client.args.ip.isEmpty() ? textfield.getText() : Client.args.ip;
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String connected = Client.connect(ip);
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						if (!(Client.getStage() instanceof StageConnect))
							return;
						if (connected.isEmpty())
							Client.setStage(new StageControl());
						else
							Client.setStage(new StageConnect(connected));

					}
				});
			}
		}).start();
	}

	public StageConnect() {
		this("");
	}

	@Override
	public void onBackButtonPressed() {
		if (Gdx.app.getType() != Application.ApplicationType.Desktop)
			Gdx.app.exit();
	}

	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Input.Keys.ENTER) {
			Gdx.input.setOnscreenKeyboardVisible(false);
			connect();
		}
		return super.keyDown(keyCode);
	}
}
