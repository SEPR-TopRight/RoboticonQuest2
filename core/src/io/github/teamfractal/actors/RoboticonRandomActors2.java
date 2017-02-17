package io.github.teamfractal.actors;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.ResourceMarketScreen;
import io.github.teamfractal.screens.RoboticonRandomScreen;

public class RoboticonRandomActors2 extends Table {
	private RoboticonQuest game;
	private Label phaseInfo;
	private Label playerStats;
	private RoboticonRandomScreen screen;
	private TextButton nextButton;
	private static final Texture event[]=new Texture[9];
	private Image bg = new Image();
	private Random random = new Random();
	static {
		event[0] = new Texture(Gdx.files.internal("events/landscape.png"));
		event[1] = new Texture(Gdx.files.internal("events/moneymin.png"));
		event[2] = new Texture(Gdx.files.internal("events/foodpl.png"));
		event[3] = new Texture(Gdx.files.internal("events/foodmin.png"));
		event[4] = new Texture(Gdx.files.internal("events/orepl.png"));
		event[5]=new Texture(Gdx.files.internal("cards/tie.png"));
		event[6] = new Texture(Gdx.files.internal("cards/rock.png"));
		event[7] = new Texture(Gdx.files.internal("cards/paper.png"));
		event[8] = new Texture(Gdx.files.internal("cards/scissors.png"));
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
	 * @param screen     The screen object.
	 */
	public RoboticonRandomActors2(final RoboticonQuest game, RoboticonRandomScreen screen) {
		center();

		Skin skin = game.skin;
		this.game = game;
		this.screen = screen;
		Stage stage = screen.getStage();
		// Create UI Components
		phaseInfo = new Label("", game.skin);
		nextButton = new TextButton("Next ->", game.skin);

		playerStats = new Label("", game.skin);
		new Label("", game.skin);
		new Label("TRIAL",  skin);
		
		// Adjust properties.
		phaseInfo.setAlignment(Align.right);


		// Add UI components to screen.
		stage.addActor(phaseInfo);
		stage.addActor(nextButton);


		// Setup UI Layout.
		// Row: Player and Market Stats.
		add(playerStats);
		add().spaceRight(70);
		add(bg).padLeft(-40).padRight(90).padBottom(0).padTop(20);;
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
		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.nextPhase();
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
		int eventInd = random.nextInt(8)+1;
		game.getPlayerInt();
		game.getPhase();
		game.getPhaseString();
		
		bg.setDrawable(new TextureRegionDrawable(new TextureRegion(event[eventInd])));
		execEv(eventInd);
		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " + game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
		

	}

	private void execEv(int Ind) {
		switch(Ind){
		case 1:	game.getPlayer().event(50,0,0,0);
				break;
		case 2:	game.getPlayer().event(-50,0,0,0);
				break;
		case 3:	game.getPlayer().event(0,50,0,0);
				break;
		case 4:	game.getPlayer().event(0,-50,0,0);
				break;
		case 5:	game.getPlayer().event(0,0,50,0);
				break;
		case 6:	game.getPlayer().event(0,0,-50,0);
				break;
		case 7:	game.getPlayer().event(0,0,0,50);
				break;
		case 8:	game.getPlayer().event(0,0,0,-50);
				break;
		default:
				break;
		}
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
		nextButton.setPosition(width - nextButton.getWidth() - 10, 10);

		setWidth(width);
	}
}
