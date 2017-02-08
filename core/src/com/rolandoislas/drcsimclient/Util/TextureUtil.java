package com.rolandoislas.drcsimclient.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Rolando on 2/6/2017.
 */
public class TextureUtil {
	public static Texture resizeTexture(String internalPath, float width, float height) {
		return resizeTexture(internalPath, (int)width, (int)height);
	}

	public static Texture resizeTexture(String internalPath, int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		Pixmap origPixmap = new Pixmap(Gdx.files.internal(internalPath));
		pixmap.drawPixmap(origPixmap, 0, 0, origPixmap.getWidth(), origPixmap.getHeight(),
				0, 0, width, height);
		return new Texture(pixmap);
	}
}
