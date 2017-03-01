package io.github.teamfractal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.actors.RoboticonMarketActors;
import io.github.teamfractal.actors.RoboticonMinigameActors;

public class TimedMenuScreen extends AbstractAnimationScreen implements Screen {

	final RoboticonQuest game;
	final Stage stage;
	final Table table;
	private Table actors;
	private boolean mode=true;
	
	public TimedMenuScreen(final RoboticonQuest game,boolean mod) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());
		this.table = new Table();
		this.mode=mod;
		table.setFillParent(true);
		if(mode)
			actors = new RoboticonMinigameActors(game, this);
		else
			actors = new RoboticonMarketActors(game, this);
		table.top().left().add(actors);
		
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
		stage.draw();

		renderAnimation(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		game.getBatch().setProjectionMatrix(stage.getCamera().combined);
		if (mode) {
			((RoboticonMinigameActors) actors).widgetUpdate();
			((RoboticonMinigameActors) actors).resizeScreen(width, height);
		} else {
			((RoboticonMarketActors) actors).widgetUpdate();
			((RoboticonMarketActors) actors).resizeScreen(width, height);
		}
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

	@Override
	protected RoboticonQuest getGame() {
		return game;
	}

	@Override
	public Size getScreenSize() {
		Size s = new Size();
		s.Width = stage.getViewport().getScreenWidth();
		s.Height = stage.getViewport().getScreenHeight();
		return s;
	}
}
