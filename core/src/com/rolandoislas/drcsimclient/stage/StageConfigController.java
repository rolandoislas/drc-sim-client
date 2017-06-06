package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.ConfigController;
import com.rolandoislas.drcsimclient.config.ConfigKeymap;

/**
 * Created by Rolando on 2/7/2017.
 */
public class StageConfigController extends StageList {
	private ConfigController config;
	final String[][] buttonItems = new String[][]{
		new String[]{"Left Joystick - Horizontal (left/right)", ConfigKeymap.JOYSTICK_LEFT_X},
		new String[]{"Left Joystick - Vertical (up/down)", ConfigKeymap.JOYSTICK_LEFT_Y},
		new String[]{"Right Joystick - Horizontal (left/right)", ConfigKeymap.JOYSTICK_RIGHT_X},
		new String[]{"Right Joystick - Vertical (up/down)", ConfigKeymap.JOYSTICK_RIGHT_Y},
		new String[]{"A", ConfigKeymap.BUTTON_A},
		new String[]{"B", ConfigKeymap.BUTTON_B},
		new String[]{"X", ConfigKeymap.BUTTON_X},
		new String[]{"Y", ConfigKeymap.BUTTON_Y},
		new String[]{"Up", ConfigKeymap.BUTTON_UP},
		new String[]{"Down", ConfigKeymap.BUTTON_DOWN},
		new String[]{"Left", ConfigKeymap.BUTTON_LEFT},
		new String[]{"Right", ConfigKeymap.BUTTON_RIGHT},
		new String[]{"L (trigger)", ConfigKeymap.BUTTON_L},
		new String[]{"R (trigger)", ConfigKeymap.BUTTON_R},
		new String[]{"ZL", ConfigKeymap.BUTTON_ZL},
		new String[]{"ZR", ConfigKeymap.BUTTON_ZR},
		new String[]{"L3", ConfigKeymap.BUTTON_L3},
		new String[]{"R3", ConfigKeymap.BUTTON_R3},
		new String[]{"Minus", ConfigKeymap.BUTTON_MINUS},
		new String[]{"Plus", ConfigKeymap.BUTTON_PLUS},
		new String[]{"Home", ConfigKeymap.BUTTON_HOME},
		new String[]{"Mic Blow", ConfigKeymap.MIC_BLOW}
	};

	public StageConfigController(boolean enableDropdown) {
		super(enableDropdown);
		//Title
		setTitle("Controller Settings");
	}

	@SuppressWarnings("unused")
	public StageConfigController() {
		this(true);
		// Add items
		addItems();
	}

	void addItems() {
		getList().clearItems();
		if (getDropdown().getSelected() == null || getDropdown().getSelected().isEmpty()) {
			addItem("No controllers found.", new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					addItems();
				}
			});
		}
		else {
			config = new ConfigController(getDropdown().getSelected());
			config.load();
			for (final String item[] : buttonItems) {
				addItem(String.format("%s - [%s]", item[0],
						StageConfigInput.getInputDisplayName(StageConfigInput.CONTROLLER, config, item[1])),
						new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						Client.setStage(new StageConfigInput(StageConfigInput.CONTROLLER, config, item[1], item[0],
								getDropdown().getSelected()));
					}
				});
			}
		}
		// Back
		addStageChangeItem("Back", StageSettings.class);
	}

	@Override
	public void onBackButtonPressed() {
		Client.setStage(new StageSettings());
	}
}
