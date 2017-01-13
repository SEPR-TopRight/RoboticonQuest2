package io.github.teamfractal.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.RoboticonMarketActors;

public class RoboticonMarketScreen implements Screen {

	final RoboticonQuest game;
	final Stage stage;
	final Table table;
	private final RoboticonMarketActors actors;
	
	
	public RoboticonMarketScreen(final RoboticonQuest game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());
		this.table = new Table();
		table.setFillParent(true);
		
		
	}
}
