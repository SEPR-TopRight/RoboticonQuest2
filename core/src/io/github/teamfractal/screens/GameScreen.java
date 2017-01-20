package io.github.teamfractal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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
import io.github.teamfractal.actors.GameScreenActors;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;

public class GameScreen extends AbstractAnimationScreen implements Screen  {
	private final RoboticonQuest game;
	private final OrthographicCamera camera;
	private final Stage stage;
	private IsometricStaggeredTiledMapRenderer renderer;

	private TiledMap tmx;
	private TiledMapTileLayer mapLayer;
	private TiledMapTileLayer playerOverlay;

	private float oldX;
	private float oldY;

	private float oldW;
	private float oldH;
	private GameScreenActors actors;

	private LandPlot selectedPlot;
	private float maxDragX;
	private float maxDragY;


	public LandPlot getSelectedPlot() {
		return selectedPlot;
	}

	/**
	 * Initialise the class
	 * @param game  The game object
	 */
	public GameScreen(final RoboticonQuest game) {
		oldW = Gdx.graphics.getWidth();
		oldH = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, oldW, oldH);
		camera.update();


		this.game = game;

		// TODO: Add some HUD gui stuff (buttons, mini-map etc...)
		this.stage = new Stage(new ScreenViewport());
		this.actors = new GameScreenActors(game, this);
		actors.initialiseButtons();
		// actors.textUpdate();
		
		

		// Drag the map within the screen.
		stage.addListener(new DragListener() {
			/**
			 * On start of the drag event, record current position.
			 * @param event    The event object
			 * @param x        X position of mouse (on screen)
			 * @param y        Y position of mouse (on screen)
			 * @param pointer  unknown argument, not used.
			 */
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				oldX = x;
				oldY = y;
			}

			/**
			 * During the drag event, check against last recorded
			 * mouse positions, apply the offset in an opposite
			 * direction to the camera to create the drag effect.
			 *
			 * @param event    The event object.
			 * @param x        X position of mouse (on screen)
			 * @param y        Y position of mouse (on screen)
			 * @param pointer  unknown argument, not used.
			 */
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				// TODO: control of pausing the drag.
				// Prevent drag if the button is visible.
				if (actors.getBuyLandPlotBtn().isVisible()) return;

				float deltaX = x - oldX;
				float deltaY = y - oldY;

				// The camera translates in a different direction...
				camera.translate(-deltaX, -deltaY);
				if (camera.position.x < 20) camera.position.x = 20;
				if (camera.position.y < 20) camera.position.y = 20;
				if (camera.position.x > maxDragX) camera.position.x = maxDragX;
				if (camera.position.y > maxDragY) camera.position.y = maxDragY;

				// Record cords
				oldX = x;
				oldY = y;

				// System.out.println("drag to " + x + ", " + y);
			}
		});


		// Set initial camera position.
		camera.position.x = 20;
		camera.position.y = 50;

		//<editor-fold desc="Click event handler. Check `tileClicked` for how to handle tile click.">
		// Bind click event.
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				switch(game.getPhase()){
				case 1:
					if (actors.getBuyLandPlotBtn().isVisible()) {
						actors.getBuyLandPlotBtn().setVisible(false);
						return;
					}
				case 3:
					if (actors.getInstallRoboticonSelect().isVisible() && actors.getDropDownActive()) {
						actors.getInstallRoboticonSelect().setVisible(false);
						actors.getInstallRoboticonLabel().setVisible(false);
						return;
					}
				}

				// The Y from screen starts from bottom left.
				Vector3 cord = new Vector3(x, oldH - y, 0);
				camera.unproject(cord);

				// Padding offset
				cord.y -= 20;  // Padding from tile
				cord.x += 50;

				// Convert to grid index
				// http://2dengine.com/doc/gs_isometric.html

				float tile_height = mapLayer.getTileHeight();
				float tile_width = mapLayer.getTileWidth();

				float ty = cord.y - cord.x/2 - tile_height;
				float tx = cord.x + ty;
				ty = MathUtils.ceil(-ty/(tile_width/2));
				tx = MathUtils.ceil(tx/(tile_width/2)) + 1;
				int tileIndexX = MathUtils.floor((tx + ty)/2);
				int tileIndexY = -(int)(ty - tx);


				// TODO: Remove those magic numbers and fix it properly
				// Those magic numbers based on observation of number patterns
				tileIndexX -= 1;
				if (tileIndexY % 2 == 0) {
					tileIndexX --;
				}
				if (game.getPhase() != 3) selectedPlot = game.getPlotManager().getPlot(tileIndexX, tileIndexY);
				else if (actors.getDropDownActive()) selectedPlot = game.getPlotManager().getPlot(tileIndexX, tileIndexY);
				if (selectedPlot != null) {
					actors.tileClicked(selectedPlot, x, y);}
			}
		});
		//</editor-fold>

		// Finally, start a new game and initialise variables.
		// newGame();
	}

	public TiledMapTile getPlayerTile(Player player) {
		return tmx.getTileSets().getTile(68 + game.getPlayerIndex(player));
	}
	public TiledMapTile getResourcePlayerTile(Player player, ResourceType type){
		switch(type){
		case ORE:
			return tmx.getTileSets().getTile(68 + game.getPlayerIndex(player) + 4);
		case ENERGY:
			return tmx.getTileSets().getTile(68 + game.getPlayerIndex(player) + 8);
		default:
			return tmx.getTileSets().getTile(68 + game.getPlayerIndex(player) + 12);
		}
		}
			
	/**
	 * Reset to new game status.
	 */
	public void newGame() {
		// Setup the game board.
		if (tmx != null) tmx.dispose();
		if (renderer != null) renderer.dispose();
		this.tmx = new TmxMapLoader().load("tiles/city.tmx");
		renderer = new IsometricStaggeredTiledMapRenderer(tmx);
		game.reset();

		mapLayer = (TiledMapTileLayer)tmx.getLayers().get("MapData");
		playerOverlay = (TiledMapTileLayer)tmx.getLayers().get("PlayerOverlay");
		maxDragX = 0.75f * mapLayer.getTileWidth() * (mapLayer.getWidth() + 1);
		maxDragY = 0.75f * mapLayer.getTileHeight() * (mapLayer.getHeight() + 1);

		game.getPlotManager().setup(mapLayer, playerOverlay);
		game.nextPhase();
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

		// System.out.print("render, delta = " + delta);
		game.getBatch().setProjectionMatrix(stage.getCamera().combined);
		renderAnimation(delta);
	}

	/**
	 * Resize the viewport as the render window's size change.
	 * @param width   The new width
	 * @param height  The new height
	 */
	@Override
	public void resize(int width, int height) {
		// Avoid the viewport update if they are not changed.
		if (width != oldW || height != oldH) {
			stage.getViewport().update(width, height, true);
			camera.setToOrtho(false, width, height);
			actors.resizeScreen(width, height);
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

	@Override
	public RoboticonQuest getGame() {
		return game;
	}

	public Stage getStage() {
		return stage;
	}

	@Override
	public Size getScreenSize() {
		Size s = new Size();
		s.Height = oldH;
		s.Width = oldW;
		return s;
	}

	public TiledMap getTmx(){
		return this.tmx;
	}
	
	public GameScreenActors getActors(){
		return this.actors;
	}
}