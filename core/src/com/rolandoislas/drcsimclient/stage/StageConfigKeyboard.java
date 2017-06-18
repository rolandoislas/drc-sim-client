package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.ConfigKeyboard;

/**
 * Created by Rolando on 2/8/2017.
 */
public class StageConfigKeyboard extends StageConfigController {
	private final ConfigKeyboard config;

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
			addItem(String.format("%s - [%s]", item[0],
					StageConfigInput.getInputDisplayName(StageConfigInput.KEYBOARD, config, item[1])),
					new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					Client.setStage(new StageConfigInput(StageConfigInput.KEYBOARD, config, item[1], item[0]));
				}
			});
		}
		// Back
		addStageChangeItem("Back", StageSettings.class);
	}
}
