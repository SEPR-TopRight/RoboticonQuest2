package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.RoboticonMinigameScreen;
import io.github.teamfractal.util.RPSAI;
import static io.github.teamfractal.util.RPSAI.moves;
import static io.github.teamfractal.util.RPSAI.results;

import java.util.Random;

//@author jormandr
public class RoboticonMinigameActors extends Table {
	private RoboticonQuest game;
	private RoboticonMinigameScreen screen;
	private Integer betAmount = 0;
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

	Random rand = new Random();
	private final int BET_CHANGE_STEP = 10;

	public RoboticonMinigameActors(final RoboticonQuest game, RoboticonMinigameScreen roboticonMinigameScreen) {
		this.game = game;
		this.screen = roboticonMinigameScreen;

		new Label("", game.skin);
		new Label("", game.skin);
		resultTexture = TEXTURE_UNKNOWN;
		playerTexture = TEXTURE_UNKNOWN;
		AITexture = TEXTURE_UNKNOWN;
		widgetUpdate();

		final Label lblBet = new Label("Bet:", game.skin);

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

		// Top Row Text
		add();
		add();
		add();
		add();

		row();

		// bet inc & dec buttons,
		add(lblBet).padTop(100).padLeft(10);
		add(subRoboticonButton).padTop(100).padLeft(100);
		add(lblbetAmount).padTop(100).padLeft(-50);
		add(addRoboticonButton).padTop(100).padLeft(-100);

		row();

		// button to start the bet (moved to different row to preserve position
		// of other buttons)
		add();
		add(rock).padLeft(0).padBottom(160);
		add(paper).padLeft(50).padBottom(160);
		add(scissors).padLeft(150).padBottom(160);
		add();

		add();
		row();

		add();
		add();
		add();
		add();

		add();
		add();

		row();
		// image of the card
		add();
		add();
		add(rpspl).padLeft(-150).padRight(100).padBottom(60).padTop(-150);
		add(card).padLeft(-100).padRight(150).padBottom(60).padTop(-150);
		add(rpscom).padLeft(-150).padRight(-50).padBottom(60).padTop(-150);
		add();

		row();

		row();

		add();
		add();
		add();
		add();

		add();
		add(nextButton).padTop(40);

	}
	
	private void handleRPSResult() {
		if (betAmount < game.getPlayer().getMoney()) {
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
				game.getPlayer().gambleResult(true, betAmount);
			} else if (RPSResult == results.LOSE) {
				game.getPlayer().gambleResult(false, betAmount);
			}
		} else {
			playerTexture = TEXTURE_UNKNOWN;
			AITexture     = TEXTURE_UNKNOWN;
			resultTexture = TEXTURE_BANKRUPT;
		}
		
		widgetUpdate();
	}
	

	public void widgetUpdate() {

		// Draws turn and phase info on screen
		if (this.topText != null)
			this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getWidth() / 2 - 40,
				screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		card.setDrawable(new TextureRegionDrawable(new TextureRegion(resultTexture)));
		rpspl.setDrawable(new TextureRegionDrawable(new TextureRegion(playerTexture)));
		rpscom.setDrawable(new TextureRegionDrawable(new TextureRegion(AITexture)));
		// Draws player stats on screen
		if (this.playerStats != null)
			this.playerStats.remove();
		String statText = "Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);

	}
}
