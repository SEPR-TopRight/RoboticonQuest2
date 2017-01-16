package io.github.teamfractal.actors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.PlotMap;
import io.github.teamfractal.screens.GameScreen;

public class GameScreenActors extends Table {
	private RoboticonQuest game;
	private GameScreen screen;
	private Label topText;
	private Label playerStats;
	private TextButton currentButton;
	private TextButton nextButton;
	
	public GameScreenActors(final RoboticonQuest game,GameScreen screen) {
		this.game = game;
		this.screen = screen;
		
	}
		
		
		
	public void initialiseButtons(){
		
		nextButton = new TextButton("nextPhase", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentButton != null) {
					currentButton.remove();
					currentButton = null;
				}
				game.nextPhase();
				screen.setButtonNotPressed(false);
				textUpdate();
			}
		});
		
		nextButton.setPosition(this.screen.getStage().getWidth() - 80, 0);
		screen.getStage().addActor(nextButton);

}
	
public void nextUpdate(){
	if (nextButton != null) nextButton.remove();
	nextButton.setPosition(this.screen.getStage().getWidth() - 80, 0);
	screen.getStage().addActor(nextButton);
}




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

public void clicked(final TiledMapTileLayer.Cell cell, final TiledMapTileLayer.Cell cell2,
		float mouseX, float mouseY, final int cordX, final int cordY){
	if (currentButton != null) {
		currentButton.remove();
	}
	if (game.getPhase() == 1 && screen.isButtonNotPressed() && 
			! game.plotMap.getPlot(cordX, cordY).isOwned()){
	
		currentButton = new TextButton("buy landplot", game.skin);
		currentButton.setPosition(mouseX, mouseY);
		currentButton.addListener(new ChangeListener() {
		

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (currentButton != null) currentButton.remove();
				System.out.println(cordX + " " + cordY);
				if (! game.plotMap.getPlot(cordX, cordY).isOwned()){
					game.getPlayer().purchaseLandPlot(cordX, cordY);
					cell2.setTile(screen.getTmx().getTileSets().getTile(67 + game.getPlayerInt()));
					
				}
				
				textUpdate();
				screen.setButtonNotPressed(false);
				currentButton = null;
		}
	});
	screen.getStage().addActor(currentButton);
	
}
if (! screen.isButtonNotPressed()){
	screen.setButtonNotPressed(true);
}
}



public TextButton getCurrentButton() {
	return currentButton;
}
}
