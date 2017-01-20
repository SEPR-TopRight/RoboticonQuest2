package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.AbstractAnimationScreen;

public class AnimationAddResources implements IAnimation {
	private final Player player;
	private final int energy;
	private final int food;
	private final int ore;
	private float time;
	private static BitmapFont font = new BitmapFont();

	public AnimationAddResources(Player player, int energy, int food, int ore) {
		time = 0;
		this.player = player;
		this.energy = energy;
		this.food = food;
		this.ore = ore;

		// System.out.println("Phase 5 Res Gen: " + generateResourceString());
	}

	private String resStr(int resCount, String type) {
		if (resCount == 0) {
			return "-- " + type + "   ";
		}
		return (resCount > 0 ? "+" : "-") + resCount + " " + type + "   ";
	}

	private float fn_quad(float t) {
		if (t > 1f) return 1;
		return t * t;
	}

	private float fn_opacity () {
		if (time < 1f) {
			return fn_quad(time);
		} else if (time >= 1f && time < 2f) {
			return 1;
		} else {
			// 2 ~ 3:
			return 1f - fn_quad(time - 2f);
		}
	}

	private static final float animationLength = 3;

	@Override
	public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
		time += delta;
		if (time > animationLength) {
			return true;
		}

		batch.begin();
		font.setColor(1,1,1, fn_opacity());
		font.draw(batch, generateResourceString(), 20, fn_quad(time) * 30);
		batch.end();
		return false;
	}

	private IAnimationFinish callback;
	@Override
	public void setAnimationFinish(IAnimationFinish callback) {
		this.callback = callback;
	}

	public void callAnimationFinish() {
		if (callback != null)
			callback.OnAnimationFinish();
	}

	@Override
	public void cancelAnimation() {
		callback = null;
		time += animationLength;
	}

	private String generateResourceString() {
		return (resStr(energy, "Energy")
				+ resStr(food, "Food")
				+ resStr(ore, "Ore")).trim();
	}
}
