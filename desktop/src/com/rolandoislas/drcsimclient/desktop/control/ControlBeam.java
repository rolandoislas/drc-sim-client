package com.rolandoislas.drcsimclient.desktop.control;

import com.rolandoislas.drcsimclient.Client;
import com.rolandoislas.drcsimclient.control.Control;
import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.stage.StageControl;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.services.impl.UsersService;
import pro.beam.interactive.event.EventListener;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.robot.Robot;
import pro.beam.interactive.robot.RobotBuilder;

import java.util.concurrent.ExecutionException;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by Rolando on 2/18/2017.
 */
public class ControlBeam implements Control {
	private boolean run = true;

	@Override
	public void init(StageControl stage) {
		if (Client.args.apiBeam.isEmpty()) {
			run = false;
			return;
		}
		BeamAPI beam = new BeamAPI(Client.args.apiBeam);
		BeamUser user;
		try {
			user = beam.use(UsersService.class).getCurrent().get();
		} catch (InterruptedException e) {
			die(e);
			return;
		} catch (ExecutionException e) {
			die(e);
			return;
		}
		Robot robot;
		try {
			robot = new RobotBuilder()
					.channel(user.channel)
					.build(beam, false)
					.get();
		} catch (InterruptedException e) {
			die(e);
			return;
		} catch (ExecutionException e) {
			die(e);
			return;
		}
		robot.on(Protocol.Report.class, new EventListener<Protocol.Report>() {
			@Override
			public void handle(Protocol.Report message) {
				if (!message.hasUsers())
					return;
				handleJoystick(message);
				handleButton(message);
				//handleScreen(message); // TODO handle screen clicks
			}
		});
	}

	private void handleScreen(Protocol.Report message) {
		int x = 0;
		int y = 0;
		for (Protocol.Report.ScreenInfo screen : message.getScreenList()) {
			if (screen.hasId() && screen.getId() == 5 && screen.hasClicks() &&
					screen.hasCoordMean() && screen.hasCoordStddev()) {
				x = (int) (screen.getCoordMean().getX() + screen.getCoordStddev().getX());
				y = (int) (screen.getCoordMean().getY() + screen.getCoordStddev().getY());
			}
		}
		if (x > 0 && y > 0)
			sockets.sendTouchScreenInput(x, y);
	}

	private void handleButton(Protocol.Report message) {
		int buttonBits = 0;
		for (Protocol.Report.TactileInfo tactile : message.getTactileList()) {
			if (tactile.hasHolding() && tactile.hasReleaseFrequency() && tactile.hasId() &&
					tactile.getHolding() >= 1) {
				if (tactile.getId() == 1)
					buttonBits |= Constants.BUTTON_A;
				if (tactile.getId() == 2)
					buttonBits |= Constants.BUTTON_B;
				if (tactile.getId() == 3)
					buttonBits |= Constants.BUTTON_L;
				if (tactile.getId() == 4)
					buttonBits |= Constants.BUTTON_R;
				if (tactile.getId() == 6)
					buttonBits |= Constants.BUTTON_MINUS;
				if (tactile.getId() == 7)
					buttonBits |= Constants.BUTTON_PLUS;
				if (tactile.getId() == 8)
					buttonBits |= Constants.BUTTON_X;
				if (tactile.getId() == 9)
					buttonBits |= Constants.BUTTON_Y;
				if (tactile.getId() == 10)
					buttonBits |= Constants.BUTTON_UP;
				if (tactile.getId() == 11)
					buttonBits |= Constants.BUTTON_DOWN;
				if (tactile.getId() == 12)
					buttonBits |= Constants.BUTTON_LEFT;
				if (tactile.getId() == 13)
					buttonBits |= Constants.BUTTON_RIGHT;
			}
		}
		sockets.sendButtonInput(buttonBits);
	}

	private void handleJoystick(Protocol.Report message) {
		float[] axes = {0, 0, 0, 0};
		for (Protocol.Report.JoystickInfo joystick : message.getJoystickList()) {
			if (joystick.hasCoordMean() && joystick.hasCoordStddev() && joystick.hasId()) {
				if (joystick.getId() == 0) {
					axes[0] = (float) (joystick.getCoordMean().getX() + joystick.getCoordStddev().getX());
					axes[1] = (float) (joystick.getCoordMean().getY() + joystick.getCoordStddev().getY());
				} else if (joystick.getId() == 1) {
					axes[2] = (float) (joystick.getCoordMean().getX() + joystick.getCoordStddev().getX());
					axes[3] = (float) (joystick.getCoordMean().getY() + joystick.getCoordStddev().getY());
				}
			}
		}
		sockets.sendJoystickInput(axes);
	}

	private void die(Exception e) {
		e.printStackTrace();
		run = false;
	}

	@Override
	public void update() {
		if (!run && !Client.args.apiBeam.isEmpty())
			Client.setStage(new StageControl());
	}

	@Override
	public void vibrate(int milliseconds) {

	}
}
