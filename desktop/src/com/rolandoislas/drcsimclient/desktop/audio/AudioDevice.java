package com.rolandoislas.drcsimclient.desktop.audio;

import javax.sound.sampled.*;

/**
 * Created by rolando on 2/11/17.
 */
public class AudioDevice implements com.rolandoislas.drcsimclient.audio.AudioDevice {
    private SourceDataLine line;
    private final byte[][] audioBuffer = new byte[15][832*2];
    private int audioPosRead = 0;
    private int getAudioPosWrite = 0;
    private long playTime = 0;
    private boolean running = true;

    public AudioDevice() {
        AudioFormat af = new AudioFormat(48000, 16, 2, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(af, 4096);
            line.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running)
                    writeLoop();
            }
        }).start();
    }

    private void writeLoop() {
        if (System.currentTimeMillis() - playTime <= 15)
            return;
        playTime = System.currentTimeMillis();
        for (int samples = 0; samples < 2; samples++) {
            byte[] sample = audioBuffer[getAudioPosWrite++];
            write(sample, 0, sample.length);
            getAudioPosWrite %= audioBuffer.length;
        }
    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public void dispose() {
        running = false;
        if (line != null)
            line.close();
    }

    @Override
    public void write(byte[] data) {
        audioBuffer[audioPosRead++] = data;
        audioPosRead %= audioBuffer.length;
    }

    public void write(byte[] data, int start, int stop) {
        if (line != null)
            try {
                line.write(data, start, stop);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
    }
}
