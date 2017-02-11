package com.rolandoislas.drcsimclient.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;

import java.util.ArrayList;

/**
 * Created by Rolando on 2/10/2017.
 */
public class AudioUtil {
	private final AudioDevice audioDevice;
	private int audioPosR = 0;
	private int audioPosW = 0;
	private int audioNumBufs = 15;
	private ArrayList<ArrayList<Short>> audioRing = new ArrayList<ArrayList<Short>>();
	//[array.array('H', '\0' * 416 * 2)] * self.pa_num_bufs

	public AudioUtil() {
		audioDevice = Gdx.audio.newAudioDevice(48000, false);
		audioDevice.setVolume(0.03f);
		for (int buf = 0; buf < audioNumBufs; buf++)
			audioRing.add(new ArrayList<Short>());
	}

	public void addData(byte[] data) {
		ArrayList<Short> shortData = new ArrayList<Short>();
		for (short number : data)
			shortData.add(number);
		/*
		self.pa_ring[self.pa_rpos] = array.array('H', data)
        self.pa_rpos += 1
        self.pa_rpos %= self.pa_num_bufs
		*/
		this.audioRing.set(this.audioPosR, shortData);
		this.audioPosR += 1;
		this.audioPosR %= this.audioNumBufs;
	}

	public void update() {
		/*samples = self.pa_ring[self.pa_wpos]
		self.pa_wpos += 1
		self.pa_wpos %= self.pa_num_bufs
		samples.extend(self.pa_ring[self.pa_wpos])
		self.pa_wpos += 1
		self.pa_wpos %= self.pa_num_bufs
		return samples, pyaudio.paContinue*/
		ArrayList<Short> samples = this.audioRing.get(this.audioPosW);
		this.audioPosW += 1;
		this.audioPosW %= this.audioNumBufs;
		samples.addAll(this.audioRing.get(this.audioPosW));
		this.audioPosW += 1;
		this.audioPosW %= this.audioNumBufs;
		short[] samplesPrimitive = new short[samples.size()];
		for (int number = 0; number < samples.size(); number++)
			samplesPrimitive[number] = samples.get(number);
		this.audioDevice.writeSamples(samplesPrimitive, 0, samplesPrimitive.length);
	}

	public void dispose() {
		audioDevice.dispose();
	}
}
