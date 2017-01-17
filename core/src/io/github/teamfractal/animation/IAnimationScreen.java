package io.github.teamfractal.animation;

import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;

import java.util.ArrayList;

public abstract class IAnimationScreen {
	protected abstract RoboticonQuest getGame();

	protected ArrayList<IAnimation> animations = new ArrayList<IAnimation>();

	protected abstract Stage getStage();
	public void addAnimation(IAnimation animation) {
		if (!animations.contains(animation)) {
			animations.add(animation);
		}
	}

	public void renderAnimation(float delta) {
		ArrayList<IAnimation> toRemove = new ArrayList<IAnimation>();

		for (IAnimation animation : animations) {
			if (animation.tick(delta, this)) {
				toRemove.add(animation);
			}
		}

		for (IAnimation animation : toRemove) {
			animations.remove(animation);
		}
	}
}
