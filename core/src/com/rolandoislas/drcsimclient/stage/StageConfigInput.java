package com.rolandoislas.drcsimclient.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.config.ConfigKeymap;
import com.rolandoislas.drcsimclient.graphics.TextUtil;

import java.util.Locale;

/**
 * Created by rolando on 6/5/17.
 */
public class StageConfigInput extends Stage {
    public static final int CONTROLLER = 0;
    public static final int KEYBOARD = 1;
    private final int type;
    private final ConfigKeymap config;
    private final String key;
    private final String controllerName;
    private final String  displayName;

    public StageConfigInput(int type, ConfigKeymap config, String key, String displayName, String controllerName) {
        this.type = type;
        this.config = config;
        this.key = key;
        this.displayName = displayName;
        this.controllerName = controllerName;
        // Input name
        float marginY = Gdx.graphics.getHeight() * 10f / 720f;
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle();
        titleLabelStyle.font = TextUtil.generateScaledFont(1.5f);
        Label title = new Label(String.format("Setting Input\n%s", displayName), titleLabelStyle);
        title.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
        title.setPosition(0, Gdx.graphics.getHeight() - title.getHeight() * 2 - marginY);
        title.setAlignment(Align.center);
        addActor(title);
        // Current name
        Label.LabelStyle currentLabelStyle = new Label.LabelStyle();
        currentLabelStyle.font = TextUtil.generateScaledFont(1f);
        Label current = new Label(String.format("Current value: %s", getInputDisplayName(type, config, key)),
                currentLabelStyle);
        current.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * .1f);
        current.setPosition(0,
                Gdx.graphics.getHeight() / 2 - current.getHeight() / 2);
        current.setAlignment(Align.center);
        addActor(current);
        // Clear Button
        TextButton.TextButtonStyle connectButtonStyle = new TextButton.TextButtonStyle();
        connectButtonStyle.font = TextUtil.generateScaledFont(1.5f);
        connectButtonStyle.fontColor = new Color(1, 1, 1, 1);
        TextButton clearButton = new TextButton("Unbind", connectButtonStyle);
        clearButton.setBounds(Gdx.graphics.getWidth() / 2 - clearButton.getWidth() - Gdx.graphics.getWidth() * .05f,
                marginY,
                clearButton.getWidth(), clearButton.getHeight());
        clearButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                unbind();
            }
        });
        addActor(clearButton);
        // Reset Button
        TextButton resetButton = new TextButton("Reset", connectButtonStyle);
        resetButton.setBounds(Gdx.graphics.getWidth() / 2 + Gdx.graphics.getWidth() * .05f,
                clearButton.getY(), resetButton.getWidth(), resetButton.getHeight());
        resetButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reset();
            }
        });
        addActor(resetButton);
        // Reset Button
        TextButton backButton = new TextButton("Back", connectButtonStyle);
        backButton.setBounds(marginY, marginY,
                backButton.getWidth(), backButton.getHeight());
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onBackButtonPressed();
            }
        });
        addActor(backButton);
    }

    public static String getInputDisplayName(int type, ConfigKeymap config, String key) {
        ConfigKeymap.Input input = config.getInput(key, -1, -1, -1);
        if (input.getInput() < 0)
            return "NONE";
        if (type == KEYBOARD && input.getInput() <= 255)
            return Input.Keys.toString(input.getInput());
        else if (type == CONTROLLER) {
            if (input.getType() == ConfigKeymap.Input.TYPE_AXIS)
                return String.format(Locale.US, "Axis %d", input.getInput());
            else if (input.getType() == ConfigKeymap.Input.TYPE_POV) {
                String pov = String.valueOf(input.getExtra());
                for (PovDirection dir : PovDirection.values())
                    if (dir.ordinal() == input.getExtra())
                        pov = dir.name();
                return String.format(Locale.US, "D-pad %d %s", input.getInput(), pov);
            }
            else if (input.getType() == ConfigKeymap.Input.TYPE_BUTTON)
                return String.format(Locale.US, "Button %d", input.getInput());
        }
        return String.format(Locale.US, "%d", input.getInput());
    }

    public StageConfigInput(int type, ConfigKeymap config, String key, String displayName) {
        this(type, config, key, displayName, null);
    }

    @Override
    public void resize(int width, int height) {
        Client.setStage(new StageConfigInput(type, config, key, displayName, controllerName));
    }

    private void reset() {
        config.remove(key);
        config.load();
        config.flush();
        Client.setStage(new StageConfigInput(type, config, key, displayName, controllerName));
    }

    private void unbind() {
        config.putInput(key, -1, -1, -1);
        config.load();
        config.flush();
        Client.setStage(new StageConfigInput(type, config, key, displayName, controllerName));
    }

    @Override
    public void act() {
        super.act();
        if (type == CONTROLLER)
            pollInputController();
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (type != KEYBOARD)
            return true;
        saveInputKeyboard(keyCode);
        return true;
    }

    private void saveInputKeyboard(int keyCode) {
        config.putInput(this.key, ConfigKeymap.Input.TYPE_BUTTON, keyCode, 0);
        config.load();
        config.flush();
        Client.setStage(new StageConfigInput(type, config, key, displayName, controllerName));
    }

    private void pollInputController() {
        int input = -1;
        int input_extra = -1;
        int input_type = -1;
        main:
        for (Controller controller : Controllers.getControllers()) {
            if (!controller.getName().equals(this.controllerName))
                continue;
            // Check axes
            for (int axis = 0; axis < 1000; axis++) {
                if (Math.abs(controller.getAxis(axis)) > .2) {
                    input_type = ConfigKeymap.Input.TYPE_AXIS;
                    input = axis;
                    break main;
                }
            }
            // Only allow an axis to be set for joystick input.
            // TODO Map all 4(8) axes to allow any input to control the joystick(s). 4 directions per joystick
            if (key.contains("JOYSTICK"))
                break;
            // Check dpad/pov
            for (int pov = 0; pov < 1000; pov++) {
                PovDirection povDirection = controller.getPov(pov);
                if (!povDirection.equals(PovDirection.center)) {
                    input_type = ConfigKeymap.Input.TYPE_POV;
                    input = pov;
                    input_extra = povDirection.ordinal();
                    break main;
                }
            }
            // Check buttons
            for (int button = 0; button < 1000; button++) {
                if (controller.getButton(button)) {
                    input_type = ConfigKeymap.Input.TYPE_BUTTON;
                    input = button;
                    break main;
                }
            }
            return;
        }
        if (input < 0)
            return;
        // Save input
        config.putInput(this.key, input_type, input, input_extra);
        config.load();
        config.flush();
        Client.setStage(new StageConfigInput(type, config, key, displayName, controllerName));
    }

    @Override
    public void onBackButtonPressed() {
        switch (type) {
            case CONTROLLER:
                Client.setStage(new StageConfigController());
                break;
            case KEYBOARD:
                Client.setStage(new StageConfigKeyboard());
                break;
        }
    }
}
