package io.github.teamfractal.actors;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import io.github.teamfractal.entity.enums.ResourceType;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.ResourceMarketScreen;

public class ResourceMarketActors extends Table {
	private RoboticonQuest game;
	private Integer buyOreAmount;
	private Integer sellOreAmount;
	private Integer buyEnergyAmount;
	private Label topText;
	private Label playerStats;
	private ResourceMarketScreen screen;
	private TextButton nextButton;
	private Label marketStats;
	private Integer sellEnergyAmount;
	
	public ResourceMarketActors(final RoboticonQuest game, ResourceMarketScreen screen) {
		this.game = game;
		this.screen = screen;
		
		//Set up and position Buy Label
		final Label buyLabel = new Label("Buy", game.skin);
		buyLabel.setColor((float) 0.5, 0, 0, 1);
		
		//Set up and position ore Label
		final Label buyOreLabel = new Label("Ore: " + game.market.getSellPrice(ResourceType.ORE) + " Gold", 
				game.skin);
		//initialise ore amount
		buyOreAmount = 0;
		
		//Set up the display to show how much ore is being bought
		final Label buyOreAmountText = new Label(buyOreAmount.toString(), game.skin);
		buyOreAmountText.setWidth(10);
		
		//Set button to increment ore to be bought
		final TextButton addBuyOreButton = new TextButton("+", game.skin);
		addBuyOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyOreAmount += 1;
				buyOreAmountText.setText(buyOreAmount.toString());
		}
		});
		
		//Set up button to decrement ore to be bought
		final TextButton subBuyOreButton = new TextButton("-", game.skin);
		subBuyOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (buyOreAmount > 0){
					buyOreAmount -= 1;
					buyOreAmountText.setText(buyOreAmount.toString());
				}
		}
		});
		//Set up button to confirm ore purchase
		final TextButton buyOreButton = new TextButton("Buy ore", game.skin);
		buyOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().purchaseResourceFromMarket(buyOreAmount, game.market, ResourceType.ORE);
				buyOreAmount = 0;
				buyOreAmountText.setText(buyOreAmount.toString());
				buyOreLabel.setText("Ore: " + game.market.getSellPrice(ResourceType.ORE) + " Gold");
				widgetUpdate();
				}
		});
		
		
		
		
		//Set up and position energy Label
		final Label buyEnergyLabel = new Label("Energy: " + game.market.getSellPrice(ResourceType.ENERGY) 
			+ " Gold", game.skin);
		
		buyEnergyAmount = 0;
		
		//Set up the display to show how much energy is being bought
		final Label buyEnergyAmountText = new Label(buyEnergyAmount.toString(), game.skin);
		
		//set up button to increment Energy amount
		final TextButton addBuyEnergyButton = new TextButton("+", game.skin);
		addBuyEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyEnergyAmount += 1;
				buyEnergyAmountText.setText(buyEnergyAmount.toString());
		}
		});
		
		//set up button to decrement energy amount
		final TextButton subBuyEnergyButton = new TextButton("-", game.skin);
		subBuyEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (buyEnergyAmount > 0){
					buyEnergyAmount -= 1;
					buyEnergyAmountText.setText(buyEnergyAmount.toString());
				}
		}
		});
		
		//set up button to confirm energy purchase
		final TextButton buyEnergyButton = new TextButton("Buy energy", game.skin);
		buyEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().purchaseResourceFromMarket(buyEnergyAmount, game.market, ResourceType.ENERGY);
				buyEnergyAmount = 0;
				buyEnergyAmountText.setText(buyEnergyAmount.toString());
				buyEnergyLabel.setText("Energy: " + game.market.getSellPrice(ResourceType.ENERGY) 
			+ " Gold");
				widgetUpdate();
				}
		});
		
		//set up sell label
		final Label sellLabel = new Label("Sell", game.skin);
		sellLabel.setColor(0, (float) 0.5, 0, 1);
		
		//set up ore label
		final Label sellOreLabel = new Label("Ore: " + game.market.getBuyPrice(ResourceType.ORE) + " Gold", 
				game.skin);
		
		//initalise ore amount
		sellOreAmount = 0;
		
		//set up display of ore amount to be sold
		final Label sellOreAmountText = new Label(sellOreAmount.toString(), game.skin);
		sellOreAmountText.setWidth(10);
		
		//set up button to increment ore amount 
		final TextButton addSellOreButton = new TextButton("+", game.skin);
		addSellOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sellOreAmount += 1;
				sellOreAmountText.setText(sellOreAmount.toString());
		}
		});
		
		//set up button to decrement ore amount
		final TextButton subSellOreButton = new TextButton("-", game.skin);
		subSellOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (sellOreAmount > 0){
					sellOreAmount -= 1;
					sellOreAmountText.setText(sellOreAmount.toString());
				}
		}
		});
		//set up button to confirm ore sale
		final TextButton sellOreButton = new TextButton("Sell ore", game.skin);
		sellOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().sellResourceToMarket(sellOreAmount, game.market, ResourceType.ORE);
				sellOreAmount = 0;
				sellOreAmountText.setText(sellOreAmount.toString());
				sellOreLabel.setText("Ore: " + game.market.getBuyPrice(ResourceType.ORE) + " Gold");
				widgetUpdate();
				}
		});
		
		//set up label for energy
		final Label sellEnergyLabel = new Label("Energy: " + game.market.getBuyPrice(ResourceType.ENERGY) + " Gold", 
				game.skin);
		
		//initialise energy amount
		sellEnergyAmount = 0;
		
		//set up display for amount of energy being sold
		final Label sellEnergyAmountText = new Label(sellEnergyAmount.toString(), game.skin);
		sellEnergyAmountText.setWidth(10);
		
		
		//set up button to increment energy being sold amount
		final TextButton addSellEnergyButton = new TextButton("+", game.skin);
		addSellEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sellEnergyAmount += 1;
				sellEnergyAmountText.setText(sellEnergyAmount.toString());
		}
		});
		
		//set up button to decrement energy being sold amount
		final TextButton subSellEnergyButton = new TextButton("-", game.skin);
		subSellEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (sellEnergyAmount > 0){
					sellEnergyAmount -= 1;
					sellEnergyAmountText.setText(sellEnergyAmount.toString());
				}
		}
		});
		
		//set up button to confirm energy sale
		final TextButton sellEnergyButton = new TextButton("Sell energy", game.skin);
		sellEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().sellResourceToMarket(sellEnergyAmount, game.market, ResourceType.ENERGY);
				sellEnergyAmount = 0;
				sellEnergyAmountText.setText(sellEnergyAmount.toString());
				sellEnergyLabel.setText("Ore: " + game.market.getBuyPrice(ResourceType.ENERGY) + " Gold");
				widgetUpdate();
				}
		});
		
		//place all widgets in table
		add(buyLabel).padTop(40).padLeft(90);
		add();
		add();
		add(sellLabel).padTop(40).padLeft(250);
		row();
		add(buyOreLabel).padTop(20);
		add();
		add();
		add(sellOreLabel).padTop(20).padLeft(250);
		row();
		add(subBuyOreButton).padLeft(-10).padTop(10);
		add(buyOreAmountText).padLeft(-10);
		add(addBuyOreButton).padLeft(40);
		
		add(subSellOreButton).padLeft(200).padTop(10);
		add(sellOreAmountText).padLeft(-10);
		add(addSellOreButton).padLeft(40);
		
		row();
		
		add(buyOreButton).padTop(10).padLeft(30);
		add();
		add();
		add(sellOreButton).padTop(10).padLeft(220);
		row();
		
		
		add(buyEnergyLabel).padTop(30).padLeft(25);
		add();
		add();
		add(sellEnergyLabel).padTop(20).padLeft(250);
		row();
		add(subBuyEnergyButton).padLeft(-10).padTop(10);
		add(buyEnergyAmountText);
		add(addBuyEnergyButton).padLeft(40);
		
		add(subSellEnergyButton).padLeft(200).padTop(10);
		add(sellEnergyAmountText).padLeft(-10);
		add(addSellEnergyButton).padLeft(40);
		
		row();
		add(buyEnergyButton).padTop(10).padLeft(50);
		add();
		add();
		add(sellEnergyButton).padTop(10).padLeft(220);
		
		
		
		
		widgetUpdate();
	}
	
