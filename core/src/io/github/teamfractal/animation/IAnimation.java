package io.github.teamfractal.animation;

public interface IAnimation {
	/**
	 * Draw animation on screen.
	 *
	 * @param delta     Time change since last call.
	 * @param screen    The screen to draw on.
	 * @return          return <code>true</code> for animation complete.
	 */
	boolean tick(float delta, IAnimationScreen screen);
}
