package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.teamfractal.RoboticonQuest;


public class HomeMainMenu extends Table {
	private RoboticonQuest game;

	public HomeMainMenu(final RoboticonQuest game) {
		this.game = game;



		final TextButton btnNewGame = new TextButton("New game!", game.skin);
		btnNewGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.mapScreen);
			}
		});
		
		final TextButton btnExit = new TextButton("Exit", game.skin);
		btnExit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});


		btnNewGame.pad(10);
		btnExit.pad(10);

		row();
		add(btnNewGame).pad(5);
		row();
		add(btnExit).pad(5);
	}
}
