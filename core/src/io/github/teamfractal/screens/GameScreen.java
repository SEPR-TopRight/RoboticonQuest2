package io.github.teamfractal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.teamfractal.RoboticonQuest;

public class GameScreen implements Screen {
	private final RoboticonQuest game;
	private final OrthographicCamera camera;
	private final Stage stage;
	private IsometricStaggeredTiledMapRenderer renderer;
	private TiledMap tmx;

	private float oldX;
	private float oldY;

	private float oldW;
	private float oldH;

	public GameScreen(final RoboticonQuest game) {
		oldW = Gdx.graphics.getWidth();
		oldH = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, oldW, oldH);
		camera.update();


		this.game = game;

		// TODO: Add some HUD gui stuff (buttons, mini-map etc...)
		// TODO: I forgot what was needed to do
		this.stage = new Stage(new ScreenViewport());

		// Drag the map within the screen.
		stage.addListener(new DragListener() {
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				oldX = x;
				oldY = y;
			}

			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {float deltaX = x - oldX;
				float deltaY = y - oldY;

				// TODO: Restrict the min/max camera offset from origin
				// The camera translates in a different direction...
				camera.translate(-deltaX, -deltaY);
				if (camera.position.x < 20) camera.position.x = 20;
				if (camera.position.y < 20) camera.position.y = 20;
				if (camera.position.x > 10000) camera.position.x = 10000;
				if (camera.position.y > 10000) camera.position.y = 10000;

				// Record cords
				oldX = x;
				oldY = y;
			}
		});

		camera.position.x = 20;
		camera.position.y = 20;

		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// The Y from screen starts from bottom left.
				Vector3 cord = new Vector3(x, oldH - y, 0);
				camera.unproject(cord);
				cord.y -= 20;  // Padding from tile
				cord.x += 50;
				//  System.out.println(cord.x + ", " + cord.y);

				// Convert to vector
				// http://2dengine.com/doc/gs_isometric.html
				TiledMapTileLayer layer = (TiledMapTileLayer)tmx.getLayers().get(0);
				float tile_height = layer.getTileHeight();
				float tile_width = layer.getTileWidth();

				float ty = cord.y - cord.x/2 - tile_height;
				float tx = cord.x + ty;
				ty = MathUtils.ceil(-ty/(tile_width/2));
				tx = MathUtils.ceil(tx/(tile_width/2)) + 1;
				int cordX = MathUtils.floor((tx + ty)/2);
				int cordY = -(int)(ty - tx);


				// The magic numbers based on observation of number patterns
				cordX -= 1;
				if (cordY % 2 == 0) {
					cordX --;
				}
				// System.out.println(cordX + ", " + cordY);
				TiledMapTileLayer.Cell c = layer.getCell(cordX, cordY);
				if (c != null) {
					// Todo: Call method with the TileCell and the position
					layer.getCell(cordX, cordY).setTile(tmx.getTileSets().getTile(0));
				}
			}
		});

		newGame();
	}

	public void newGame() {
		// Setup the game board.
		if (tmx != null) tmx.dispose();
		if (renderer != null) renderer.dispose();

		tmx = new TmxMapLoader().load("tiles/city.tmx");
		renderer = new IsometricStaggeredTiledMapRenderer(tmx);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		renderer.setView(camera);
		renderer.render();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		if (width != oldW && height != oldH) {
			stage.getViewport().update(width, height, true);
			camera.setToOrtho(false, width, height);

			oldW = width;
			oldH = height;
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
		tmx.dispose();
		renderer.dispose();
		stage.dispose();
	}


}