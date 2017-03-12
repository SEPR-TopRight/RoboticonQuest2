package io.github.teamfractal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.teamfractal.animation.*;
import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.*;
import io.github.teamfractal.util.PlotManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the main game boot up class.
 * It will set up all the necessary classes.
 */
public class RoboticonQuest extends Game {
	static RoboticonQuest instance;
	public static RoboticonQuest getInstance() {
		return instance;
	}
	private int tilemax=84;
	private int counter=0;
	private int turn=0;
	private int endturn=20;
	private PlotManager plotManager;
	private SpriteBatch batch;
	public Skin skin;
	public MainMenuScreen mainMenuScreen;
	public GameScreen gameScreen;
	private int phase;
	private int currentPlayer;
	public ArrayList<Player> playerList;
	public Market market;
	private int landBoughtThisTurn;
	private int numberOfPlayers = 2; // Added by Josh Neil
	private Random random = new Random();
	private Music music;
	
	public void incCount(){
		counter+=1;
		System.out.println(counter);
	}
	
	public int getPlayerIndex (Player player) {
		return playerList.indexOf(player);
	}

	public TiledMap tmx;
	
	public RoboticonQuest(){
		instance = this;
		reset();
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setupSkin();
		
		gameScreen = new GameScreen(this);

		// Setup other screens.
		mainMenuScreen = new MainMenuScreen(this);
		
		setScreen(mainMenuScreen);
		music = Gdx.audio.newMusic(Gdx.files.internal("music/FloatingCities.mp3"));
		music.play();
		music.setLooping(true);
	}

	public Batch getBatch() {
		return batch;
	}

	/**
	 * Setup the default skin for GUI components.
	 */
	private void setupSkin() {
		// Josh Neil moved back to the default skin because we didn't like the pink text
		skin = new Skin(
				Gdx.files.internal("skin/skin.json"),
				new TextureAtlas(Gdx.files.internal("skin/skin.atlas"))
			);
	}

	/**
	 * Clean up
	 */
	@Override
	public void dispose () {
		mainMenuScreen.dispose();
		gameScreen.dispose();
		skin.dispose();
		batch.dispose();
	}
	
	public int getPhase(){
		return this.phase;
	}
	
	// Added by Josh Neil so that we can have between 2 and 4 players
	public void setNumberOfPlayers(int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
	}

	public void reset() {
		this.currentPlayer = 0;
		this.phase = 0;
		this.playerList = new ArrayList<Player>();
		// Modified by Josh Neil to support an aribitary number of players
		for(int player =0; player<numberOfPlayers; player++){
			Player p = new Player(this);
			this.playerList.add(p);
		}
		this.currentPlayer = 0;
		this.market = new Market();
		plotManager = new PlotManager();
	}

	public void nextPhase () {
		if (phase + 1 == 5) {
			if (random.nextInt(20)>=10){
				phase = 5;
			} else {
				phase = 6;
			}

		} else if (phase + 1 == 8) {
			if (random.nextInt(25)>= 20) {
				phase = 8;
			} else {
				phase = 9;
			}
		} else {
			phase = phase + 1;
		}

		switch (phase) {
			// Phase 2: Purchase Roboticon
			case 2:
				TimedMenuScreen roboticonMarket = new TimedMenuScreen(this,false);
				roboticonMarket.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, phase, 30));
				setScreen(roboticonMarket);
				break;

			// Phase 3: Roboticon Customisation
			case 3:
				AnimationPhaseTimeout timeoutAnimation = new AnimationPhaseTimeout(getPlayer(), this, phase, 30);
				gameScreen.addAnimation(timeoutAnimation);
				timeoutAnimation.setAnimationFinish(new IAnimationFinish() {
					@Override
					public void OnAnimationFinish() {
						gameScreen.getActors().hideInstallRoboticon();
					}
				});
				gameScreen.getActors().updateRoboticonSelection();
				setScreen(gameScreen);
				break;

			// Phase 4: Purchase Resource
			case 4:
				gameScreen.getActors().disableNextBtn();
				generateResources();
				break;

			//Phase 5: Chancellor encounter
			case 5:
				gameScreen.activateChancellor();
				AnimationPhaseTimeout timeoutChancellorPhase = new AnimationPhaseTimeout(getPlayer(), this, phase, 15);
				gameScreen.addAnimation(timeoutChancellorPhase);
				timeoutChancellorPhase.setAnimationFinish(new IAnimationFinish() {
					@Override
					public void OnAnimationFinish() {
						gameScreen.stopChancellor();
					}
				} );
				break;

