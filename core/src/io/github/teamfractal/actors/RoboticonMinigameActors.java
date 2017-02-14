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
	private Texture texturepl;
	private Texture texturecom;
	private Label topText;
	private Label playerStats;
	private int multiplier= 0;
	private static final Texture win;
	private static final Texture lose;
	private static final Texture tie;
	private static final Texture unknown;
	private static final Texture broke;
	private static final Texture irock;
	private static final Texture ipaper;
	private static final Texture iscissors;
	private Image card = new Image();
	private Image rpspl = new Image();
	private Image rpscom = new Image();
	Random random = new Random();
	static {
		broke = new Texture(Gdx.files.internal("cards/bankrupt.png"));
		unknown = new Texture(Gdx.files.internal("cards/unknown.png"));
		win = new Texture(Gdx.files.internal("cards/win.png"));
		lose = new Texture(Gdx.files.internal("cards/lose.png"));
		tie=new Texture(Gdx.files.internal("cards/unknown.png"));
		irock = new Texture(Gdx.files.internal("cards/win.png"));
		ipaper = new Texture(Gdx.files.internal("cards/lose.png"));
		iscissors = new Texture(Gdx.files.internal("cards/bankrupt.png"));
	}
	private enum rps{
		ROCK,PAPER,SCISSORS,INIT
	}
	private rps playerChoice=rps.INIT;
	public RoboticonMinigameActors(final RoboticonQuest game, RoboticonMinigameScreen roboticonMinigameScreen) {
		this.game = game;
		this.screen = roboticonMinigameScreen;
	    
		new Label("", game.skin);
		new Label("", game.skin);
		texture=unknown;
		texturepl=unknown;
		texturecom=unknown;
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
		final TextButton rock = new TextButton("ROCK", game.skin);
		rock.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(betAmount<game.getPlayer().getMoney()){
					playerChoice=rps.ROCK;
					texturepl=irock;
					multiplier=random.nextInt(3);
					if(rpsCheck(playerChoice,multiplier)==1){
						texture=win;
						texturecom=iscissors;
						game.getPlayer().gambleResult(true, betAmount);
					}
					else if(rpsCheck(playerChoice,multiplier)==0){
						texture=lose;
						texturecom=ipaper;
						game.getPlayer().gambleResult(false, betAmount);
					}
					else{
						texture=tie;
						texturecom=irock;
					}
						
				}
				else{
					texture=broke;
				}
				
				widgetUpdate();
			}


		});

		final TextButton paper = new TextButton("PAPER", game.skin);
		paper.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(betAmount<game.getPlayer().getMoney()){
					playerChoice=rps.PAPER;
					texturepl=ipaper;
					multiplier=random.nextInt(3);
					if(rpsCheck(playerChoice,multiplier)==1){
						texture=win;
						texturecom=irock;
						game.getPlayer().gambleResult(true, betAmount);
					}
					else if(rpsCheck(playerChoice,multiplier)==-1){
						texture=lose;
						texturecom=iscissors;
						game.getPlayer().gambleResult(false, betAmount);
					}
					else{
						texture=tie;
						texturecom=ipaper;
					}
						
				}
				else{
					texture=broke;
				}
				
				widgetUpdate();
			}
		});

		final TextButton scissors = new TextButton("SCISSORS", game.skin);
		scissors.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(betAmount<game.getPlayer().getMoney()){
					playerChoice=rps.SCISSORS;
					texturepl=iscissors;
					multiplier=random.nextInt(3);
					if(rpsCheck(playerChoice,multiplier)==1){
						texture=win;
						texturecom=ipaper;
						game.getPlayer().gambleResult(true, betAmount);
					}
					else if(rpsCheck(playerChoice,multiplier)==0){
						texture=lose;
						texturecom=irock;
						game.getPlayer().gambleResult(false, betAmount);
					}
					else{
						texture=tie;
						texturecom=iscissors;
					}
						
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
		add(lblBet).padTop(100).padLeft(10);
		add(subRoboticonButton).padTop(100).padLeft(100);
		add(lblbetAmount).padTop(100).padLeft(-50);
		add(addRoboticonButton).padTop(100).padLeft(-100);

		row();

		// button to start the bet (moved to different row to preserve position of other buttons)
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



	public void widgetUpdate() {
		
		// Draws turn and phase info on screen
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getWidth() / 2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		card.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
		rpspl.setDrawable(new TextureRegionDrawable(new TextureRegion(texturepl)));
		rpscom.setDrawable(new TextureRegionDrawable(new TextureRegion(texturecom)));
		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
		
	}
	private int rpsCheck(rps playerChoice, int multiplier) {
		int result=0;
		multiplier-=1;
		if(playerChoice==rps.ROCK){
			if(multiplier==0){
				result=-1;
			}
			else if(multiplier==-1){
				result=1;
			}
		}
			
		else if(playerChoice==rps.PAPER){
		if(multiplier==-1){
			result=-1;
		}
		else if(multiplier==1){
			result=1;
		}
	}
		else if(playerChoice==rps.SCISSORS){
		if(multiplier==1){
			result=-1;
		}
		else if(multiplier==0){
			result=1;
		}
	}	
		return result;
	}
}