/**
 * Updates all widgets on screen
 */
public void widgetUpdate(){
	//diplay player stats
	if (this.topText != null) this.topText.remove();
	String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
	this.topText = new Label(phaseText, game.skin);
	topText.setWidth(120);
	topText.setPosition(screen.getStage().getWidth()/2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
	screen.getStage().addActor(topText);
	
	
	if (this.playerStats != null) this.playerStats.remove();
	String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " +  game.getPlayer().getEnergy() + " Food: "
			+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
	this.playerStats = new Label(statText, game.skin);
	playerStats.setWidth(250);
	playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
	screen.getStage().addActor(playerStats);
	
	//display nextphase button
	nextButton = new TextButton("nextPhase", game.skin);
	nextButton.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.nextPhase();
		}
	});
	nextButton.setPosition(this.screen.getStage().getWidth() - 80, 0);
	screen.getStage().addActor(nextButton);
	
	//display market statistics
	if (this.marketStats != null) this.marketStats.remove();
	String marketStatText = "Ore: " + game.market.getResource(ResourceType.ORE) 
			+ " Energy: " +  game.market.getResource(ResourceType.ENERGY) + " Food: "
			+ game.market.getResource(ResourceType.FOOD);
	this.marketStats = new Label(marketStatText, game.skin);
	marketStats.setWidth(250);
	marketStats.setPosition(screen.getStage().getWidth() - 200, screen.getStage().getViewport().getWorldHeight() - 20);
	screen.getStage().addActor(marketStats);
	
	}
}
