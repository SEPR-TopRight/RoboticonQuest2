package io.github.teamfractal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.ResourceMarketActors;

/**
 * The screen that players can use to buy / sell resources from / to the market and each other
 *
 */
public class ResourceMarketScreen implements Screen {
	final RoboticonQuest game;
	final Stage stage;
	final Table table;
	private final ResourceMarketActors actors;
	
	/**
	 * Constructor
	 * @param game The RoboticonQuest object that contains the players and the market
	 */
	public ResourceMarketScreen(final RoboticonQuest game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());
		this.table = new Table();
		table.setFillParent(true);

		actors = new ResourceMarketActors(game, this); // generates actors for the screen
		table.center().add(actors); // positions actors

		stage.addActor(actors.getBackgroundImage());
		stage.addActor(table);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		actors.getBackgroundImage().toBack();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		actors.resizeScreen(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	public Stage getStage(){
		return this.stage;
	}
}