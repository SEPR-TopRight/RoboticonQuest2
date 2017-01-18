package io.github.teamfractal.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.animation.IAnimation;

import java.util.ArrayList;

public abstract class AbstructAnimationScreen {
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

		Batch batch = getGame().getBatch();
		batch.begin();
		for (IAnimation animation : animations) {
			if (animation.tick(delta, this, batch)) {
				toRemove.add(animation);
				animation.callAnimationFinish();
			}
		}
		batch.end();

		for (IAnimation animation : toRemove) {
			animations.remove(animation);
		}
	}
}
