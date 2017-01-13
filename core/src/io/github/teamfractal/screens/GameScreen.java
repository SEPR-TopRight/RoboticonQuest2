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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;

public class GameScreen implements Screen {
	private final RoboticonQuest game;
	private final OrthographicCamera camera;
	private final Stage stage;
	private final Table table;
	private IsometricStaggeredTiledMapRenderer renderer;

	private TiledMap tmx;
	private TiledMapTileLayer mapLayer;
	private TiledMapTileLayer playerOverlay;

	private TextButton buyLandPlotBtn;
	private TextButton nextButton;
	private Label topText;
	private Label playerStats;
	private float oldX;
	private float oldY;

	private float oldW;
	private float oldH;

	private LandPlot selectedPlot;
	

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
		this.table = new Table();
		stage.addActor(table);
		nextButton = new TextButton("Next Phase", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			buyLandPlotBtn.setVisible(false);
			game.nextPhase();
			topTextUpdate();
			playerStatsUpdate();
			}
		});
		nextButton.setPosition(stage.getViewport().getWorldWidth() - 80, 0);

		stage.addActor(nextButton);
		topTextUpdate();
		playerStatsUpdate();
		
		

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
				if (buyLandPlotBtn.isVisible()) return;

				float deltaX = x - oldX;
				float deltaY = y - oldY;

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




		buyLandPlotBtn = new TextButton("Buy LandPlot", game.skin);
		buyLandPlotBtn.setVisible(false);
		buyLandPlotBtn.pad(2, 10, 2, 10);
		buyLandPlotBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Click::BuyLandPlot");
				// TODO: purchase land
				if (selectedPlot.hasOwner()) {
					return ;
				}

				Player player = game.getPlayer();
				if (player.purchaseLandPlot(selectedPlot)) {
					TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
					playerTile.setTile(tmx.getTileSets().getTile(101 + game.getPlayerInt()));

					playerStatsUpdate();
				}
			}
		});
		stage.addActor(buyLandPlotBtn);



		// Set initial camera position.
		camera.position.x = 20;
		camera.position.y = 50;

		//<editor-fold desc="Click event handler. Check `tileClicked` for how to handle tile click.">
		// Bind click event.
		stage.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (buyLandPlotBtn.isVisible()) {
					buyLandPlotBtn.setVisible(false);
					return;
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

				selectedPlot = game.getPlotManager().getPlot(tileIndexX, tileIndexY);
				if (selectedPlot != null) {
					GameScreen.this.tileClicked(selectedPlot, x, y);
				}
			}
		});
		//</editor-fold>

		// Finally, start a new game and initialise variables.
		newGame();
	}

	/**
	 * Tile click callback event.
	 * @param plot          The landplot clicked.
	 * @param x             Current mouse x position
	 * @param y             Current mouse y position
	 */
	private void tileClicked(LandPlot plot, float x, float y) {
		Player player = game.getPlayer();

		// TODO: Need proper event callback
		switch (game.getPhase()) {
			// Phase 1:
			// Purchase LandPlot.
			case 1:
				buyLandPlotBtn.setPosition(x, y);
				if (plot.hasOwner() || !player.haveEnoughMoneyForLandplot()) {
					buyLandPlotBtn.setDisabled(true);
				} else {
					buyLandPlotBtn.setDisabled(false);
				}
				buyLandPlotBtn.setVisible(true);
				break;
		}
	}
	public void topTextUpdate(){
		if (this.topText != null) this.topText.remove();
		String text = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase();
		this.topText = new Label(text, game.skin);
		topText.setWidth(120);
		topText.setPosition(stage.getViewport().getWorldWidth()/2, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(topText);
	}
	
	public void playerStatsUpdate(){
		if (this.playerStats != null) this.playerStats.remove();
		String text = "Ore: " + game.getPlayer().getOre() + " Energy: " +  game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(text, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(playerStats);
	}
	/**
	 * Reset to new game status.
	 */
	public void newGame() {
		// Setup the game board.
		if (tmx != null) tmx.dispose();
		if (renderer != null) renderer.dispose();

		tmx = new TmxMapLoader().load("tiles/city.tmx");
		renderer = new IsometricStaggeredTiledMapRenderer(tmx);

		mapLayer = (TiledMapTileLayer)tmx.getLayers().get("MapData");
		playerOverlay = (TiledMapTileLayer)tmx.getLayers().get("PlayerOverlay");

		game.getPlotManager().setup(mapLayer, playerOverlay);
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

	/**
	 * Resize the viewport as the render window's size change.
	 * @param width   The new width
	 * @param height  The new height
	 */
	@Override
	public void resize(int width, int height) {
		// Avoid the viewport update if they are not changed.
		if (width != oldW && height != oldH) {
			stage.getViewport().update(width, height, true);
			camera.setToOrtho(false, width, height);
			topTextUpdate();
			playerStatsUpdate();
			nextButton.setPosition(stage.getViewport().getWorldWidth() - 80, 0);
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