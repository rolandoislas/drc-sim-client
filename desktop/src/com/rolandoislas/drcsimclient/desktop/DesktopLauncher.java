package com.rolandoislas.drcsimclient.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.control.ControlController;
import com.rolandoislas.drcsimclient.control.ControlKeyboard;
import com.rolandoislas.drcsimclient.control.ControlTouch;
import com.rolandoislas.drcsimclient.data.ArgumentParser;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.desktop.audio.Audio;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 854;
		config.height = 480;
		config.title = Constants.NAME;
		config.backgroundFPS = 60;
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
			config.addIcon("image/icon-512.png", Files.FileType.Internal);
			config.addIcon("image/icon-256.png", Files.FileType.Internal);
		}
		config.addIcon("image/icon-32.png", Files.FileType.Internal);
		config.addIcon("image/icon-16.png", Files.FileType.Internal);
		ArgumentParser argParser = new ArgumentParser(arg);
		Control[] controls = new Control[argParser.touchControl ? 3 : 2];
		controls[0] = new ControlKeyboard();
		controls[1] = new ControlController();
		if (argParser.touchControl)
			controls[2] = new ControlTouch();
		new LwjglApplication(new Client(controls, new Audio(), argParser), config);
	}
}
