package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.teamfractal.RoboticonQuest;


public class HomeMainMenu extends Table {
	private RoboticonQuest game;

	public HomeMainMenu(final RoboticonQuest game) {
		this.game = game;

		
		Texture titleTexture = new Texture(Gdx.files.internal("roboticon_images/Roboticon_Quest_Title"));
		final Image imgTitle = new Image();
		imgTitle.setDrawable(new TextureRegionDrawable(new TextureRegion(titleTexture)));
		
		final TextButton btnNewGame = new TextButton("New game!", game.skin);
		btnNewGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame();
			}
		});
		
		final TextButton btnExit = new TextButton("Exit", game.skin);
		btnExit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});

		
		add(imgTitle);
		btnNewGame.pad(10);
		btnExit.pad(10);
		row();
		add(btnNewGame).pad(5);
		row();
		add(btnExit).pad(5);
	}
}
