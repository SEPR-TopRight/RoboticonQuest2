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
	private Integer oreAmount;
	private Integer energyAmount;
	private Label topText;
	private Label playerStats;
	private ResourceMarketScreen screen;
	private TextButton nextButton;
	private Label marketStats;
	
	public ResourceMarketActors(final RoboticonQuest game, ResourceMarketScreen screen) {
		this.game = game;
		this.screen = screen;
		
		
		final Label buyLabel = new Label("Buy", game.skin);
		buyLabel.setColor((float) 0.5, 0, 0, 1);
		
		final Label buyOreLabel = new Label("Ore: " + game.market.getBuyPrice(ResourceType.ORE) + " Gold", 
				game.skin);
		oreAmount = 0;
		final Label oreAmountText = new Label(oreAmount.toString(), game.skin);
		oreAmountText.setWidth(10);
		
		
		final TextButton addOreButton = new TextButton("+", game.skin);
		addOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				oreAmount += 1;
				oreAmountText.setText(oreAmount.toString());
		}
		});
		
		
		final TextButton subOreButton = new TextButton("-", game.skin);
		subOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (oreAmount > 0){
					oreAmount -= 1;
					oreAmountText.setText(oreAmount.toString());
				}
		}
		});
		final TextButton buyOreButton = new TextButton("Buy ore", game.skin);
		buyOreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().purchaseResourceFromMarket(oreAmount, game.market, ResourceType.ORE);
				oreAmount = 0;
				oreAmountText.setText(oreAmount.toString());
				buyOreLabel.setText("Ore: " + game.market.getBuyPrice(ResourceType.ORE) + " Gold");
				widgetUpdate();
				}
		});
		
		
		
		
		
		final Label buyEnergyLabel = new Label("Energy: " + game.market.getBuyPrice(ResourceType.ENERGY) 
			+ " Gold", game.skin);
		energyAmount = 0;
		final Label energyAmountText = new Label(energyAmount.toString(), game.skin);
		final TextButton addEnergyButton = new TextButton("+", game.skin);
		addEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				energyAmount += 1;
				energyAmountText.setText(energyAmount.toString());
		}
		});
		
		
		final TextButton subEnergyButton = new TextButton("-", game.skin);
		subEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (energyAmount > 0){
					energyAmount -= 1;
					energyAmountText.setText(energyAmount.toString());
				}
		}
		});
		
		final TextButton buyEnergyButton = new TextButton("Buy energy", game.skin);
		buyEnergyButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.getPlayer().purchaseResourceFromMarket(energyAmount, game.market, ResourceType.ENERGY);
				energyAmount = 0;
				energyAmountText.setText(energyAmount.toString());
				buyEnergyLabel.setText("Energy: " + game.market.getBuyPrice(ResourceType.ENERGY) 
			+ " Gold");
				widgetUpdate();
				}
		});
		
		
		add(buyLabel).padTop(40).padLeft(90);
		row();
		add(buyOreLabel).padTop(20);
		row();
		add(subOreButton).padLeft(-10).padTop(10);
		add(oreAmountText).padLeft(-10);
		add(addOreButton).padLeft(40);
		row();
		add(buyOreButton).padTop(10).padLeft(30);
		
		row();
		
		
		add(buyEnergyLabel).padTop(30).padLeft(25);
		row();
		add(subEnergyButton).padLeft(-10).padTop(10);
		add(energyAmountText);
		add(addEnergyButton).padLeft(40);
		row();
		add(buyEnergyButton).padTop(10).padLeft(50);
		
		
		widgetUpdate();
	}
	

public void widgetUpdate(){
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
	
	nextButton = new TextButton("nextPhase", game.skin);
	nextButton.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.nextPhase();
		}
	});
	
	
	if (this.marketStats != null) this.marketStats.remove();
	String marketStatText = "Ore: " + game.market.getResource(ResourceType.ORE) 
			+ " Energy: " +  game.market.getResource(ResourceType.ENERGY) + " Food: "
			+ game.market.getResource(ResourceType.FOOD);
	this.marketStats = new Label(marketStatText, game.skin);
	marketStats.setWidth(250);
	marketStats.setPosition(screen.getStage().getWidth() - 200, screen.getStage().getViewport().getWorldHeight() - 20);
	screen.getStage().addActor(marketStats);
	
	nextButton = new TextButton("nextPhase", game.skin);
	nextButton.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			game.nextPhase();
		}
	});
	
	nextButton.setPosition(this.screen.getStage().getWidth() - 80, 0);
	screen.getStage().addActor(nextButton);
	}
}
