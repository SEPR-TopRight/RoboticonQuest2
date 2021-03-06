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
// For a compiled executable version of the game please see: https://sepr-topright.github.io/SEPR/documentation/assessment4/game.zip
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
	private int numberOfPlayers = 2; // Added by Josh Neil - Top Right Corner
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
		// Josh Neil - Top Right Corner - moved back to the default skin because we didn't like the pink text
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
	
	// Added by Josh Neil - Top Right Corner - so that we can have between 2 and 4 players
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
		// Changed order of phases to allow shared market screen and gambling - Ben (Top Right Corner)
		// No longer have a separate gambling phase and the shared market phase occurs after all players have had their turn
		// rather than after each players turn
		phase = phase + 1;
		if (phase == 5 && (random.nextInt(20)<=10)) {
			phase = 6;
		}
		if ((phase == 6) && (this.currentPlayer != this.numberOfPlayers - 1)){
			phase = 7;
		}
		if (phase == 7 && (random.nextInt(25)< 20)) {
			phase = 8;
		}

		switch (phase) {
			// Phase 2: Purchase Roboticon
			case 2:
				TimedMenuScreen roboticonMarket = new TimedMenuScreen(this);
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
			//Added by Christian Beddows - Top Right Corner - to add in the capture the chancellor phase
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

			
			// Phase 7: Generate resource for player.
			case 6:
				setScreen(new ResourceMarketScreen(this));
				break;
										
			case 7:
				//RANDOM EVENT
				setScreen(new RoboticonRandomScreen(this));
				break;
			// End phase - CLean up and move to next player.
			case 8:
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
				gameScreen.getActors().enableNextBtn();
				setScreen(gameScreen);
				landBoughtThisTurn = 0;
				gameScreen.addAnimation(new AnimationShowPlayer(getPlayerInt() + 1));
				break;
		}

		if (gameScreen != null)
			gameScreen.getActors().textUpdate();
	}


	public int[] scoreCalc(ArrayList<Player> pl) {
		// score is total money after selling all resources
		int[] score=new int[5];
		score[1]=pl.get(0).getMoney()+pl.get(0).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(0).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(0).getFood()*this.market.getSellPrice(ResourceType.FOOD);
		score[2]=pl.get(1).getMoney()+pl.get(1).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(1).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(1).getFood()*this.market.getSellPrice(ResourceType.FOOD);
		if (getNumberOfPlayers() == 3) {score[3]=pl.get(2).getMoney()+pl.get(2).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(2).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(2).getFood()*this.market.getSellPrice(ResourceType.FOOD);}
		if (getNumberOfPlayers() == 4) {score[4]=pl.get(3).getMoney()+pl.get(3).getEnergy()*this.market.getSellPrice(ResourceType.ENERGY)+pl.get(3).getOre()*this.market.getSellPrice(ResourceType.ORE)+pl.get(3).getFood()*this.market.getSellPrice(ResourceType.FOOD);}
		
		// Added by Josh Neil (Top Right Corner) - calculate the highest scoring player
		int highestScore = 0;
		int highestScoreingPlayer =1;
		for(int scoreIndex=1;scoreIndex<score.length;scoreIndex++){
			if (score[scoreIndex]>highestScore){
				highestScore=score[scoreIndex];
				highestScoreingPlayer=scoreIndex;
			}
		}
		
		// Added by Josh Neil (Top Right Corner) - check if all players have drawn
		Boolean allScoresSame = true;
		for(int scoreIndex=2;scoreIndex<score.length;scoreIndex++){
			if (score[scoreIndex]!=score[1]){ // If a given player has not score the same as player 1
				allScoresSame=false;
			}
		}
	
		if(allScoresSame){
			highestScoreingPlayer=5; // indicates draw
		}
		
		score[0]=highestScoreingPlayer;
		
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
		
		// Modified by Josh Neil - Top Right Corner - now accepts the values returned by Player.generateResources()
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
				return "Capture the Chancellor"; //Added by Christian Beddows - Top Right Corner

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

	//Added by Christian Beddows - Top Right Corner - to change the music for the chancellor mode
	public void setMusic(FileHandle fileName) {
		music.stop();
		music = Gdx.audio.newMusic(fileName);
		music.play();
		music.setLooping(true);
	}

	public int getNumberOfPlayers() {return numberOfPlayers;}
}
