package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.graphics.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by Rolando on 2/14/2017.
 */
public class StageLoad extends Stage {
	private final Image icon;
	private final Label loading;
	private int skipTicks = 1;
	private ArrayList<Float> fontSizes = new ArrayList<Float>(Arrays.asList(1f, 1.5f, 2f));
	private int percent = 0;
	private boolean doGenerate;
	private boolean loadControllers = false;

	public StageLoad() {
		// Create icon
		icon = new Image(new Texture("image/icon-512.png"));
		int size = Gdx.graphics.getHeight();
		if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())
			size = Gdx.graphics.getWidth();
		icon.setSize(size, size);
		icon.setPosition(Gdx.graphics.getWidth() / 2 - size / 2,
				Gdx.graphics.getHeight() / 2 - icon.getHeight() / 2);
		addActor(icon);
		// Loading Text
		Label.LabelStyle loadingStyle = new Label.LabelStyle();
		loadingStyle.font = new BitmapFont(Gdx.files.internal("font/collvetica.fnt"));
		loadingStyle.font.getData().setScale(Gdx.graphics.getHeight() * 2 / 720);
		loading = new Label("DRC Sim", loadingStyle);
		loading.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .2f);
		loading.setAlignment(Align.center);
		addActor(loading);
	}

	@Override
	public void act(float delta) {
		if (this.skipTicks > 0) {
			this.skipTicks--;
			return;
		}
		if (doGenerate) {
			TextUtil.generateScaledFont(fontSizes.get(0));
			fontSizes.remove(0);
			doGenerate = false;
			skipTicks++;
			percent += 25;
			return;
		}
		else if (fontSizes.size() > 0) {
			loading.setText(String.format(Locale.US,"Generating Fonts %d%%", percent));
			doGenerate = true;
			skipTicks++;
			return;
		}
		else if (!loadControllers) {
			loading.setText(String.format(Locale.US,"Loading Controllers %d%%", percent));
			skipTicks++;
			loadControllers = true;
			return;
		}
		// Load Controllers - A Windows issue causes this to hangs occasionally.
		Controllers.getControllers();
		//loading.setText(String.format(Locale.US,"Launching %d%%", 100));
		// Set Connect Stage - Font generation takes a while on mobile.
		Client.setStage(new StageConnect());
	}

	@Override
	public void onBackButtonPressed() {
		Gdx.app.exit();
	}
}
