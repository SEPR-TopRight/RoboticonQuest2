package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.teamfractal.RoboticonQuest;


public class HomeMainMenu extends Table {
	private RoboticonQuest game;
	private TextButton btnNewGame;
	private TextButton btnExit;
	
	// Added by Josh Neil to allow the user to select the number of players that are playing the game
	private SelectBox<String> numberOfPlayersDropDown;

	private static Texture titleTexture = new Texture(Gdx.files.internal("roboticon_images/Roboticon_Quest_Title"));

	/**
	 * Initialise the Home Menu.
	 * @param game    The game object.
	 */
	public HomeMainMenu(RoboticonQuest game) {
		this.game = game;

		// Create UI Components
		final Image imgTitle = new Image();
		imgTitle.setDrawable(new TextureRegionDrawable(new TextureRegion(titleTexture)));
		
		//Roboticon text to go next to + and - buttons
		final Label lblCredits = new Label("Song Credits: 'Floating Cities' \n Kevin MacLeod\n licensed: CC-BY",game.skin);

		// Added by Josh Neil
		createNumberOfPlayersDropDown();	
		
		btnNewGame = new TextButton("New game!", game.skin);
		btnExit = new TextButton("Exit", game.skin);

		// Adjust properties.
		btnNewGame.pad(10);
		btnExit.pad(10);

		// Bind events.
		bindEvents();

		// Add UI Components to table.
		add(imgTitle);
		row();
		// Added by Josh Neil
		add(numberOfPlayersDropDown);
		row();
		add(btnNewGame).pad(5);
		row();
		add(btnExit).pad(5);
		row();
		row();
		add(lblCredits);
	}
	
	// Added by Josh Neil to create the drop down required so that users may select the number of players that they want to play the game
	private void createNumberOfPlayersDropDown(){
		
		numberOfPlayersDropDown = new SelectBox<String>(game.skin);
		String options[] = {"2 players","3 players","4 players"};
		numberOfPlayersDropDown.setItems(options);
		numberOfPlayersDropDown.setSelectedIndex(0);
	}

	/**
	 * Bind button events.
	 */
	private void bindEvents() {
		btnNewGame.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				game.setScreen(game.gameScreen);
				
				// Added by Josh to set the correct number of players
				game.setNumberOfPlayers(numberOfPlayersDropDown.getSelectedIndex()+2);
				
				game.gameScreen.newGame();
			}
		});

		btnExit.addListener(new ClickListener() {
			@Override
			public void clicked (InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
	}
}
