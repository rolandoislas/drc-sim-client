package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.rolandoislas.drcsimclient.Client;

/**
 * Created by Rolando on 12/29/2016.
 */
public class StageConnect extends Stage {
	public StageConnect() {
		// Font

		// Textfield
		TextField.TextFieldStyle textfieldStyle = new TextField.TextFieldStyle();
		textfieldStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		textfieldStyle.font.getData().setScale(2);
		textfieldStyle.fontColor = new Color(1, 1, 1, 1);
		final TextField textfield = new TextField("", textfieldStyle);
		textfield.setBounds(Gdx.graphics.getWidth() * .15f, Gdx.graphics.getHeight() * .6f,
				Gdx.graphics.getWidth() * .7f, Gdx.graphics.getHeight() * .1f);
		textfield.setMessageText("hostname or ip");
		final Preferences lastHostPreferences = Gdx.app.getPreferences("com.rolandoislas.drcsimclient.lasthost");
		textfield.setText(lastHostPreferences.getString("lastHost"));
		addActor(textfield);
		// Connect Button
		TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.font = textfieldStyle.font;
		TextButton connectButton = new TextButton("Connect", buttonStyle);
		connectButton.setBounds(textfield.getX(), textfield.getY() - textfield.getHeight(),
				connectButton.getWidth(), connectButton.getHeight());
		connectButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				lastHostPreferences.putString("lastHost", textfield.getText());
				lastHostPreferences.flush();
				Client.setStage(new StageControl(textfield.getText()));
			}
		});
		addActor(connectButton);
	}
}
