package com.rolandoislas.drcsimclient.stage;

import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.control.ControlController;
import com.rolandoislas.drcsimclient.control.ControlKeyboard;
import com.rolandoislas.drcsimclient.control.ControlTouch;

/**
 * Created by Rolando on 2/7/2017.
 */
public class StageSettings extends StageList {
	public StageSettings() {
		// Title
		setTitle("Settings");
		// Control settings
		for (Control control : Client.controls) {
			if (control instanceof ControlKeyboard)
				addStageChangeItem("Keyboard Settings", StageConfigKeyboard.class);
			if (control instanceof ControlController)
				addStageChangeItem("Controller Settings", StageConfigController.class);
			if (control instanceof ControlTouch)
				addStageChangeItem("Touch Settings", StageConfigTouch.class);
		}
		addStageChangeItem("General Settings", StageConfigGeneral.class);
		// Info
		addStageChangeItem("Info", StageInfo.class);
		// Back
		addStageChangeItem("Back", StageConnect.class);
	}

	@Override
	public void onBackButtonPressed() {
		Client.setStage(new StageConnect());
	}
}
