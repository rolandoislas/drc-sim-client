package com.rolandoislas.drcsimclient.config;

import java.util.Locale;

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
    public Input buttonA;
    public Input buttonB;
    public Input buttonX;
    public Input buttonY;
    public Input buttonUp;
    public Input buttonDown;
    public Input buttonLeft;
    public Input buttonRight;
    public Input buttonL;
    public Input buttonR;
    public Input buttonZL;
    public Input buttonZR;
    public Input buttonL3;
    public Input buttonR3;
    public Input buttonMinus;
    public Input buttonPlus;
    public Input buttonHome;
    public Input micBlow;
    public Input joystickLeftX;
    public Input joystickLeftY;
    public Input joystickRightX;
    public Input joystickRightY;

    ConfigKeymap(String configName) {
        super(configName);
    }

    /**
     * Store an input.
     * @param key key that will be used as the place for storage
     * @param inputType type of input
     * @param input main input value
     * @param inputExtra extra input value
     */
    public void putInput(String key, int inputType, int input, int inputExtra) {
        Input in = new Input(inputType, input, inputExtra);
        putString(key, in.toString());
    }

    /**
     * Store an input
     * @param key key to use for storage
     * @param input input
     */
    public void putInput(String key, Input input) {
        putString(key, input.toString());
    }

    /**
     * Get a stored input or return an input with the provided defaults.
     * @param key key that will be used as the place for storage
     * @param inputType type of input
     * @param input main input value
     * @param inputExtra extra input value
     * @return input from the key's value or the defaults provided
     */
    public Input getInput(String key, int inputType, int input, int inputExtra) {
        String inputString = getString(key);
        if (inputString.isEmpty())
            return new Input(inputType, input, inputExtra);
        return new Input(inputString, inputType, input, inputExtra);
    }

    /**
     * Save all inputs.
     */
    public void save() {
        putInput(BUTTON_A, buttonA);
        putInput(BUTTON_B, buttonB);
        putInput(BUTTON_X, buttonX);
        putInput(BUTTON_Y, buttonY);
        putInput(BUTTON_UP, buttonUp);
        putInput(BUTTON_DOWN, buttonDown);
        putInput(BUTTON_LEFT, buttonLeft);
        putInput(BUTTON_RIGHT, buttonRight);
        putInput(BUTTON_L, buttonL);
        putInput(BUTTON_R, buttonR);
        putInput(BUTTON_ZL, buttonZL);
        putInput(BUTTON_ZR, buttonZR);
        putInput(BUTTON_L3, buttonL3);
        putInput(BUTTON_R3, buttonR3);
        putInput(BUTTON_MINUS, buttonMinus);
        putInput(BUTTON_PLUS, buttonPlus);
        putInput(BUTTON_HOME, buttonHome);
        putInput(JOYSTICK_LEFT_X, joystickLeftX);
        putInput(JOYSTICK_LEFT_Y, joystickLeftY);
        putInput(JOYSTICK_RIGHT_X, joystickRightX);
        putInput(JOYSTICK_RIGHT_Y, joystickRightY);
        putInput(MIC_BLOW, micBlow);
        flush();
    }

    public class Input {
        public static final int TYPE_AXIS = 0;
        public static final int TYPE_POV = 1;
        public static final int TYPE_BUTTON = 2;
        private int inputType;
        private int input;
        private int inputExtra;

        /**
         * Default input constructor
         * @param inputType type of input
         * @param input main input value
         * @param inputExtra extra input value
         */
        Input(int inputType, int input, int inputExtra) {
            this.inputType = inputType;
            this.input = input;
            this.inputExtra = inputExtra;
        }

        /**
         * Parses an input string from toString(). Returns given defaults on error.
         * @param inputString string of input from toString()
         * @param inputType default type of input
         * @param input default input value
         * @param inputExtra default extra input value
         */
        Input(String inputString, int inputType, int input, int inputExtra) {
            this(inputType, input, inputExtra);
            String parts[] = inputString.split("\\|");
            if (parts.length == 3) {
                try {
                    int _inputType = Integer.parseInt(parts[0]);
                    int _input = Integer.parseInt(parts[1]);
                    int _inputExtra = Integer.parseInt(parts[2]);
                    this.inputType = _inputType;
                    this.input = _input;
                    this.inputExtra = _inputExtra;
                }
                catch (NumberFormatException ignore) {}
            }
        }

        /**
         * Serialize the Input class.
         * @return string of numbers delimited with a pipe `|`. (type|input|extra)
         */
        @Override
        public String toString() {
            return String.format(Locale.US,"%d|%d|%d", inputType, input, inputExtra);
        }

        public int getInput() {
            return input;
        }

        public int getType() {
            return inputType;
        }

        public int getExtra() {
            return inputExtra;
        }
    }
}
