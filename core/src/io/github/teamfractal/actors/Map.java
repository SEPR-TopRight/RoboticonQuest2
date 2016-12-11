package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

import io.github.teamfractal.RoboticonQuest;

public class Map extends Table {
	private RoboticonQuest game;
	public int height = 20;
	public int width = 20;
	
	
	public Map(final RoboticonQuest game) {
		
		this.game = game;

		center();
		
		final TextButton buttonTest = new TextButton("A button", game.skin);
		buttonTest.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buttonTest.setText("Button clicked.");
			}
		});
				
		for (int i = 0; i <= this.height;  i ++){
			add(new TextButton ("hello", game.skin));
			
		}
	}
	}
			
	
			
		
