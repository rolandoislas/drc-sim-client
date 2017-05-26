package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.ConfigTouch;

/**
 * Created by rolando on 3/22/17.
 */
public class StageConfigTouch extends StageList {
    private final ConfigTouch config;

    public StageConfigTouch() {
        setTitle("Touch Settings");
        config = new ConfigTouch();
        config.load();
        addItems();
    }

    @Override
    public void onBackButtonPressed() {
        Client.setStage(new StageSettings());
    }

    void addItems() {
        getList().clearItems();
        addItem("Triggers Visible: " + (config.triggersVisible == 0 ? "false" : "true"), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.set(ConfigTouch.TRIGGERS_VISIBLE, config.triggersVisible == 0 ? 1 : 0);
                addItems();
            }
        });
        // Back
        addStageChangeItem("Back", StageSettings.class);
    }
}
