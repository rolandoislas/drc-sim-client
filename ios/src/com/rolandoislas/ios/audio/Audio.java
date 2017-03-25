package com.rolandoislas.ios.audio;

/**
 * Created by rolando on 3/24/17.
 */
public class Audio implements com.rolandoislas.drcsimclient.audio.Audio {
    @Override
    public AudioDevice newAudioDevice() {
        return new AudioDevice();
    }
}
