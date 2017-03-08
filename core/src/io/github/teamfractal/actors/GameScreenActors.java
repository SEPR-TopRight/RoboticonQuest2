package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.AbstractAnimationScreen;
import io.github.teamfractal.screens.GameScreen;
import io.github.teamfractal.util.SoundEffects;
import io.github.teamfractal.util.TileConverter;

public class GameScreenActors {
	private final Stage stage;
	private RoboticonQuest game;
	private GameScreen screen;
	private Label phaseInfo;
	private Label playerStats;
	private TextButton buyLandPlotBtn;
	private TextButton installRoboticonBtn;
	private TextButton installRoboticonBtnCancel;
	private Label installRoboticonLabel;
	private SelectBox<String> installRoboticonSelect;
	private Label plotStats;
	private TextButton nextButton;
	private Image background;
	private float backgroundX, backgroundY;
	private boolean dropDownActive; // TODO figure out if this is needed
	private boolean listUpdated;

	/**
	 * Initialise the main game screen components.
	 * @param game         The game manager {@link RoboticonQuest}
	 * @param screen       Current screen to display on.
	 */
	public GameScreenActors(final RoboticonQuest game, GameScreen screen) {
		this.game = game;
		this.screen = screen;
		this.stage = screen.getStage();
	}

	/**
	 * Setup buttons.
	 */
	public void initialiseButtons() {
		// Create UI components
		phaseInfo = new Label("", game.skin);
		plotStats = new Label("", game.skin);
		playerStats = new Label("", game.skin);
		nextButton = new TextButton("Next ->", game.skin);
		buyLandPlotBtn = new TextButton("Buy LandPlot", game.skin);
		background = new Image(new Texture(Gdx.files.internal("background/space-stars.jpeg")));
		createRoboticonInstallMenu();

		// Adjust properties.
		listUpdated = false;
		hideInstallRoboticon();
		buyLandPlotBtn.setVisible(false);
		buyLandPlotBtn.pad(2, 10, 2, 10);
		phaseInfo.setAlignment(Align.right);
		plotStats.setAlignment(Align.topLeft);
		installRoboticonSelect.setSelected(null);

		// Bind events
		bindEvents();

		// Add to the stage for rendering.
		stage.addActor(nextButton);
		stage.addActor(buyLandPlotBtn);
		stage.addActor(installRoboticonTable);
		stage.addActor(phaseInfo);
		stage.addActor(plotStats);
		stage.addActor(playerStats);

		// Update UI positions.
		AbstractAnimationScreen.Size size = screen.getScreenSize();
		resizeScreen(size.Width, size.Height);
	}

	private Table installRoboticonTable;

	/**
	 * Create the roboticon installation menu.
	 */
	private void createRoboticonInstallMenu() {
		installRoboticonTable = new Table();
		Table t = installRoboticonTable;

		installRoboticonSelect = new SelectBox<String>(game.skin);
		
		// (Top Right Corner) changed the below to use getCustomisedRoboticonAmountList instead
		// of getRoboticonAmountList as we don't want players to be able to see how many uncustomised roboticons
		// they have when they go to place a roboticon (as they shouldn't be able to place uncustomised 
		// roboticons)
		installRoboticonSelect.setItems(game.getPlayer().getCustomisedRoboticonAmountList());

		installRoboticonLabel = new Label("Install Roboticon: ", game.skin);
		installRoboticonBtn = new TextButton("Confirm", game.skin);
		installRoboticonBtnCancel = new TextButton("Cancel", game.skin);

		t.add(installRoboticonLabel).colspan(2);
		t.row();
		t.add(installRoboticonSelect).colspan(2);
		t.row();
		t.add(installRoboticonBtn);
		t.add(installRoboticonBtnCancel);
		t.row();
	}


