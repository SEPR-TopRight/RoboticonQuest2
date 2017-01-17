package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.GameScreen;

public class AddResourceAnimation implements IAnimation {
	private final Player player;
	private final int energy;
	private final int food;
	private final int ore;
	private float time;
	BitmapFont font = new BitmapFont();


	public AddResourceAnimation(Player player, int energy, int food, int ore) {
		time = 0;
		this.player = player;
		this.energy = energy;
		this.food = food;
		this.ore = ore;
	}

	@Override
	public boolean tick(float delta, IAnimationScreen screen) {
		time += delta;
		if (delta > 500) {
			return true;
		}

		Stage stage = screen.getStage();

		return false;
	}
}
