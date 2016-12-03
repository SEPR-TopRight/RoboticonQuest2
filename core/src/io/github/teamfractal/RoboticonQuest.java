package io.github.teamfractal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.teamfractal.screens.MainMenuScreen;

public class RoboticonQuest extends Game {
	SpriteBatch batch;
	public Skin skin;
	public MainMenuScreen mainMenuScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setupSkin();


		// Setup other screens.
		mainMenuScreen = new MainMenuScreen(this);

		setScreen(mainMenuScreen);
	}

	private void setupSkin() {
		skin = new Skin(
				Gdx.files.internal("skin/skin.json"),
				new TextureAtlas(Gdx.files.internal("skin/skin.atlas"))
		);
	}

	@Override
	public void dispose () {
		batch.dispose();
		mainMenuScreen.dispose();
		skin.dispose();
	}
}
