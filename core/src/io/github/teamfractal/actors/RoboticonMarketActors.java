package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.RoboticonMarketScreen;

public class RoboticonMarketActors extends Table{
	
		private RoboticonQuest game;
		private RoboticonMarketScreen screen;
		private Integer roboticonAmount = 0;
		
		public RoboticonMarketActors(RoboticonQuest game, RoboticonMarketScreen screen) {
			this.game = game;
			this.screen = screen;
			
			
			// Buy Roboticon Text: Top Left
			final Label lblBuyRoboticon = new Label("Purchase Roboticions:", game.skin);
			
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
			
			final TextButton buyRoboticonsButton = new TextButton("Buy Roboticons", game.skin);
			buyRoboticonsButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					
				}
			});
			
			
			
			
			
			// Current Roboticon Text: Top Right
			String playerRoboticonText = "Player " + game.getPlayerInt() + "'s Roboticons:";
			final Label lblCurrentRoboticon = new Label(playerRoboticonText, game.skin);
			
			
			
			
		}
		
		public void widgetUpdate() {
			
		}
		
}
