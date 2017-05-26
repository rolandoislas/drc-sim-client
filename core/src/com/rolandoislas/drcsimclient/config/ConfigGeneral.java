package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Preferences;
import com.rolandoislas.drcsimclient.util.PreferencesUtil;

/**
 * Created by rolando on 4/13/17.
 */
public class ConfigGeneral extends Config {
    public static final String TOUCH_SCREEN = "TOUCH_SCREEN";
    public static final String VIBRATE = "VIBRATE";
    private final Preferences config;
    public int touchScreen;
    public int vibrate;

    public ConfigGeneral() {
        config = PreferencesUtil.get("general");
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
    }

    @Override
    public String get(String key) {
        return config.getString(key);
    }
}
