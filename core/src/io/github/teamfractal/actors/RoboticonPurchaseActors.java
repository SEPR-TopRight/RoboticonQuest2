package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.screens.RoboticonMarketScreen;
import io.github.teamfractal.screens.RoboticonPurchaseScreen;

public class RoboticonPurchaseActors extends Table {
	private RoboticonQuest game;
	private RoboticonPurchaseScreen screen;

	public RoboticonPurchaseActors(final RoboticonQuest game, RoboticonPurchaseScreen screen) {
		this.game = game;
		this.screen = screen;

		build();
	}

	private void build() {
		//Set up and position Roboticon Label
		final Label roboticonLabel = new Label("Buy Roboticon", game.skin);
		roboticonLabel.setColor((float) 0.5, 0, 0, 1);

		// image
		Image roboticonPhoto = new Image();
		roboticonPhoto.setDrawable(new TextureRegionDrawable(
				new TextureRegion(new Texture(Gdx.files.internal("robot.png")))));
		roboticonPhoto.setSize((float)(569 * 0.2),(float)(690*0.2));
		Table container = new Table();
		container.addActor(roboticonPhoto);


		// oreLebel
		final Label oreLebel = new Label("Ore: ", game.skin);
		roboticonLabel.setColor((float) 0.5, 0, 0, 1);


		// foodLebel
		final Label foodLebel = new Label("Food: ", game.skin);
		roboticonLabel.setColor((float) 0.5, 0, 0, 1);

		// energyLebel
		final Label energyLebel = new Label("Energy: ", game.skin);
		roboticonLabel.setColor((float) 0.5, 0, 0, 1);







		// add to the screen
		add(roboticonLabel).padTop(20).padBottom(100);
		row();
		add(container).padTop(80).padBottom(50).padLeft(100).padRight(200);
		row();
		add(oreLebel).padTop(20).padBottom(30);
		row();
		add(foodLebel).padTop(20).padBottom(30);
		row();
		add(energyLebel).padTop(20).padBottom(30);

	}





}
