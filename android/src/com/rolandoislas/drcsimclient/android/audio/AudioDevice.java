package com.rolandoislas.drcsimclient.android.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;

/**
 * Created by rolando on 2/12/17.
 */
public class AudioDevice implements com.rolandoislas.drcsimclient.audio.AudioDevice {
    private final AudioTrack audioTrack;

    public AudioDevice() {
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT, 832 * 2*15, AudioTrack.MODE_STREAM);
        audioTrack.play();
    }

    @Override
    public void setVolume(float volume) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioTrack.setVolume(volume);
        }
    }

    @Override
    public void dispose() {
        audioTrack.pause();
        audioTrack.flush();
        audioTrack.release();
    }

    @Override
    public void write(byte[] data) {
        write(data, 0, data.length);
    }

    @Override
    public void write(byte[] data, int offset, int length) {
        try {
            audioTrack.write(data, offset, length);
        }
        catch (IllegalStateException ignore) {}
    }
}
