package com.rolandoislas.drcsimclient.config;

import com.badlogic.gdx.Preferences;
import com.rolandoislas.drcsimclient.util.PreferencesUtil;

import java.util.Map;

/**
 * Created by Rolando on 2/7/2017.
 */
public class Config implements Preferences {
	private final Preferences config;

	public Config(String configName) {
		config = PreferencesUtil.get(configName);
	}

	public void load() {
		throw new RuntimeException("Not implemented");
	}

	// Config wrapper
	@Override
	public Preferences putBoolean(String key, boolean val) {
		return config.putBoolean(key, val);
	}

	@Override
	public Preferences putInteger(String key, int val) {
		return config.putInteger(key, val);
	}

	@Override
	public Preferences putLong(String key, long val) {
		return config.putLong(key, val);
	}

	@Override
	public Preferences putFloat(String key, float val) {
		return config.putFloat(key, val);
	}

	@Override
	public Preferences putString(String key, String val) {
		return config.putString(key, val);
	}

	@Override
	public Preferences put(Map<String, ?> vals) {
		return config.put(vals);
	}

	@Override
	public boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	@Override
	public int getInteger(String key) {
		return config.getInteger(key);
	}

	@Override
	public long getLong(String key) {
		return config.getLong(key);
	}

	@Override
	public float getFloat(String key) {
		return config.getFloat(key);
	}

	@Override
	public String getString(String key) {
		return config.getString(key);
	}

	@Override
	public boolean getBoolean(String key, boolean defValue) {
		return config.getBoolean(key, defValue);
	}

	@Override
	public int getInteger(String key, int defValue) {
		return config.getInteger(key, defValue);
	}

	@Override
	public long getLong(String key, long defValue) {
		return config.getLong(key, defValue);
	}

	@Override
	public float getFloat(String key, float defValue) {
		return config.getFloat(key, defValue);
	}

	@Override
	public String getString(String key, String defValue) {
		return config.getString(key, defValue);
	}

	@Override
	public Map<String, ?> get() {
		return config.get();
	}

	@Override
	public boolean contains(String key) {
		return config.contains(key);
	}

	@Override
	public void clear() {
		config.clear();
	}

	@Override
	public void remove(String key) {
		config.remove(key);
	}

	@Override
	public void flush() {
		config.flush();
	}
}
