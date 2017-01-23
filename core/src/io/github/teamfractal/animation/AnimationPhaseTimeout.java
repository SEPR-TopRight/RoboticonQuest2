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
	private boolean callbackDone;
	private static BitmapFont font = new BitmapFont();
	private static ShapeRenderer rect = new ShapeRenderer();
	private static GlyphLayout glyphLayout = new GlyphLayout();

	static {
		rect.setAutoShapeType(true);
	}

	/**
	 * Initialise the animation.
	 * @param player         Current player.
	 * @param game           The game object.
	 * @param currentPhase   Current phase number.
	 * @param timeout        Timeout length, in seconds.
	 */
	public AnimationPhaseTimeout(Player player, RoboticonQuest game, int currentPhase, float timeout) {
		this.player = player;
		this.game = game;
		this.currentPhase = currentPhase;
		this.timeout = timeout;
	}

	/**
	 * Check if the animation should continue or not.
	 * @return  <code>true</code> if the animation should continue.
	 */
	private boolean continueAnimation() {
		return !callbackDone
				&& game.getPhase() == currentPhase
				&& game.getPlayer() == player;
	}

	/**
	 * Count down bar colour changes from green to red overtime.
	 */
	private void barColour() {
		float r = time / timeout;
		rect.setColor(r, 1 - r, 0, 0.7f);
	}


	/**
	 * Draw animation on screen.
	 *
	 * @param delta     Time change since last call.
	 * @param screen    The screen to draw on.
	 * @param batch     The Batch for drawing stuff.
	 * @return          return <code>true</code> if the animation has completed.
	 */
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
			barColour();
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
