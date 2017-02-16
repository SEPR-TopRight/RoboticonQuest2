package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.RoboticonMinigameScreen;
import io.github.teamfractal.screens.RoboticonRandomScreen;

import java.util.Random;
//@author jormandr
public class RoboticonRandomActors extends Table {
	private RoboticonQuest game;
	private RoboticonRandomScreen screen;
	private Texture texture;
	private Texture texturepl;
	private Texture texturecom;
	private Label topText;
	private Label playerStats;
	private static final Texture event[]=new Texture[9];
	private Image bg = new Image();
	Random random = new Random();
	static {
		event[0] = new Texture(Gdx.files.internal("events/landscape.png"));
		event[1] = new Texture(Gdx.files.internal("cards/bankrupt.png"));
		event[2] = new Texture(Gdx.files.internal("cards/unknown.png"));
		event[3] = new Texture(Gdx.files.internal("cards/win.png"));
		event[4] = new Texture(Gdx.files.internal("cards/lose.png"));
		event[5]=new Texture(Gdx.files.internal("cards/tie.png"));
		event[6] = new Texture(Gdx.files.internal("cards/rock.png"));
		event[7] = new Texture(Gdx.files.internal("cards/paper.png"));
		event[8] = new Texture(Gdx.files.internal("cards/scissors.png"));
	}
	public RoboticonRandomActors(final RoboticonQuest game, RoboticonRandomScreen roboticonRandomScreen) {
		this.game = game;
		this.screen = roboticonRandomScreen;
		texture=event[0];
		new Label("", game.skin);
		new Label("", game.skin);

		widgetUpdate();
		
		final Label lbltxt = new Label("Event TExt", game.skin);
		

		final TextButton nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.nextPhase();
			}
		});

		// Top Row Text
		add();
		add();
		add();
		add();

		row();


		row();
		
		add();
		row();
		
		add();
		add();
		add();
		add();

		add();
		add();
		
		row();
		// image of the card
		add();
		add();
		add(bg).padLeft(0).padRight(150).padBottom(60).padTop(-150);
		add(lbltxt);

		row();



		row();

		add();
		add();
		add();
		add();

		add();
		add(nextButton).padTop(40);

	}



	public void widgetUpdate() {
		
		// Draws turn and phase info on screen
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(screen.getStage().getWidth() / 2 - 40, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(topText);
		bg.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
		// Draws player stats on screen
		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, screen.getStage().getViewport().getWorldHeight() - 20);
		screen.getStage().addActor(playerStats);
	}
	}