	/**
	 * Bind all button events.
	 */
	private void bindEvents() {
		buyLandPlotBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				hideBuyLand();
				if (buyLandPlotBtn.isDisabled()) {
					return ;
				}
				LandPlot selectedPlot = screen.getSelectedPlot();

				if (selectedPlot.hasOwner()) {
					return;
				}

				Player player = game.getPlayer();
				if (player.purchaseLandPlot(selectedPlot)) {
					SoundEffects.click();
					TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
					playerTile.setTile(screen.getPlayerTile(player));
					textUpdate();
					game.incCount();
				}
			}
		});

		nextButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				if (nextButton.isDisabled()) {
					return ;
				}
				SoundEffects.click();
				buyLandPlotBtn.setVisible(false);
				plotStats.setVisible(false);
				hideInstallRoboticon();
				game.nextPhase();
				dropDownActive = true;
				// Changed to getCustomisedRoboticonAmountList by Josh Neil so that players
				// do not see how many uncustomised roboticions they have when they go to placed one
				installRoboticonSelect.setItems(game.getPlayer().getCustomisedRoboticonAmountList());
				textUpdate();
			}
		});

		installRoboticonBtn.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				if (installRoboticonBtn.isDisabled()) {
					return ;
				}
				if (!listUpdated) { //prevents updating selection list from updating change listener
					LandPlot selectedPlot = screen.getSelectedPlot();
					if (selectedPlot.getOwner() == game.getPlayer() && !selectedPlot.hasRoboticon()) {
						Roboticon roboticon = null;
						ResourceType type = ResourceType.Unknown;
						int selection = installRoboticonSelect.getSelectedIndex();

						Array<Roboticon> roboticons = game.getPlayer().getRoboticons();
						switch (selection) {
							case 0:
								type = ResourceType.ORE;
								break;
							case 1:
								type = ResourceType.ENERGY;
								break;
							case 2:
								type = ResourceType.FOOD;
								break;
							default:
								type = ResourceType.Unknown;
								break;
						}

						for (Roboticon r : roboticons) {
							if (!r.isInstalled() && r.getCustomisation() == type) {
								roboticon = r;
								break;
							}
						}

						if (roboticon != null) {
							SoundEffects.click();
							selectedPlot.installRoboticon(roboticon);
							TiledMapTileLayer.Cell roboticonTile = selectedPlot.getRoboticonTile();
							roboticonTile.setTile(TileConverter.getRoboticonTile(roboticon.getCustomisation()));
							selectedPlot.setHasRoboticon(true);
							textUpdate();
						}

						hideInstallRoboticon();
						updateRoboticonList();
						dropDownActive = true;

					} else listUpdated = false;
				}
			}
		});

		installRoboticonBtnCancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				event.stop();
				SoundEffects.click();
				dropDownActive = false;
				hideInstallRoboticon();
			}
		});
	}

	/**
	 * Tile click callback event.
	 *
	 * @param plot The landplot tileClicked.
	 * @param x    Current mouse x position
	 * @param y    Current mouse y position
	 */
	public void tileClicked(LandPlot plot, float x, float y) {
		Player player = game.getPlayer();
		SoundEffects.click();

		switch (game.getPhase()) {
			// Phase 1:
			// Purchase LandPlot.
			case 1:
				buyLandPlotBtn.setPosition(x + 10, y);
				if(!(player.haveEnoughMoney(plot)
		                  && !plot.hasOwner())){
					buyLandPlotBtn.setVisible(false);
					showUnavailable(x + 10, y);
				}
				else{
					buyLandPlotBtn.setVisible(true);
					showPlotStats(plot, x + 10, y);
				    }
				buyLandPlotBtn.setDisabled(!(!plot.hasOwner()
						                  && player.haveEnoughMoney(plot)));
				

				break;

			// Phase 3:
			// Install Roboticon 
			case 3:
				if (player == plot.getOwner()) {
					installRoboticonTable.setPosition(x, y, Align.center);
					updateRoboticonList();
					installRoboticonTable.setVisible(true);
				} else {
					hideInstallRoboticon();
				}
		}


	}

	/**
	 * Update the dropdown list of roboticon available.
	 */
	private void updateRoboticonList() {
		// (Top Right Corner) changed the below to use getCustomisedRoboticonAmountList instead
		// of getRoboticonAmountList as we don't want players to be able to see how many uncustomised roboticons
		// they have when they go to place a roboticon (as they shouldn't be able to place uncustomised 
		// roboticons)
		installRoboticonSelect.setItems(game.getPlayer().getCustomisedRoboticonAmountList());
	}

	/**
	 * Get the "Buy Land" button.
	 * @return "Buy Land" button.
	 */
	public TextButton getBuyLandPlotBtn() {
		return buyLandPlotBtn;
	}

	/**
	 * Updates the UI display.
	 */
	public void textUpdate() {
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase() + " - " + game.getPhaseString();
		phaseInfo.setText(phaseText);

		String statText = "Ore: " + game.getPlayer().getOre()
				+ " Energy: " + game.getPlayer().getEnergy()
				+ " Food: " + game.getPlayer().getFood()
				+ " Money: " + game.getPlayer().getMoney();

		playerStats.setText(statText);
	}

	/**
	 * Callback event on window updates,
	 * to adjust the UI components position relative to the screen.
	 *
	 * @param width    The new Width.
	 * @param height   The new Height.
	 */
	public void resizeScreen(float width, float height) {
		float topBarY = height - 20;
		phaseInfo.setWidth(width - 10);
		phaseInfo.setPosition(0, topBarY);

		playerStats.setPosition(10, topBarY);
		nextButton.setPosition(width - nextButton.getWidth() - 10, 10);

		backgroundX = width/background.getWidth();
		backgroundY = height/background.getHeight();
		background.setScale(backgroundX, backgroundY);
	}

	/**
	 * Show plot information about current selected stats.
	 * @param plot           The land plot to show info.
	 * @param x              The <i>x</i> position to display the information.
	 * @param y              The <i>y</i> position to display the information.
	 */
	public void showPlotStats(LandPlot plot, float x, float y) {
		String plotStatText = "Ore: " + plot.getResource(ResourceType.ORE)
				+ "  Energy: " + plot.getResource(ResourceType.ENERGY) + "  Food: " + plot.getResource(ResourceType.FOOD);

		plotStats.setText(plotStatText);
		plotStats.setPosition(x, y);
		plotStats.setVisible(true);
	}
	
	public void showUnavailable(float x, float y) {
		String plotStatText = "Not Available";

		plotStats.setText(plotStatText);
		plotStats.setPosition(x, y);
		plotStats.setVisible(true);
	}
	public void updateRoboticonSelection() {
		// TODO: Implement this method
	}

	/**
	 * Hide "Buy Land" button and plot information.
	 */
	public void hideBuyLand() {
		buyLandPlotBtn.setVisible(false);
		plotStats.setVisible(false);
	}

	/**
	 * Hide install roboticon dialog.
	 */
	public void hideInstallRoboticon() {
		installRoboticonTable.setVisible(false);
	}

	/**
	 * Check if install roboticon dialog is visible.
	 * @return   <code>true</code> if is visible, <code>false</code> if not visible.
	 */
	public boolean installRoboticonVisible() {
		return installRoboticonTable.isVisible();
	}

	public Image getBackground(){return background;}
}
