package com.rolandoislas.drcsimclient.audio;

/**
 * Created by rolando on 2/11/17.
 */
public interface AudioDevice {
    void setVolume(float volume);

    void dispose();

    void write(byte[] data);

    void write(byte[] data, int i, int length);
}
