package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
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
	private String getInput = "";
	final String[][] buttonItems = new String[][]{
		new String[]{"Left Joystick - Horizontal (left/right)", ConfigKeymap.JOYSTICK_LEFT_X},
		new String[]{"Left Joystick - Vertical (up/down)", ConfigKeymap.JOYSTICK_LEFT_Y},
		new String[]{"Right Joystick - Horizontal (left/right)", ConfigKeymap.JOYSTICK_RIGHT_X},
		new String[]{"Right Joystick - Horizontal (up/down)", ConfigKeymap.JOYSTICK_RIGHT_Y},
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
				String button = String.valueOf(config.getInteger(item[1]));
				addItem(String.format("%s - [%s]", item[0], button), new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						getInput = item[1];
					}
				});
			}
		}
		// Back
		addStageChangeItem("Back", StageSettings.class);
	}

	@Override
	public void act() {
		super.act();
		getInput();
	}

	void getInput() {
		if (getInput.equals(""))
			return;
		int input = -1;
		// Poll input
		for (Controller controller : Controllers.getControllers()) {
			if (controller.getName().equals(getDropdown().getSelected())) {
				if (getInput.contains("JOYSTICK")) {
					for (int axis = 0; axis < 4; axis++)
						if (Math.abs(controller.getAxis(axis)) > .2)
							input = axis;
				} else if (getInput.equals(ConfigKeymap.BUTTON_UP) ||
						getInput.equals(ConfigKeymap.BUTTON_DOWN) ||
						getInput.equals(ConfigKeymap.BUTTON_LEFT) ||
						getInput.equals(ConfigKeymap.BUTTON_RIGHT)) {
					for (int pov = 0; pov < 2; pov++) {
						if (!controller.getPov(pov).equals(PovDirection.center))
							input = pov;
					}
				} else {
					for (int button = 0; button < 100; button++)
						if (controller.getButton(button))
							input = button;
				}
				break;
			}
		}
		// Check input
		if (input < 0)
			return;
		// Save input
		config.putInteger(getInput, input);
		config.flush();
		getInput = "";
		getList().setSelectedIndex(-1);
		addItems();
	}

	@Override
	public void onBackButtonPressed() {
		Client.setStage(new StageSettings());
	}
}
