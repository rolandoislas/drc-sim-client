package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Preferences;
import com.rolandoislas.drcsimclient.util.PreferencesUtil;

/**
 * Created by rolando on 3/22/17.
 */
public class ConfigTouch extends Config {
    public static final String TRIGGERS_VISIBLE = "TRIGGERS_VISIBLE";
    private final Preferences config;
    public int triggersVisible;

    public ConfigTouch() {
        config = PreferencesUtil.get("touch");
    }

    @Override
    public void set(String item, int input) {
        config.putInteger(item, input);
        config.flush();
        load();
    }

    @Override
    public void load() {
        triggersVisible = config.getInteger(TRIGGERS_VISIBLE, 1);
        config.flush();
    }

    @Override
    public String get(String key) {
        return config.getString(key); // Why is this a string when the only values are ints?
    }
}
