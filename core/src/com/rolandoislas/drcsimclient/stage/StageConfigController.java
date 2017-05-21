package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.Config;
import com.rolandoislas.drcsimclient.config.ConfigController;
import com.rolandoislas.drcsimclient.config.ConfigControllerConfig;

/**
 * Created by Rolando on 2/7/2017.
 */
public class StageConfigController extends StageList {
	private final ConfigController config;
	private String getInput = "";
	final String[][] buttonItems = new String[][]{
		new String[]{"Left Joystick - Horizontal (left/right)", ConfigControllerConfig.JOYSTICK_LEFT_X},
		new String[]{"Left Joystick - Vertical (up/down)", ConfigControllerConfig.JOYSTICK_LEFT_Y},
		new String[]{"Right Joystick - Horizontal (left/right)", ConfigControllerConfig.JOYSTICK_RIGHT_X},
		new String[]{"Right Joystick - Horizontal (up/down)", ConfigControllerConfig.JOYSTICK_RIGHT_Y},
		new String[]{"A", ConfigControllerConfig.BUTTON_A},
		new String[]{"B", ConfigControllerConfig.BUTTON_B},
		new String[]{"X", ConfigControllerConfig.BUTTON_X},
		new String[]{"Y", ConfigControllerConfig.BUTTON_Y},
		new String[]{"Up", ConfigControllerConfig.BUTTON_UP},
		new String[]{"Down", ConfigControllerConfig.BUTTON_DOWN},
		new String[]{"Left", ConfigControllerConfig.BUTTON_LEFT},
		new String[]{"Right", ConfigControllerConfig.BUTTON_RIGHT},
		new String[]{"L (trigger)", ConfigControllerConfig.BUTTON_L},
		new String[]{"R (trigger)", ConfigControllerConfig.BUTTON_R},
		new String[]{"ZL", ConfigControllerConfig.BUTTON_ZL},
		new String[]{"ZR", ConfigControllerConfig.BUTTON_ZR},
		new String[]{"L3", ConfigControllerConfig.BUTTON_L3},
		new String[]{"R3", ConfigControllerConfig.BUTTON_R3},
		new String[]{"Minus", ConfigControllerConfig.BUTTON_MINUS},
		new String[]{"Plus", ConfigControllerConfig.BUTTON_PLUS},
		new String[]{"Home", ConfigControllerConfig.BUTTON_HOME},
		new String[]{"Mic Blow", ConfigControllerConfig.MIC_BLOW}
	};

	public StageConfigController(boolean enableDropdown) {
		super(enableDropdown);
		//Title
		setTitle("Controller Settings");
		// Config
		config = new ConfigController();
	}

	@SuppressWarnings("unused")
	public StageConfigController() {
		this(true);
		// Add items
		addItems();
	}

	void addItems() {
		getList().clearItems();
		ConfigControllerConfig controllerConfig = config.get(getDropdown().getSelected());
		if (controllerConfig != null) {
			for (final String item[] : buttonItems) {
				String button = controllerConfig.get(item[1]);
				addItem(item[0] + " - [$id]".replace("$id", button), new ChangeListener() {
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
				} else if (getInput.equals(Config.BUTTON_UP) ||
						getInput.equals(Config.BUTTON_DOWN) ||
						getInput.equals(Config.BUTTON_LEFT) ||
						getInput.equals(Config.BUTTON_RIGHT)) {
					for (int pov = 0; pov < 2; pov++)
						if (!controller.getPov(pov).equals(PovDirection.center))
							input = pov;
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
		ConfigControllerConfig controllerConfig = config.get(getDropdown().getSelected());
		controllerConfig.set(getInput, input);
		getInput = "";
		getList().setSelectedIndex(-1);
		addItems();
	}

	@Override
	public void onBackButtonPressed() {
		Client.setStage(new StageSettings());
	}
}
