package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by Rolando on 2/6/2017.
 */
public class ConfigController {
	private HashMap<String, ConfigControllerConfig> configs = new HashMap<String, ConfigControllerConfig>();

	private void loadAll() {
		Array<Controller> controllers = Controllers.getControllers();
		for (int controllerN = 0; controllerN < controllers.size; controllerN++) {
			Controller controller = controllers.get(controllerN);
			ConfigControllerConfig config = configs.get(controller.getName());
			if (config == null) {
				config = new ConfigControllerConfig(controller.getName());
				config.load();
				configs.put(controller.getName(), config);
			}
		}
	}

	public ConfigControllerConfig get(String name) {
		ConfigControllerConfig config = configs.get(name);
		if (config == null)
			loadAll();
		return configs.get(name);
	}
}
