package com.rolandoislas.drcsimclient.control;

import com.rolandoislas.drcsimclient.stage.StageControl;

/**
 * Created by Rolando on 2/6/2017.
 */
public interface Control {
	void init(StageControl stage);

	void update();

    void vibrate(int milliseconds);
}
