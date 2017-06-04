package com.rolandoislas.drcsimclient.config;

/**
 * Created by rolando on 6/2/17.
 */
public abstract class ConfigKeymap extends Config {
    public static final String BUTTON_A = "BUTTON_A";
    public static final String BUTTON_B = "BUTTON_B";
    public static final String BUTTON_X = "BUTTON_X";
    public static final String BUTTON_Y = "BUTTON_Y";
    public static final String BUTTON_UP = "BUTTON_UP";
    public static final String BUTTON_DOWN = "BUTTON_DOWN";
    public static final String BUTTON_LEFT = "BUTTON_LEFT";
    public static final String BUTTON_RIGHT = "BUTTON_RIGHT";
    public static final String BUTTON_L = "BUTTON_L";
    public static final String BUTTON_R = "BUTTON_R";
    public static final String BUTTON_ZL = "BUTTON_ZL";
    public static final String BUTTON_ZR = "BUTTON_ZR";
    public static final String BUTTON_L3 = "BUTTON_L3";
    public static final String BUTTON_R3 = "BUTTON_R3";
    public static final String BUTTON_MINUS = "BUTTON_MINUS";
    public static final String BUTTON_PLUS = "BUTTON_PLUS";
    public static final String BUTTON_HOME = "BUTTON_HOME";
    public static final String JOYSTICK_LEFT_X = "JOYSTICK_LEFT_X";
    public static final String JOYSTICK_LEFT_Y = "JOYSTICK_LEFT_Y";
    public static final String JOYSTICK_RIGHT_X = "JOYSTICK_RIGHT_X";
    public static final String JOYSTICK_RIGHT_Y = "JOYSTICK_RIGHT_Y";
    public static final String MIC_BLOW = "MIC_BLOW";
    public int buttonA;
    public int buttonB;
    public int buttonX;
    public int buttonY;
    public int buttonUp;
    public int buttonDown;
    public int buttonLeft;
    public int buttonRight;
    public int buttonL;
    public int buttonR;
    public int buttonZL;
    public int buttonZR;
    public int buttonL3;
    public int buttonR3;
    public int buttonMinus;
    public int buttonPlus;
    public int buttonHome;
    public int micBlow;
    public int joystickLeftX;
    public int joystickLeftY;
    public int joystickRightX;
    public int joystickRightY;

    public ConfigKeymap(String configName) {
        super(configName);
    }
}
