package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by rolando on 3/22/17.
 */
public class ConfigTouch extends Config {
    public static final String TOUCH_SCREEN = "TOUCH_SCREEN";
    public static final String VIBRATE = "VIBRATE";
    private final Preferences config;
    public int touchScreen;
    public int vibrate;

    public ConfigTouch() {
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
        vibrate = config.getInteger(VIBRATE, 1);
        config.flush();
    }

    @Override
    public String get(String key) {
        return config.getString(key); // Why is this a string when the only values are ints?
    }
}
