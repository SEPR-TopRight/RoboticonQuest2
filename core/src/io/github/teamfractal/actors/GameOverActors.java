package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.GameOverScreen;

public class GameOverActors extends Table {
	private RoboticonQuest game;
	private Label phaseInfo;
	private Label playerStats;
	private GameOverScreen screen;
	private TextButton nextButton;
	private TextButton btnNewGame;
	private static final Texture END_TEXTURES[] = new Texture[4];
	private Image bg = new Image();
	static {
		END_TEXTURES[0] = new Texture(Gdx.files.internal("events/landscape.png"));
		END_TEXTURES[1] = new Texture(Gdx.files.internal("events/win1.png"));
		END_TEXTURES[2] = new Texture(Gdx.files.internal("events/win2.png"));
		END_TEXTURES[3] = new Texture(Gdx.files.internal("events/wint.png"));
		
	}

	/**
	 * Sync. information with the adjustable.
	 * @param adjustableActor     The adjustable to manipulate with.
	 * @param resource            The resource type.
	 * @param bIsSell             <code>true</code> if the adjustable is for sell,
	 *                            <code>false</code> if is for buy.
	 */
		/**
	 * Initialise market actors.
	 * @param game       The game object.
	 * @param gameOverScreen     The screen object.
	 */
	public GameOverActors(final RoboticonQuest game, GameOverScreen gameOverScreen) {
		center();

		Skin skin = game.skin;
		this.game = game;
		this.screen = gameOverScreen;
		Stage stage = gameOverScreen.getStage();
		// Create UI Components
		phaseInfo = new Label("", game.skin);
		nextButton = new TextButton("EXIT", game.skin);
		btnNewGame= new TextButton("NEW GAME", game.skin);
		playerStats = new Label("", game.skin);
		new Label("", game.skin);
		new Label("TRIAL",  skin);
		
		// Adjust properties.
		phaseInfo.setAlignment(Align.right);


		// Add UI components to screen.
		stage.addActor(phaseInfo);
		stage.addActor(nextButton);
		stage.addActor(btnNewGame);

		// Setup UI Layout.
		// Row: Player and Market Stats.
		add(playerStats);
		add().spaceRight(70);
		add(bg).padLeft(-40).padRight(90).padBottom(50).padTop(20);;
		rowWithHeight(1);

		// Row: Label of Sell and Buy
		add();
		add();
		add();
		rowWithHeight(1);

		// Row: Ore buy/sell

		add();

		rowWithHeight(1);

		// Row: Energy buy/sell
		rowWithHeight(1);

		// Row: Food buy/sell
		bindEvents();
		widgetUpdate();
	}

	/**
	 * Bind button events.
	 */
	private void bindEvents() {
		btnNewGame.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);
				game.gameScreen.newGame();
			}
		});

		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
	/**
	 * Add an empty row to current table.
	 * @param height  The height for that empty row.
	 */
	private void rowWithHeight(int height) {
		row();
		add().spaceTop(height);
		row();
	}

	/**
	 * Updates all widgets on screen
	 */
	public void widgetUpdate() {
		// update player stats, phase text, and the market stats.
		int[] score=game.scoreCalc(game.playerList);
		int eventInd = score[0];
		String statText;
		game.getPlayerInt();
		game.getPhase();
		game.getPhaseString();
		
		bg.setDrawable(new TextureRegionDrawable(new TextureRegion(END_TEXTURES[eventInd])));
		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		if (game.getNumberOfPlayers()==2) {
			statText = "    SCORE:                      PLAYER 1-" + score[1] + "                       PLAYER 2-" + score[2];
		} else if (game.getNumberOfPlayers()==3) {
			statText = "    SCORE:          PLAYER 1-" + score[1] + "           PLAYER 2-" + score[2]+ "PLAYER 3-" + score[3];
		} else {
			statText = "    SCORE: PLAYER 1-" + score[1] + " PLAYER 2-" + score[2] + " PLAYER 3-" + score[3] + " PLAYER 4-" + score[4];
		}
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
		

	}


	/**
	 * Respond to the screen resize event, updates widgets position
	 * accordingly.
	 * @param width    The new width.
	 * @param height   The new Height.
	 */
	public void screenResize(float width, float height) {
		// Bottom Left
		phaseInfo.setPosition(0, height - 20);
		phaseInfo.setWidth(width - 10);

		// Bottom Right
		nextButton.setPosition(width/2 - (nextButton.getWidth()+20), 10);
		btnNewGame.setPosition(width/2 + btnNewGame.getWidth() - 100, 10);
		setWidth(width);
	}
}
