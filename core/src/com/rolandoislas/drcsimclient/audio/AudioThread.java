package com.rolandoislas.drcsimclient.audio;

import com.rolandoislas.drcsimclient.net.NetUtil;

import java.io.IOException;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by rolando on 2/12/17.
 */
public class AudioThread extends Thread {
    private final AudioUtil audioUtil;
    private boolean running = true;

    public AudioThread(AudioUtil audioUtil) {
        this.audioUtil = audioUtil;
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] packet = NetUtil.recv(sockets.socketAud, "audio");
                audioUtil.addData(packet);
            } catch (IOException e) {
                if (e.getMessage().contains("Disconnected"))
                    dispose();
            }
        }
    }

    public void dispose() {
        running = false;
    }
}
