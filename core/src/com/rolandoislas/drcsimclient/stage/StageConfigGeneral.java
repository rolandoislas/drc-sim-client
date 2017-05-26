package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.ConfigGeneral;

/**
 * Created by rolando on 4/13/17.
 */
public class StageConfigGeneral extends StageList {
    private final ConfigGeneral config;

    public StageConfigGeneral() {
        setTitle("General Settings");
        config = new ConfigGeneral();
        config.load();
        addItems();
    }

    @Override
    public void onBackButtonPressed() {
        Client.setStage(new StageSettings());
    }

    void addItems() {
        getList().clearItems();
        addItem("Touch Screen: " + (config.touchScreen == 0 ? "false" : "true"), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.set(ConfigGeneral.TOUCH_SCREEN, config.touchScreen == 0 ? 1 : 0);
                addItems();
            }
        });
        addItem("Vibrate: " + (config.vibrate == 0 ? "false" : "true"), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.set(ConfigGeneral.VIBRATE, config.vibrate == 0 ? 1 : 0);
                addItems();
            }
        });
        // Back
        addStageChangeItem("Back", StageSettings.class);
    }
}
