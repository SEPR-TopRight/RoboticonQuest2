package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import io.github.teamfractal.screens.AbstructAnimationScreen;

public interface IAnimation {
	/**
	 * Draw animation on screen.
	 *
	 * @param delta     Time change since last call.
	 * @param screen    The screen to draw on.
	 * @return          return <code>true</code> for animation complete.
	 */
	boolean tick(float delta, AbstructAnimationScreen screen, Batch batch);

	/**
	 *
	 * @param callback
	 */
	void setAnimationFinish(IAnimationFinish callback);
	void callAnimationFinish();
	void cancelAnimation();
}
