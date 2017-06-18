package com.rolandoislas.drcsimclient.config;

/**
 * Created by rolando on 3/22/17.
 */
public class ConfigTouch extends Config {
    public static final String TRIGGERS_VISIBLE = "TRIGGERS_VISIBLE";
    public boolean triggersVisible;

    public ConfigTouch() {
        super("touch");
    }

    @Override
    public void load() {
        triggersVisible = getBoolean(TRIGGERS_VISIBLE, true);
        flush();
    }
}
