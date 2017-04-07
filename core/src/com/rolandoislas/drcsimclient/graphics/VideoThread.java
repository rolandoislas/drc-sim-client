package com.rolandoislas.drcsimclient.graphics;

import com.rolandoislas.drcsimclient.net.NetUtil;
import com.rolandoislas.drcsimclient.stage.StageConnect;
import com.rolandoislas.drcsimclient.util.logging.Logger;

import static com.rolandoislas.drcsimclient.Client.setStage;
import static com.rolandoislas.drcsimclient.Client.sockets;

/**
 * Created by rolando on 4/6/17.
 */
public class VideoThread extends Thread {
    private boolean running = true;
    private byte[] imageData = new byte[0];

    public VideoThread() {
        this.setName("Network Thread: Video");
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] data = NetUtil.recv(sockets.socketVid, "video");
                synchronized (this) {
                    imageData = data;
                }
            } catch (NetUtil.ReadTimeoutException ignore) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Logger.exception(e);
                }
            } catch (NetUtil.DisconnectedException e) {
                Logger.exception(e);
                Logger.info("Disconnected");
                dispose();
            }
        }
    }

    public void dispose() {
        running = false;
    }

    public byte[] getImageData() {
        byte[] data;
        synchronized (this) {
            data = imageData;
            imageData = new byte[0];
        }
        return data;
    }
}
