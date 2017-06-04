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
        addItems();
    }

    @Override
    public void onBackButtonPressed() {
        Client.setStage(new StageSettings());
    }

    void addItems() {
        getList().clearItems();
        config.load();
        addItem(String.format("Triggers Visible: %s", config.triggersVisible), new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                config.putBoolean(ConfigTouch.TRIGGERS_VISIBLE, !config.triggersVisible);
                config.flush();
                addItems();
            }
        });
        // Back
        addStageChangeItem("Back", StageSettings.class);
    }
}
