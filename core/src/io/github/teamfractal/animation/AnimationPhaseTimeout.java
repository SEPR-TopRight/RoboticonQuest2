package io.github.teamfractal.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.AbstractAnimationScreen;

public class AnimationPhaseTimeout implements IAnimation {
	private final Player player;
	private final RoboticonQuest game;
	private final int currentPhase;
	private final float timeout;
	private float time;
	private IAnimationFinish callback;
	boolean callbackDone;
	private static BitmapFont font = new BitmapFont();
	private static ShapeRenderer rect = new ShapeRenderer();
	private static GlyphLayout glyphLayout = new GlyphLayout();

	static {
		rect.setAutoShapeType(true);
	}

	public AnimationPhaseTimeout(Player player, RoboticonQuest game, int currentPhase, float timeout) {
		this.player = player;
		this.game = game;
		this.currentPhase = currentPhase;
		this.timeout = timeout;
	}

	private boolean continueAnimation() {
		return !callbackDone
				&& game.getPhase() == currentPhase
				&& game.getPlayer() == player;
	}



	@Override
	public boolean tick(float delta, AbstractAnimationScreen screen, Batch batch) {
		if (!continueAnimation()) return true;

		AbstractAnimationScreen.Size size = screen.getScreenSize();
		time += delta;

		if (time >= timeout) return true;

		int timeLeft = (int)(timeout - time) + 1;
		String countdown = String.valueOf(timeLeft);

		synchronized (rect) {
			rect.setProjectionMatrix(batch.getProjectionMatrix());
			rect.begin(ShapeRenderer.ShapeType.Filled);
			rect.setColor(0, 1, 0, 0.1f);
			rect.rect(0, 0, (1 - time / timeout) * size.Width, 3);
			rect.end();
		}

		synchronized (font) {
			batch.begin();
			glyphLayout.setText(font, countdown);
			font.setColor(1,1,1, 1);
			font.draw(batch, glyphLayout, 10, 20);
			batch.end();
		}

		return false;
	}

	@Override
	public void setAnimationFinish(IAnimationFinish callback) {
		this.callback = callback;
	}

	@Override
	public void callAnimationFinish() {
		if (continueAnimation()) {
			callbackDone = true;
			game.nextPhase();

			if (callback != null)
				callback.OnAnimationFinish();
		}
	}

	@Override
	public void cancelAnimation() {
		callbackDone = true;
	}
}
