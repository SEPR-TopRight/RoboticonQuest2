package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.animation.IAnimation;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractAnimationScreen {
	protected abstract RoboticonQuest getGame();

	private final ArrayList<IAnimation> animations = new ArrayList<IAnimation>();
	private final ArrayList<IAnimation> queueAnimations = new ArrayList<IAnimation>();

	/**
	 * Add a new animation to current Screen.
	 * @param animation    The animation to be added.
	 */
	public void addAnimation(IAnimation animation) {
		if (!animations.contains(animation) && !queueAnimations.contains(animation)) {
			synchronized (queueAnimations) {
				queueAnimations.add(animation);
			}
		}
	}

	/**
	 * Request to render animation.
	 * @param delta   Time delta from last render call.
	 */
	void renderAnimation(float delta) {
		Batch batch = getGame().getBatch();

		synchronized (animations) {
			synchronized (queueAnimations) {
				animations.addAll(queueAnimations);
				queueAnimations.clear();
			}

			Iterator<IAnimation> iterator = animations.iterator();

			while (iterator.hasNext()) {
				IAnimation animation = iterator.next();
				if (animation.tick(delta, this, batch)) {
					iterator.remove();
					animation.callAnimationFinish();
				}
			}
		}
	}

	/**
	 * The screen size.
	 * @return  Size of the screen.
	 */
	abstract public Size getScreenSize();

	/**
	 * A size structure with Width and Height property.
	 */
	public class Size {
		public float Width;
		public float Height;
	}
}
