package com.rolandoislas.drcsimclient.desktop.audio;

import com.rolandoislas.drcsimclient.util.logging.Logger;

import javax.sound.sampled.*;

/**
 * Created by rolando on 2/11/17.
 */
public class AudioDevice implements com.rolandoislas.drcsimclient.audio.AudioDevice {
    private SourceDataLine line;
    private byte buffer[][] = new byte[50][1664]; // Buffer causes some delay
    private int readIndex = 0;
    private int writeIndex = 0;
    private boolean running = true;

    public AudioDevice() {
        AudioFormat af = new AudioFormat(48000, 16, 2, true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(af, 4096);
            line.start();
        }
        catch (IllegalArgumentException e) {
            Logger.exception(e);
            Logger.warn("Audio format not supported by system.");
            running = false;
        }
        catch (LineUnavailableException e) {
            Logger.exception(e);
            Logger.warn("Audio format not supported by system.");
            running = false;
        }
        Thread playThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    write(buffer[readIndex], 0, buffer[readIndex].length);
                    readIndex = (readIndex + 1) % (buffer.length - 1);
                }
            }
        });
        playThread.setName("Audio Play Thread");
        playThread.start();
    }

    @Override
    public void setVolume(float volume) {
        // TODO volume control
    }

    @Override
    public void dispose() {
        running = false;
        Logger.debug("Closing audio line");
        // FIXME closing the audio line occasionally stalls the thread
        //if (line != null && line.isOpen())
        //    line.close();
    }

    @Override
    public void write(byte[] data) {
        buffer[writeIndex] = data;
        writeIndex = (writeIndex + 1) % (buffer.length - 1);
    }

    public void write(byte[] data, int start, int stop) {
        if (line != null && line.isOpen())
            try {
                line.write(data, start, stop);
            }
            catch (IllegalArgumentException e) {
                Logger.exception(e);
            }
    }
}
