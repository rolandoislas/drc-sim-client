package com.rolandoislas.drcsimclient.net;

import com.rolandoislas.drcsimclient.data.Constants;
import com.rolandoislas.drcsimclient.net.packet.CommandPacket;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.IOException;
import java.net.DatagramPacket;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by rolando on 4/6/17.
 */
public class CommandThread extends Thread {
    private static final long PING_INTERVAL = 1000;
    private static final long TIMEOUT = 5000;
    private boolean running = true;
    private CommandPacket command = null;
    private long lastPingTime;
    private long lastPongTime;

    public CommandThread() {
        this.setName("Network Thread: Command");
    }

    @Override
    public void run() {
        lastPongTime = System.currentTimeMillis();
        while (running) {
            long time = System.currentTimeMillis();
            if (time - lastPingTime >= PING_INTERVAL) {
                sockets.sendCommand(Constants.COMMAND_PING); // Keep-alive
                lastPingTime = System.currentTimeMillis();
            }
            if (time - lastPongTime >= TIMEOUT / 2)
                sockets.sendCommand(Constants.COMMAND_REGISTER);
            if (time - lastPongTime >= TIMEOUT)
                dispose();
            byte[] buf = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                sockets.socketCmd.receive(packet);
            } catch (IOException ignore) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Logger.exception(e);
                }
                continue;
            }
            if (packet.getLength() <= 0)
                continue;
            CommandPacket commandPacket = new CommandPacket(packet);
            synchronized (this) {
                this.command = commandPacket;
            }
        }
    }

    public void dispose() {
        running = false;
    }

    public CommandPacket getCommand() {
        CommandPacket commandLocal;
        synchronized (this) {
            commandLocal = this.command;
            this.command = null;
        }
        return commandLocal;
    }

    public void resetTimeout() {
        lastPongTime = System.currentTimeMillis();
    }
}
