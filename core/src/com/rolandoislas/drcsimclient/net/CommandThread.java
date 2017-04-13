package com.rolandoislas.drcsimclient.net;

import com.rolandoislas.drcsimclient.util.logging.Logger;

import java.io.IOException;
import java.net.DatagramPacket;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by rolando on 4/6/17.
 */
public class CommandThread extends Thread {
    private boolean running = true;
    private Command command = new Command();

    public CommandThread() {
        this.setName("Network Thread: Command");
    }

    @Override
    public void run() {
        while (running) {
            byte[] buf = new byte[1024];
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
            String[] command = Codec.decodeCommand(packet);
            if (!command[0].isEmpty()) {
                Logger.debug("Received command %1$s", command[0]);
                Logger.extra("Received command %1$s with data %2$s", command[0], command[1]);
                synchronized (this) {
                    this.command = new Command(command[0], command[1]);
                }
            }
        }
    }

    public void dispose() {
        running = false;
    }

    public Command getCommand() {
        Command commandLocal;
        synchronized (this) {
            commandLocal = this.command;
            this.command = new Command();
        }
        return commandLocal;
    }

    public class Command {
        private final String data;
        private String command;

        Command(String command, String data) {
            this.command = command;
            this.data = data;
        }

        Command() {
            this.command = "";
            this.data = "";
        }

        public boolean isCommand(String commandString) {
            return this.command.equals(commandString);
        }
    }
}
