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
		addItems();
	}

	@Override
	void addItems() {
		getList().clearItems();
		config.load();
		for (final String item[] : buttonItems) {
			if (item[1].contains("JOYSTICK")) // Not implemented
				continue;
			String button = "";
			try {
				button = Input.Keys.toString(config.getInteger(item[1]));
			}
			catch (IllegalArgumentException ignore) {}
			addItem(String.format("%s - [%s]", item[0], button), new ChangeListener() {
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
		config.putInteger(getInput, keyCode);
		config.flush();
		getInput = "";
		getList().setSelectedIndex(-1);
		addItems();
	}

	@Override
	void getInput() {}
}
