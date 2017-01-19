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

	public void addAnimation(IAnimation animation) {
		if (!animations.contains(animation) && !queueAnimations.contains(animation)) {
			synchronized (queueAnimations) {
				queueAnimations.add(animation);
			}
		}
	}

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

	abstract public Size getScreenSize();

	public class Size {
		public float Width;
		public float Height;
	}
}
