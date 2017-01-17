package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.RoboticonMarketScreen;
import io.github.teamfractal.screens.RoboticonPurchaseScreen;

public class RoboticonPurchaseActors extends Table {
	private RoboticonQuest game;
	private RoboticonPurchaseScreen screen;

	public RoboticonPurchaseActors(final RoboticonQuest game, RoboticonPurchaseScreen screen) {
		this.game = game;
		this.screen = screen;

		build();
	}

	private void build() {

	}
}
