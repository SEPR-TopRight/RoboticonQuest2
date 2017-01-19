package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.animation.IAnimation;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractAnimationScreen {
	protected abstract RoboticonQuest getGame();

	protected ArrayList<IAnimation> animations = new ArrayList<IAnimation>();
	protected ArrayList<IAnimation> queueAnimations = new ArrayList<IAnimation>();

	protected abstract Stage getStage();
	public void addAnimation(IAnimation animation) {
		if (!animations.contains(animation) && !queueAnimations.contains(animation)) {
			synchronized (queueAnimations) {
				queueAnimations.add(animation);
			}
		}
	}

	public void renderAnimation(float delta) {
		Batch batch = getGame().getBatch();

		synchronized (animations) {
			synchronized (queueAnimations) {
				animations.addAll(queueAnimations);
				queueAnimations.clear();
			}

			Iterator<IAnimation> iter = animations.iterator();

			while (iter.hasNext()) {
				IAnimation animation = iter.next();
				if (animation.tick(delta, this, batch)) {
					iter.remove();
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
