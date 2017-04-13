package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by rolando on 4/13/17.
 */
public class ConfigGeneral extends Config {
    public static final String TOUCH_SCREEN = "TOUCH_SCREEN";
    private final Preferences config;
    public int touchScreen;

    public ConfigGeneral() {
        config = Gdx.app.getPreferences("com.rolandoislas.drcsimclient.config.touch");
    }

    @Override
    public void set(String item, int input) {
        config.putInteger(item, input);
        config.flush();
        load();
    }

    @Override
    public void load() {
        touchScreen = config.getInteger(TOUCH_SCREEN, 1); // Who needs a boolean?
    }

    @Override
    public String get(String key) {
        return config.getString(key);
    }
}
