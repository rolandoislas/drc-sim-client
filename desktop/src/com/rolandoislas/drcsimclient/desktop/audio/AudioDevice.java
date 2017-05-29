package com.rolandoislas.drcsimclient.desktop.audio;

import com.rolandoislas.drcsimclient.util.logging.Logger;

import javax.sound.sampled.*;

/**
 * Created by rolando on 2/11/17.
 */
public class AudioDevice implements com.rolandoislas.drcsimclient.audio.AudioDevice {
    private SourceDataLine line;

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
        }
        catch (LineUnavailableException e) {
            Logger.exception(e);
            Logger.warn("Audio format not supported by system.");
        }
    }

    @Override
    public void setVolume(float volume) {
        // TODO volume control
    }

    @Override
    public void dispose() {
        Logger.debug("Closing audio line");
        // FIXME closing the audio line occasionally stalls the thread
        //if (line != null && line.isOpen())
        //    line.close();
    }

    @Override
    public void write(byte[] data) {
        write(data, 0, data.length);
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