			//Phase 6: Gambling
			case 6:
				gameScreen.getActors().enableNextBtn();
				TimedMenuScreen minigame = new TimedMenuScreen(this,true);
				minigame.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, phase, 30));
				setScreen(minigame);
				break;
			
			// Phase 7: Generate resource for player.
			case 7:
				setScreen(new ResourceMarketScreen(this));
				break;
										
			case 8:
				//RANDOM EVENT
				setScreen(new RoboticonRandomScreen(this));
				break;
			// End phase - CLean up and move to next player.
			case 9:
				phase = 1;
				this.turn+=1;
				System.out.println(this.turn);
				this.nextPlayer();
				if (this.turn>=endturn || counter>=tilemax){
					setScreen(new GameOverScreen(this));
					System.out.println("win cond");
					break;
				}
				
				// No "break;" here!
				// Let the game to do phase 1 preparation.

			// Phase 1: Enable of purchase LandPlot
			case 1:
				setScreen(gameScreen);
				landBoughtThisTurn = 0;
				gameScreen.addAnimation(new AnimationShowPlayer(getPlayerInt() + 1));
				break;
		}

		if (gameScreen != null)
			gameScreen.getActors().textUpdate();
	}


	public int[] scoreCalc(ArrayList<Player> pl) {
		// score is tot money after selling all resources
		int[] score=new int[3];
		score[1]=pl.get(0).getMoney()+pl.get(0).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(0).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(0).getFood()*this.market.getSellPrice(ResourceType.FOOD);
		score[2]=pl.get(1).getMoney()+pl.get(1).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(1).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(1).getFood()*this.market.getSellPrice(ResourceType.FOOD);
		if(score[1]>score[2])
			score[0]=1;
		else if(score[2]>score[1])
			score[0]=2;
		else
			score[0]=3;
		return score;
	}

	// Note this method is re-used from our (Top Right Corner's) old project where we had
	// made the modifications described below
	/**
	 * Phase 4: generate resources.
	 */
	private void generateResources() {
		// Switch back to purchase to game screen.
		setScreen(gameScreen);

		// Generate resources.
		Player p = getPlayer();
		
		// Modified by Josh Neil - now accepts the values returned by Player.generateResources()
		// and produces an animation that displays this information on screen (see Player.generateResources
		// for a more in depth explanation)
		HashMap<ResourceType,Integer> generatedResources = p.generateResources();
		int energy = generatedResources.get(ResourceType.ENERGY);
		int food = generatedResources.get(ResourceType.FOOD);
		int ore = generatedResources.get(ResourceType.ORE);
		IAnimation animation = new AnimationAddResources(p, energy, food, ore);
		animation.setAnimationFinish(new IAnimationFinish() {
			@Override
			public void OnAnimationFinish() {
					nextPhase();
			}
		});
		gameScreen.addAnimation(animation);
	}

	/**
	 * Event callback on player bought a {@link io.github.teamfractal.entity.LandPlot}
	 */
	public void landPurchasedThisTurn() {
		landBoughtThisTurn ++;
	}


	// TODO: Would a lookup array be better?
	public String getPhaseString () {
		int phase = getPhase();

		switch(phase){
			case 1:
				return "Buy Land Plot";

			case 2:
				return "Purchase Roboticons";

			case 3:
				return "Install Roboticons";

			case 4:
				return "Resource Generation";

			case 5:
				return "Capture the Chancellor";

			case 6:
				return "Gambling";
			
			case 7:
				return "Resource Auction";
			
			case 8:
				return "Random Event";
				
			default:
				return "Unknown phase";
		}

	}

	public Player getPlayer(){
		return this.playerList.get(this.currentPlayer);
	}
	
	public int getPlayerInt(){
		return this.currentPlayer;
	}
	
	public void nextPlayer() {
		this.currentPlayer = (this.currentPlayer + 1) % playerList.size();
	}

	public PlotManager getPlotManager() {
		return plotManager;
	}

	public Music getMusic() {return music;}

	public void setMusic(FileHandle fileName) {
		music.stop();
		music = Gdx.audio.newMusic(fileName);
		music.play();
		music.setLooping(true);
	}
}
