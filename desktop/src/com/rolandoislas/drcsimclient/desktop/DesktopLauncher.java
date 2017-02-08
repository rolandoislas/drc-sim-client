package com.rolandoislas.drcsimclient.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.control.ControlController;
import com.rolandoislas.drcsimclient.control.ControlKeyboard;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		Control[] controls = new Control[] {new ControlKeyboard(), new ControlController()};
		new LwjglApplication(new Client(controls), config);
	}
}
