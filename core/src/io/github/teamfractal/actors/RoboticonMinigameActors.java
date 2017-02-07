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

import java.util.Random;
//@author jormandr
public class RoboticonMinigameActors extends Table {
	private RoboticonQuest game;
	private RoboticonMinigameScreen screen;
	private Integer betAmount = 0;
	private Texture texture;
	private Label topText;
	private Label playerStats;
	private boolean multiplier= false;
	private static final Texture win;
	private static final Texture lose;
	private static final Texture unknown;
	private static final Texture broke;
	private Image card = new Image();
	Random random = new Random();
	static {
		broke = new Texture(Gdx.files.internal("cards/bankrupt.png"));
		unknown = new Texture(Gdx.files.internal("cards/unknown.png"));
		win = new Texture(Gdx.files.internal("cards/win.png"));
		lose = new Texture(Gdx.files.internal("cards/lose.png"));
	}

	public RoboticonMinigameActors(final RoboticonQuest game, RoboticonMinigameScreen roboticonMinigameScreen) {
		this.game = game;
		this.screen = roboticonMinigameScreen;
	    
		new Label("", game.skin);
		new Label("", game.skin);
		texture=unknown;
		
		widgetUpdate();

		
		final Label lblBet = new Label("Bet:", game.skin);
		
		final Label lblbetAmount = new Label(betAmount.toString(), game.skin);
		
		// Button to increase bet amount
		final TextButton addRoboticonButton = new TextButton("+", game.skin);
		addRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				betAmount += 10;
				lblbetAmount.setText(betAmount.toString());
			}
		});

		// Button to decrease bet amount
		final TextButton subRoboticonButton = new TextButton("-", game.skin);
		subRoboticonButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (betAmount > 0) {
					betAmount -= 10;
					lblbetAmount.setText(betAmount.toString());
				}
			}
		});

		// Button to start the gamble
		final TextButton buyRoboticonsButton = new TextButton("GAMBLE!", game.skin);
		buyRoboticonsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(betAmount<game.getPlayer().getMoney()){
					multiplier=random.nextBoolean();
					if(multiplier){
						texture=win;
					}
					else{
						texture=lose;
					}
					game.getPlayer().gambleResult(multiplier, betAmount);	
				}
				else{
					texture=broke;
				}
				
				widgetUpdate();
			}
		});
				

		/*imagebutton try, wasnt working
		 * final ImageButton card = new ImageButton(texDrawable); //Set the button up
		card.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
					multiplier=random.nextBoolean();
					if(multiplier){
						texture=win;
					}
					else{
						texture=lose;
					}
					
					widgetUpdate();
			}
		});
*/




		

		final TextButton nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.nextPhase();
			}
		});

		// Top Row Text
		add();
		add();
		add();
		add();

		row();

		// bet inc & dec buttons,
		add(lblBet).padTop(40).padLeft(10);
		add(subRoboticonButton).padTop(40);
		add(lblbetAmount).padTop(40);
		add(addRoboticonButton).padTop(40);

		add();
		add();
		add();

		row();

		// button to start the bet (moved to different row to preserve position of other buttons)
		add();
		add(buyRoboticonsButton).padLeft(250).padBottom(160);
		add();
		add();
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
		add();
		add(card).padLeft(-250).padRight(50).padBottom(10).padTop(-50);
		add();
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



	public void widgetUpdate() {
		
		// Draws turn and phase info on screen
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getWidth() / 2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		card.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
		
		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
		
	}

}
