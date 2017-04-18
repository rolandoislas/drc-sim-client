package com.rolandoislas.drcsimclient.audio;

import com.rolandoislas.drcsimclient.net.NetUtil;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by rolando on 2/12/17.
 */
public class AudioThread extends Thread {
    private final AudioUtil audioUtil;
    private final NetUtil netUtil;
    private boolean running = true;

    public AudioThread() {
        this.audioUtil = new AudioUtil();
        this.setName("Network Thread: Audio");
        netUtil = new NetUtil();
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] packet = netUtil.recv(sockets.socketAud);
                audioUtil.addData(packet);
            } catch (NetUtil.ReadTimeoutException ignore) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Logger.exception(e);
                }
            } catch (NetUtil.DisconnectedException e) {
                Logger.exception(e);
                running = false;
            }
        }
    }

    public void dispose() {
        running = false;
        audioUtil.dispose();
    }

    public void resetTimeout() {
        netUtil.resetTimeout();
    }
}
