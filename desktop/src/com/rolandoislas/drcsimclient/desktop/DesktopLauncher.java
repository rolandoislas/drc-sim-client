package com.rolandoislas.drcsimclient.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.control.ControlBeam;
import com.rolandoislas.drcsimclient.control.ControlController;
import com.rolandoislas.drcsimclient.control.ControlKeyboard;
import com.rolandoislas.drcsimclient.data.ArgumentParser;
import com.rolandoislas.drcsimclient.desktop.audio.Audio;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.title = "DRC Sim";
		config.backgroundFPS = 30;
		if (System.getProperties().getProperty("os.name").toLowerCase().contains("mac")) {
			config.addIcon("image/icon-512.png", Files.FileType.Internal);
			config.addIcon("image/icon-256.png", Files.FileType.Internal);
		}
		config.addIcon("image/icon-32.png", Files.FileType.Internal);
		config.addIcon("image/icon-16.png", Files.FileType.Internal);
		Control[] controls = new Control[] {new ControlKeyboard(), new ControlController(),
				new ControlBeam()};
		new LwjglApplication(new Client(controls, new Audio(), new ArgumentParser(arg)), config);
	}
}
