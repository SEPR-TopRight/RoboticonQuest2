package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.RoboticonMarketScreen;

public class RoboticonMarketActors extends Table{
	
		private RoboticonQuest game;
		private RoboticonMarketScreen screen;
		private Integer roboticonAmount = 0;
		private Roboticon currentlySelectedRoboticon;
		private Texture roboticonTexture;
		
		public RoboticonMarketActors(final RoboticonQuest game, RoboticonMarketScreen screen) {
			this.game = game;
			this.screen = screen;
			
			
			// Buy Roboticon Text: Top Left
			final Label lblBuyRoboticon = new Label("Purchase Roboticons:", game.skin);
			
			//Roboticon text to go next to + and - buttons
			final Label lblRoboticons = new Label("Roboticons:", game.skin);
			
			final Label lblRoboticonAmount = new Label(roboticonAmount.toString(), game.skin);
			
			// Button to increase number of roboticons bought
			final TextButton addRoboticonButton = new TextButton("+", game.skin);
			addRoboticonButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					roboticonAmount += 1;
					lblRoboticonAmount.setText(roboticonAmount.toString());
				}
			});
			
			// Button to decrease number of roboticons bought
			final TextButton subRoboticonButton = new TextButton("-", game.skin);
			subRoboticonButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (roboticonAmount > 0) {
						roboticonAmount -= 1;
						lblRoboticonAmount.setText(roboticonAmount.toString());
					}
				}
			});
			
			// Button to buy the selected amount of roboticons from the market
			final TextButton buyRoboticonsButton = new TextButton("Buy Roboticons", game.skin);
			buyRoboticonsButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.getPlayer().purchaseResourceFromMarket(roboticonAmount, game.market, ResourceType.ROBOTICON);
					roboticonAmount = 0;
					lblRoboticonAmount.setText(roboticonAmount.toString());
					widgetUpdate();
					
				}
			});
			
			// Current Roboticon Text: Top Right
			String playerRoboticonText = "Player " + (game.getPlayerInt() + 1) + "'s Roboticons:";
			final Label lblCurrentRoboticon = new Label(playerRoboticonText, game.skin);
			
			// Image widget which displays the roboticon in the player's inventory
			roboticonTexture = new Texture(Gdx.files.internal("roboticon_images/roboticon_uncustomised.jpg"));
			final Image roboticonImage = new Image();
			roboticonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(roboticonTexture)));
			roboticonImage.setSize(roboticonTexture.getWidth(), roboticonTexture.getHeight());
			
			//
			
			
			// Buttons to move backwards and forwards in the player's roboticon inventory
			final TextButton moveLeftRoboticonInventoryBtn= new TextButton("<", game.skin);
			moveLeftRoboticonInventoryBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
				}
			});
			
			final TextButton moveRightRoboticonInventoryBtn = new TextButton(">", game.skin);
			moveRightRoboticonInventoryBtn.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
				}
			});
			
			// Purchase Customisation Text: Bottom Right
			final Label lblPurchaseCustomisation = new Label("Purchase Customisation:", game.skin);
			
			// Drop down menu to select how to customise the selected roboticion
			final SelectBox<String> customisationDropDown = new SelectBox<String>(game.skin);
			String[] customisations = {"Energy", "Ore"};
			customisationDropDown.setItems(customisations);
			
			// Button to buy the selected customisation and customise the selected roboticon
			final TextButton buyCustomisationButton = new TextButton("Buy Roboticon Customisation", game.skin);
			
			final TextButton nextButton = new TextButton("Next ->", game.skin);
			nextButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.nextPhase();
				}
			});
			
			// Top Row Text
			add(lblBuyRoboticon).padTop(40).padLeft(68);
			add();
			add();
			add(lblCurrentRoboticon).padTop(40).padLeft(200);
			
			row();
			
			// Roboticon inc & dec buttons,
			add(lblRoboticons).padTop(40);
			add(subRoboticonButton).padTop(40).padLeft(-90);
			add(lblRoboticonAmount).padTop(40);
			add(addRoboticonButton).padTop(40).padLeft(-260);
			
			add();
			add();
			add();
			
			row();
			
			// Roboticon in inventory selection (moved to different row to preserve position of other buttons)
			add();
			add();
			add();
			add();
			
			add(moveLeftRoboticonInventoryBtn).padTop(40).padLeft(-350).padBottom(200);
			add(roboticonImage).padLeft(-150).padRight(75).padBottom(100).padTop(-50);
			add(moveRightRoboticonInventoryBtn).padTop(40).padLeft(-100).padBottom(200);
			
			row();
			
			row();
			add(nextButton).padTop(40);
			
			
			
		}
		
		public void widgetUpdate() {
			
		}
		
}
