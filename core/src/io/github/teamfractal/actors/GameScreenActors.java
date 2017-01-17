package io.github.teamfractal.actors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.PlotMap;
import io.github.teamfractal.screens.GameScreen;

public class GameScreenActors {
	private final Stage stage;
	private RoboticonQuest game;
	private GameScreen screen;
	private Label topText;
	private Label playerStats;
	private TextButton buyLandPlotBtn;
	private Label plotStats;
	private TextButton nextButton;

	public GameScreenActors(final RoboticonQuest game, GameScreen screen) {
		this.game = game;
		this.screen = screen;
		this.stage = screen.getStage();
	}
	public void initialiseButtons() {
		nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyLandPlotBtn.setVisible(false);
				if (plotStats != null){
					plotStats.remove();
					plotStats = null;
				}
				
				game.nextPhase();
				textUpdate();
			}
		});

		nextButton.setPosition(this.stage.getWidth() - 80, 0);
		stage.addActor(nextButton);


		buyLandPlotBtn = new TextButton("Buy LandPlot", game.skin);
		buyLandPlotBtn.setVisible(false);
		buyLandPlotBtn.pad(2, 10, 2, 10);
		buyLandPlotBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LandPlot selectedPlot = screen.getSelectedPlot();

				// TODO: purchase land
				if (selectedPlot.hasOwner()) {
					return ;
				}

				Player player = game.getPlayer();
				if (player.purchaseLandPlot(selectedPlot)) {
					TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
					playerTile.setTile(screen.getPlayerTile(player));
					textUpdate();
				}
			}
		});
		stage.addActor(buyLandPlotBtn);
	}

	/**
	 * Tile click callback event.
	 * @param plot          The landplot tileClicked.
	 * @param x             Current mouse x position
	 * @param y             Current mouse y position
	 */
	public void tileClicked(LandPlot plot, float x, float y) {
		Player player = game.getPlayer();

		// TODO: Need proper event callback
		switch (game.getPhase()) {
			// Phase 1:
			// Purchase LandPlot.
			case 1:
				buyLandPlotBtn.setPosition(x, y);
				if (plot.hasOwner() || !player.haveEnoughMoneyForLandplot()) {
					buyLandPlotBtn.setDisabled(true);
				} else {
					buyLandPlotBtn.setDisabled(false);
				}
				buyLandPlotBtn.setVisible(true);
				break;
		}
	}

	public void nextUpdate() {
		if (nextButton != null) nextButton.remove();
		nextButton.setPosition(this.stage.getWidth() - 80, 0);
		stage.addActor(nextButton);
	}

	public void textUpdate() {
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase() + " - " + game.getPhaseString();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(topText);

		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " + game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(playerStats);
	}

	public TextButton getBuyLandPlotBtn() {
		return buyLandPlotBtn;
	}
	
	
	
	/**
	 * Updates next phase button	
	 */
	public void nextUpdate(){
		if (nextButton != null) nextButton.remove();
		nextButton.setPosition(this.screen.getStage().getWidth() - 80, 0);
		screen.getStage().addActor(nextButton);
	}
	
	
	
	/**
	 * Updates Textfield widgets
	 */
	public void textUpdate(){
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getViewport().getWorldWidth()/2, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		
		
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " +  game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
	}
	
	/**
	 * Handles a mouse click on screen
	 * @param cell Cell on the tiled map bottom layer
	 * @param cell2 Cell on the second tilemap layer
	 * @param mouseX Position of mouse click on x axis
	 * @param mouseY Position of mouse click on y axis
	 * @param cordX X coordinate of cell in tiled map
	 * @param cordY Y coordinate of cell in tiled map
	 */
	public void clicked_james(final TiledMapTileLayer.Cell cell, final TiledMapTileLayer.Cell cell2,
			float mouseX, float mouseY, final int cordX, final int cordY){
		if (currentButton != null) currentButton.remove();
		if (plotStats != null) plotStats.remove();
		if (game.getPhase() == 1 && screen.isButtonNotPressed() && 
				! game.plotMap.getPlot(cordX, cordY).isOwned()&& ! game.getPlayer().getPlotBought()){
			
			int[] productionAmounts =  game.plotMap.getPlot(cordX, cordY).getProductionAmounts();
			String plotStatText = "Ore: " + productionAmounts[0] + " Energy: " + productionAmounts[1]  ;
			plotStats = new Label(plotStatText, game.skin);
			plotStats.setPosition(mouseX, mouseY + 25);
			currentButton = new TextButton("buy landplot", game.skin);
			currentButton.setPosition(mouseX, mouseY);
			currentButton.addListener(new ChangeListener() {
			
	
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (currentButton != null) currentButton.remove();
					if (plotStats != null) plotStats.remove();
					if (! game.plotMap.getPlot(cordX, cordY).isOwned() ){
						game.getPlayer().purchaseLandPlot(cordX, cordY);
						cell2.setTile(screen.getTmx().getTileSets().getTile(67 + game.getPlayerInt()));
						
					}
					
				textUpdate();
				screen.setButtonNotPressed(false);
				currentButton = null;
			}
		});
			
		
		screen.getStage().addActor(currentButton);
		screen.getStage().addActor(plotStats);
		}
		if (! screen.isButtonNotPressed()){
			screen.setButtonNotPressed(true);
		}
	}
	
	public Label getPlotStats(){
		return plotStats;
	}
}
