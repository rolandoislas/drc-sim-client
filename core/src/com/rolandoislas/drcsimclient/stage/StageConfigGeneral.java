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
        addItems();
    }

    @Override
    public void onBackButtonPressed() {
        Client.setStage(new StageSettings());
    }

    void addItems() {
        getList().clearItems();
        config.load();
        addItem(String.format("Touch Screen: %s", config.touchScreen), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.putBoolean(ConfigGeneral.TOUCH_SCREEN, !config.touchScreen);
                config.flush();
                addItems();
            }
        });
        addItem(String.format("Vibrate: %s", config.vibrate), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.putBoolean(ConfigGeneral.VIBRATE, !config.vibrate);
                config.flush();
                addItems();
            }
        });
        // Back
        addStageChangeItem("Back", StageSettings.class);
    }
}
