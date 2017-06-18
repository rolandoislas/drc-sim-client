package com.rolandoislas.drcsimclient.config;

/**
 * Created by rolando on 4/13/17.
 */
public class ConfigGeneral extends Config {
    public static final String TOUCH_SCREEN = "TOUCH_SCREEN";
    public static final String VIBRATE = "VIBRATE";
    public boolean touchScreen;
    public boolean vibrate;

    public ConfigGeneral() {
        super("general");
    }

    @Override
    public void load() {
        touchScreen = getBoolean(TOUCH_SCREEN, true);
        vibrate = getBoolean(VIBRATE, true);
    }
}
