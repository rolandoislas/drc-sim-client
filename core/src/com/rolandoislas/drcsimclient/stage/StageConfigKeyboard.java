package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.config.ConfigKeyboard;

/**
 * Created by Rolando on 2/8/2017.
 */
public class StageConfigKeyboard extends StageConfigController {
	private final ConfigKeyboard config;
	private String getInput = "";

	public StageConfigKeyboard() {
		super(false);
		setTitle("Keyboard Settings");
		config = new ConfigKeyboard();
		config.load();
		addItems();
	}

	@Override
	void addItems() {
		getList().clearItems();
		for (final String item[] : buttonItems) {
			if (item[1].contains("JOYSTICK"))
				continue;
			String button = "";
			try {
				int buttonInt = Integer.parseInt(config.get(item[1]));
				button =Input.Keys.toString(buttonInt);
			}
			catch (NumberFormatException ignore) {}
			catch (IllegalArgumentException ignore) {}
			addItem(item[0] + " - [$id]".replace("$id", button), new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					getInput = item[1];
				}
			});
		}
		// Back
		addStageChangeItem("Back", StageSettings.class);
	}

	@Override
	public boolean keyUp(int keyCode) {
		getInput(keyCode);
		return true;
	}

	private void getInput(int keyCode) {
		if (getInput.equals(""))
			return;
		// Save input
		config.set(getInput, keyCode);
		getInput = "";
		getList().setSelectedIndex(-1);
		addItems();
	}

	@Override
	void getInput() {}
}
