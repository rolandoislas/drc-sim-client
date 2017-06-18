package com.rolandoislas.drcsimclient.data;

import java.io.File;

/**
 * Created by Rolando on 12/21/2016.
 */
public class Constants {
	// Info
	public static final String NAME = "DRC Sim Client";
	public static final String VERSION = "2.0";
	// Ports
	public static final int PORT_SERVER_VID = 50000;
	public static final int PORT_SERVER_AUD = 50001;
	public static final int PORT_SERVER_CMD = 50002;
	// Commands
	public static final short COMMAND_REGISTER = 0;
    public static final short COMMAND_PING = 1;
    public static final short COMMAND_PONG = 2;
    public static final short COMMAND_INPUT_VIBRATE = 3;
    public static final short COMMAND_INPUT_MIC_BLOW = 4;
    public static final short COMMAND_INPUT_BUTTON = 5;
    public static final short COMMAND_INPUT_JOYSTICK = 6;
    public static final short COMMAND_INPUT_TOUCH = 7;
    public static final short COMMAND_INPUT_BUTTON_EXTRA = 8;
	// Buttons
	public static final int BUTTON_A = 0x8000;
	public static final int BUTTON_B = 0x4000;
	public static final int BUTTON_X = 0x2000;
	public static final int BUTTON_Y = 0x1000;
	public static final int BUTTON_L = 0x0020;
	public static final int BUTTON_R = 0x0010;
	public static final int BUTTON_ZL = 0x0080;
	public static final int BUTTON_ZR = 0x0040;
	public static final int BUTTON_MINUS = 0x0004;
	public static final int BUTTON_PLUS = 0x0008;
	public static final int BUTTON_HOME = 0x0002;
	public static final int BUTTON_L3 = 0x08;
	public static final int BUTTON_R3 = 0x04;
	public static final int BUTTON_LEFT = 0x800;
	public static final int BUTTON_RIGHT = 0x400;
	public static final int BUTTON_DOWN = 0x100;
	public static final int BUTTON_UP = 0x200;
	// Path
	private static final File PATH_ROOT = new File(System.getProperty("user.home"), ".drc-sim/");
    public static final File PATH_LOG = new File(PATH_ROOT, "log");
}
