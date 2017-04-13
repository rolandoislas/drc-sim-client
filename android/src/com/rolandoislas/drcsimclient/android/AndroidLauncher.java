package com.rolandoislas.drcsimclient.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.android.audio.Audio;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.control.ControlController;
import com.rolandoislas.drcsimclient.control.ControlTouch;
import com.rolandoislas.drcsimclient.data.ArgumentParser;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		config.useWakelock = true;
		Control[] controls = new Control[] {new ControlTouch(), new ControlController()};
		ArgumentParser args = new ArgumentParser(new String[]{"-f"});
		initialize(new Client(controls, new Audio(), args), config);
	}
}
