package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.graphics.TextUtil;

import java.util.Locale;

/**
 * Created by rolando on 5/26/17.
 */
public class StageInfo extends Stage {
    private final TextButton clientLink;
    private final TextButton serverLink;
    private float delta;

    public StageInfo() {
        // Text
        Skin textFieldSkin = new Skin();
        textFieldSkin.add("cursor", new Texture("image/textfield-cursor.png"));
        textFieldSkin.add("selection", new Texture("image/textfield-selection.png"));
        TextField.TextFieldStyle textfieldStyle = new TextField.TextFieldStyle();
        textfieldStyle.font = TextUtil.generateScaledFont(.5f);
        textfieldStyle.fontColor = new Color(1, 1, 1, 1);
        textfieldStyle.cursor = textFieldSkin.getDrawable("cursor");
        textfieldStyle.selection = textFieldSkin.getDrawable("selection");
        TextArea textArea = new TextArea("", textfieldStyle);
        textArea.setBounds(getWidth() * .1f, getHeight() - (getHeight() * .8f),
                getWidth() * .8f, getHeight() * .5f);
        textArea.setDisabled(true);
        char endl = '\n';
        String infoText = String.format(Locale.US,"%s %s", Constants.NAME, Constants.VERSION) + endl + endl +
                "DRC Sim \"Server\" is a required counterpart to this client. Without the server, the client will NOT" +
                " function." + endl + endl +
                "DRC Sim Client is free software: you can redistribute it and/or modify" +
                " it under the terms of the GNU General Public License as published by" +
                " the Free Software Foundation, either version 2 of the License, or" +
                " (at your option) any later version." + endl + endl;
        if (Gdx.app.getType().equals(Application.ApplicationType.Desktop))
            infoText += "Click to continue.";
        else
            infoText += "Tap to continue.";
        textArea.setText(infoText);
        addActor(textArea);
        // Create icon
        Image icon = new Image(new Texture("image/icon-512.png"));
        float size = Gdx.graphics.getHeight() * .2f;
        if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth())
            size = Gdx.graphics.getWidth() * .2f;
        icon.setSize(size, size);
        icon.setPosition(Gdx.graphics.getWidth() / 2 - size / 2,
                Gdx.graphics.getHeight() - icon.getHeight() * 1.2f);
        addActor(icon);
        // Server link
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = TextUtil.generateScaledFont(1f);
        buttonStyle.fontColor = new Color(1, 1, 1, 1);
        serverLink = new TextButton("Server Website", buttonStyle);
        float margin = getWidth() * .05f;
        serverLink.setBounds(getWidth() - serverLink.getWidth() - margin, margin,
                serverLink.getWidth(), serverLink.getHeight());
        serverLink.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://github.com/rolandoislas/drc-sim/wiki");
            }
        });
        addActor(serverLink);
        // Client link
        clientLink = new TextButton("Client Website", buttonStyle);
        clientLink.setBounds(margin, margin, clientLink.getWidth(), clientLink.getHeight());
        clientLink.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://github.com/rolandoislas/drc-sim-client/wiki");
            }
        });
        addActor(clientLink);
    }

    @Override
    public void onBackButtonPressed() {
        Client.setStage(new StageConnect());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.delta += delta;
        if (clientLink.isPressed() || serverLink.isPressed())
            return;
        if (Gdx.input.isTouched() && this.delta >= 1)
            onBackButtonPressed();
    }
}
