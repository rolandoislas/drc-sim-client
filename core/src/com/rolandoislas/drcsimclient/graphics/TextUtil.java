package com.rolandoislas.drcsimclient.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;

/**
 * Created by Rolando on 2/13/2017.
 */
public class TextUtil {
	private static HashMap<Integer, BitmapFont> fonts = new HashMap<Integer, BitmapFont>();

	public static BitmapFont generateScaledFont(float scale) {
		int size = (int) (50f * Gdx.graphics.getHeight() * scale / 720f);
		if (fonts.containsKey(size))
			return fonts.get(size);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/coolvetica.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		FreeTypeFontGenerator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		fonts.put(size, font);
		return font;
	}

	public static void dispose() {
		fonts.clear();
	}
}
