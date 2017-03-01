package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.MainMenuScreen;
import io.github.teamfractal.util.SoundEffects;


public class HomeMainMenu extends Table {
	private RoboticonQuest game;
	private Stage stage;
	private TextButton btnNewGame;
	private TextButton btnExit;
	private Image background;
	private float backgroundX, backgroundY;

	private static Texture titleTexture = new Texture(Gdx.files.internal("roboticon_images/Roboticon_Quest_Title"));

	/**
	 * Initialise the Home Menu.
	 * @param game    The game object.
	 */
	public HomeMainMenu(RoboticonQuest game, MainMenuScreen screen) {
		this.game = game;
		this.stage = screen.getStage();

		// Create UI Components
		final Image imgTitle = new Image();
		imgTitle.setDrawable(new TextureRegionDrawable(new TextureRegion(titleTexture)));
		
		//Roboticon text to go next to + and - buttons
		final Label lblCredits = new Label("Song Credits: 'Floating Cities' \n Kevin MacLeod\n licensed: CC-BY",game.skin);


		
		
		btnNewGame = new TextButton("New game!", game.skin);
		btnExit = new TextButton("Exit", game.skin);
		background = new Image(new Texture(Gdx.files.internal("background/corridor.jpg")));
		background.setPosition(0,0);
		stage.addActor(background);


		// Adjust properties.
		btnNewGame.pad(10);
		btnExit.pad(10);

		// Bind events.
		bindEvents();

		// Add UI Components to table.

		add(imgTitle);
		row();
		add(btnNewGame).pad(5);
		row();
		add(btnExit).pad(5);
		row();
		row();
		add(lblCredits);

	}

	/**
	 * Bind button events.
	 */
	private void bindEvents() {
		btnNewGame.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				SoundEffects.click();
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame();
			}
		});

		btnExit.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				SoundEffects.click();
				Gdx.app.exit();
			}
		});
	}

	public void resizeScreen(float width, float height) {
		stage.getViewport().update((int)width, (int)height, true);
		backgroundX = width/background.getWidth();
		backgroundY = height/background.getHeight();
		background.setScale(backgroundX, backgroundY);
	}
}
