package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.ResourceMarketScreen;
import io.github.teamfractal.screens.TimedMenuScreen;
import io.github.teamfractal.util.RPSAI;
import static io.github.teamfractal.util.RPSAI.moves;
import static io.github.teamfractal.util.RPSAI.results;

import java.util.Random;

//@author jormandr
public class RoboticonMinigameActors extends Table {
	private RoboticonQuest game;
	private ResourceMarketScreen screen;
	private Integer betAmount = 10;
	private Texture resultTexture;
	private Texture playerTexture;
	private Texture AITexture;
	private Label topText;
	private Label playerStats;
	private static final Texture TEXTURE_WIN = new Texture(Gdx.files.internal("cards/win.png"));
	private static final Texture TEXTURE_LOSE = new Texture(Gdx.files.internal("cards/lose.png"));
	private static final Texture TEXTURE_TIE = new Texture(Gdx.files.internal("cards/tie.png"));
	private static final Texture TEXTURE_UNKNOWN = new Texture(Gdx.files.internal("cards/unknown.png"));
	private static final Texture TEXTURE_BANKRUPT = new Texture(Gdx.files.internal("cards/bankrupt.png"));
	private static final Texture TEXTURE_ROCK = new Texture(Gdx.files.internal("cards/rock.png"));
	private static final Texture TEXTURE_PAPER = new Texture(Gdx.files.internal("cards/paper.png"));
	private static final Texture TEXTURE_SCISSORS = new Texture(Gdx.files.internal("cards/scissors.png"));;
	private Image card = new Image();
	private Image rpspl = new Image();
	private Image rpscom = new Image();
	private RPSAI rpsai = new RPSAI();
	
	// Added by Josh Neil (Top Right Corner) so that we can select which player is to gamble
	private SelectBox<String> playerDropDown;

	Random rand = new Random();
	private final int BET_CHANGE_STEP = 10;

	public RoboticonMinigameActors(final RoboticonQuest game, ResourceMarketScreen screen) {
		this.game = game;
		this.screen = screen;

		new Label("", game.skin);
		new Label("", game.skin);
		resultTexture = TEXTURE_UNKNOWN;
		playerTexture = TEXTURE_UNKNOWN;
		AITexture = TEXTURE_UNKNOWN;
		
		// Added by Josh Neil (Top Right Corner) so that we can select which player is to gamble
		createPlayerDropDown();
		
		widgetUpdate();

		final Label lblBet = new Label("bets", game.skin);

		final Label lblbetAmount = new Label(betAmount.toString(), game.skin);


		// Button to increase bet amount
		final TextButton addRoboticonButton = new TextButton("+", game.skin);
		addRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				betAmount += BET_CHANGE_STEP;
				lblbetAmount.setText(betAmount.toString());
			}
		});

		// Button to decrease bet amount
		final TextButton subRoboticonButton = new TextButton("-", game.skin);
		subRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (betAmount > 0) {
					betAmount -= BET_CHANGE_STEP;
					lblbetAmount.setText(betAmount.toString());
				}
			}
		});
		
		final TextButton nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.nextPhase();
			}
		});

		// Button to start the gamble
		final TextButton rock = new TextButton("ROCK", game.skin);
		rock.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				rpsai.setHumanMove(moves.ROCK);
				handleRPSResult();
			}
		});

		final TextButton paper = new TextButton("PAPER", game.skin);
		paper.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				rpsai.setHumanMove(moves.PAPER);
				handleRPSResult();
			}
		});

		final TextButton scissors = new TextButton("SCISSORS", game.skin);
		scissors.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				rpsai.setHumanMove(moves.SCISSORS);
				handleRPSResult();
			}
		});

		// Josh Neil (Top Right Corner) combined the first two rows of the table
		// bet inc & dec buttons,
		add(playerDropDown); // Added by Josh Neil (Top Right Corner)
		add(lblBet);
		
		add(subRoboticonButton);
		add(lblbetAmount);
		add(addRoboticonButton);
		add(new Label("credits",game.skin));
		row();

		add(rock).colspan(2);
		add(paper).colspan(2);
		add(scissors).colspan(2);
		row();

		// image of the card
		add(rpspl).padLeft(30).padRight(30).padBottom(30).colspan(2);
		add(card).padLeft(30).padRight(30).padBottom(30).colspan(2);
		add(rpscom).padLeft(30).padRight(30).padBottom(30).colspan(2);

	}
	
	// Added by Josh Neil (Top Right Corner)
	
	/**
	 * Creates the drop down menu that is used to select the player that is gambling
	 */
	private void createPlayerDropDown(){
		playerDropDown = new SelectBox<String>(game.skin);
		String[] players = new String[game.playerList.size()];
		for(int player=0;player<game.playerList.size();player++){
			players[player] = "Player "+Integer.toString(player+1);
		}
		playerDropDown.setItems(players);
	}
	
	private void handleRPSResult() {
		// Added by Josh Neil (Top Right Corner) so that the selected player gambles
		// also replaced all calls to game.getPlayer() with player
		int playerIndex = playerDropDown.getSelectedIndex();
		Player player = game.playerList.get(playerIndex);
		if (betAmount <= player.getMoney()) {
			moves humanMove   = rpsai.getHumanMove();
			moves AIMove      = rpsai.getAIMove();
			results RPSResult = rpsai.getResult();
			
			

			// ?: is more concise than switch

			playerTexture = humanMove == moves.ROCK     ? TEXTURE_ROCK
					      : humanMove == moves.PAPER    ? TEXTURE_PAPER
						  : humanMove == moves.SCISSORS ? TEXTURE_SCISSORS
						  : TEXTURE_UNKNOWN;

			// Duplicated code, but deduplication would probably be more complex

			AITexture = AIMove == moves.ROCK     ? TEXTURE_ROCK
					  : AIMove == moves.PAPER    ? TEXTURE_PAPER
					  : AIMove == moves.SCISSORS ? TEXTURE_SCISSORS
					  : TEXTURE_UNKNOWN;

			resultTexture = RPSResult == results.WIN  ? TEXTURE_WIN
					      : RPSResult == results.LOSE ? TEXTURE_LOSE
						  : RPSResult == results.TIE  ? TEXTURE_TIE
						  : TEXTURE_UNKNOWN;

			if (RPSResult == results.WIN) {
				player.gambleResult(true, betAmount);
			} else if (RPSResult == results.LOSE) {
				player.gambleResult(false, betAmount);
			}
		} else {
			playerTexture = TEXTURE_UNKNOWN;
			AITexture     = TEXTURE_UNKNOWN;
			resultTexture = TEXTURE_BANKRUPT;
		}
		
		widgetUpdate();
	}
	

	public void widgetUpdate() {
		card.setDrawable(new TextureRegionDrawable(new TextureRegion(resultTexture)));
		rpspl.setDrawable(new TextureRegionDrawable(new TextureRegion(playerTexture)));
		rpscom.setDrawable(new TextureRegionDrawable(new TextureRegion(AITexture)));

	}
}
