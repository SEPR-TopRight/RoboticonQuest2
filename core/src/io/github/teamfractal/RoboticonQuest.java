package io.github.teamfractal;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.teamfractal.animation.AnimationPhaseTimeout;
import io.github.teamfractal.animation.AnimationShowPlayer;
import io.github.teamfractal.animation.IAnimationFinish;
import io.github.teamfractal.screens.*;
import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.util.PlotManager;

/**
 * This is the main game boot up class.
 * It will set up all the necessary classes.
 */
public class RoboticonQuest extends Game {
	static RoboticonQuest instance;
	public static RoboticonQuest getInstance() {
		return instance;
	}

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
	}

	public Batch getBatch() {
		return batch;
	}

	/**
	 * Setup the default skin for GUI components.
	 */
	private void setupSkin() {
		skin = new Skin(
			Gdx.files.internal("skin/neon-ui.json"),
			new TextureAtlas(Gdx.files.internal("skin/neon-ui.atlas"))
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

	public void reset() {
		this.currentPlayer = 0;
		this.phase = 0;
		
		Player player1 = new Player(this);
		Player player2 = new Player(this);
		this.playerList = new ArrayList<Player>();
		this.playerList.add(player1);
		this.playerList.add(player2);
		this.currentPlayer = 0;
		this.market = new Market();
		plotManager = new PlotManager();
	}

	public void nextPhase () {
		int newPhaseState = phase + 1;
		phase = newPhaseState;
		// phase = newPhaseState = 4;

		switch (newPhaseState) {
			// Phase 2: Purchase Roboticon
			case 2:
				TimedMenuScreen roboticonMarket = new TimedMenuScreen(this,false);
				roboticonMarket.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, newPhaseState, 30));
				setScreen(roboticonMarket);
				break;

			// Phase 3: Roboticon Customisation
			case 3:
				AnimationPhaseTimeout timeoutAnimation = new AnimationPhaseTimeout(getPlayer(), this, newPhaseState, 30);
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
				generateResources();
				break;

			//Phase 5: Gambling
			case 5:
				TimedMenuScreen minigame = new TimedMenuScreen(this,true);
				minigame.addAnimation(new AnimationPhaseTimeout(getPlayer(), this, newPhaseState, 30));
				setScreen(minigame);
				break;
			
			// Phase 6: Generate resource for player.
			case 6:
				setScreen(new ResourceMarketScreen(this));
				break;
										
			case 7:
				//RANDOM EVENT
				setScreen(new RoboticonRandomScreen(this));
				break;
			// End phase - CLean up and move to next player.
			case 8:
				phase = newPhaseState = 1;
				this.turn+=1;
				System.out.println(this.turn);
				this.nextPlayer();
				if (this.turn>=endturn){
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

	/**
	 * Phase 4: generate resources.
	 */
	private void generateResources() {
		// Switch back to purchase to game screen.
		setScreen(gameScreen);

		// Generate resources.
		Player p = getPlayer();
		p.generateResources();
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
				return "Gambling";
			
			case 6:
				return "Resource Auction";
			
			case 7:
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
}
