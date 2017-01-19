package io.github.teamfractal.actors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.GameScreen;

public class GameScreenActors {
	private final Stage stage;
	private RoboticonQuest game;
	private GameScreen screen;
	private Label topText;
	private Label playerStats;
	private TextButton buyLandPlotBtn;
	private TextButton installRoboticonBtn;
	private TextButton installRoboticonBtnCancel;
	private Label installRoboticonLabel;
	private SelectBox<String> installRoboticonSelect;
	private Label plotStats;
	private TextButton nextButton;
	private boolean dropDownActive;
	private boolean listUpdated;
	private boolean nextClickNull;


	public GameScreenActors(final RoboticonQuest game, GameScreen screen) {
		this.game = game;
		this.screen = screen;
		this.stage = screen.getStage();
	}
	public void initialiseButtons() {
		nextButton = new TextButton("Next ->", game.skin);
		buyLandPlotBtn = new TextButton("Buy LandPlot", game.skin);
		installRoboticonLabel = new Label("Install Roboticon", game.skin);
		
		installRoboticonSelect = new SelectBox<String>(game.skin);
		installRoboticonSelect.setItems(game.getPlayer().getRoboticonAmounts());
		
		installRoboticonBtn = new TextButton("confirm", game.skin);
		installRoboticonBtnCancel = new TextButton("cancel", game.skin);
		
		plotStats = new Label("", game.skin);
		nextClickNull = false;

		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyLandPlotBtn.setVisible(false);
				plotStats.setVisible(false);
				installRoboticonSelect.setVisible(false);
				installRoboticonLabel.setVisible(false);
				installRoboticonBtn.setVisible(false);
				installRoboticonBtnCancel.setVisible(false);
				game.nextPhase();
				dropDownActive = true;
				installRoboticonSelect.setItems(game.getPlayer().getRoboticonList());
				textUpdate();
			}
		});

		nextButton.setPosition(this.stage.getWidth() - 80, 0);


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
		installRoboticonSelect.setSelected(null);
		listUpdated = false;
		
		installRoboticonBtn.addListener(new ChangeListener(){
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (! listUpdated){ //prevents updating selection list from updating change listener
					LandPlot selectedPlot = screen.getSelectedPlot();
					if(selectedPlot.getOwner() == game.getPlayer() && ! selectedPlot.hasRoboticon()){
						Roboticon roboticon = null;
						int index = -1;
						ResourceType type = ResourceType.Unknown;
						int selection = installRoboticonSelect.getSelectedIndex();
						
						Array<Roboticon> roboticons = game.getPlayer().getRoboticons();
						switch(selection){
						case 0:
							type = ResourceType.ORE;
							break;
						case 1: 
							type = ResourceType.ENERGY;
							break;
						default:
							type = ResourceType.Unknown;
							break;
							}
						for (int i = 0; i < roboticons.size; i++){
							if (type ==  roboticons.get(i).getCustomisation()){
								roboticon = roboticons.get(i);
								index = i;
								break;
							}
						}
						if (roboticon != null){
							selectedPlot.installRoboticon(roboticon);
							TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
							playerTile.setTile(screen.getResourcePlayerTile(game.getPlayer(), type));
							selectedPlot.setHasRoboticon(true);
							textUpdate();
						}
						if (index >= 0 && index < roboticons.size) roboticons.removeIndex(index);
						listUpdated = true; 
						installRoboticonSelect.setItems(game.getPlayer().getRoboticonAmounts());
						dropDownActive = true;
						installRoboticonSelect.setVisible(false);
						installRoboticonLabel.setVisible(false);
						installRoboticonBtn.setVisible(false);
						installRoboticonBtnCancel.setVisible(false);
						nextClickNull = true;
							
					}
					else listUpdated = false;
				}
			}
		});
		
		installRoboticonBtnCancel.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				dropDownActive = true;
				installRoboticonSelect.setVisible(false);
				installRoboticonLabel.setVisible(false);
				installRoboticonBtn.setVisible(false);
				installRoboticonBtnCancel.setVisible(false);
				nextClickNull = true;
				
			}
			
		});

		
		installRoboticonSelect.setVisible(false);
		installRoboticonLabel.setVisible(false);
		installRoboticonBtn.setVisible(false);
		installRoboticonBtnCancel.setVisible(false);
		stage.addActor(nextButton);
		stage.addActor(buyLandPlotBtn);
		stage.addActor(installRoboticonSelect);
		stage.addActor(installRoboticonLabel);
		stage.addActor(installRoboticonBtn);
		stage.addActor(installRoboticonBtnCancel);
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
				if (game.canPurchaseLandThisTurn()
						&& !plot.hasOwner()
						&& player.haveEnoughMoney(plot)) {
					buyLandPlotBtn.setDisabled(false);
				} else {
					buyLandPlotBtn.setDisabled(true);
				}

				if (plot.hasOwner()) {
					showPlotStats(plot, x, y + 25);
				}

				buyLandPlotBtn.setVisible(true);
				break;
			// Phase 3:
			// Install Roboticon 
			case 3:
				if (!nextClickNull){
				
				if (dropDownActive){
					installRoboticonLabel.setPosition(x-70, y);
					installRoboticonLabel.setVisible(true);
					installRoboticonSelect.setPosition(x + 40,y);
					installRoboticonSelect.setVisible(true);
					installRoboticonBtn.setPosition(x+ 70, y);
					installRoboticonBtn.setVisible(true);
					installRoboticonBtnCancel.setPosition(x + 120, y);
					installRoboticonBtnCancel.setVisible(true);
					dropDownActive = false;
				}
				if (plot.hasRoboticon()){
					dropDownActive = true;
				}
				else dropDownActive = false;
					
					break;
				}
				else nextClickNull = false;
		}
		
				
		}


	public TextButton getBuyLandPlotBtn() {
		return buyLandPlotBtn;
	}
	
	public SelectBox<String> getInstallRoboticonSelect(){
		return installRoboticonSelect;
	}
	
	public Label getInstallRoboticonLabel(){
		return installRoboticonLabel;
	}
	
	public boolean getDropDownActive(){
		return dropDownActive;
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
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase() + " - " + game.getPhaseString();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getViewport().getWorldWidth()/2, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		
		
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre()
				+ " Energy: " +  game.getPlayer().getEnergy()
				+ " Food: " + game.getPlayer().getFood()
				+ " Money: " + game.getPlayer().getMoney();

		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
	}

	public Label getPlotStats(){
		return plotStats;
	}
	public void showPlotStats (LandPlot plot,  float x, float y) {
		String plotStatText = "Ore: " + plot.getResource(ResourceType.ORE)
				+ "  Energy: " + plot.getResource(ResourceType.ENERGY);

		plotStats.setText(plotStatText);
		plotStats.setPosition(x, y);
		plotStats.setVisible(true);
	}
}
