package io.github.teamfractal.actors;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.screens.GameScreen;

public class GameScreenActors {
	private final Stage stage;
	private RoboticonQuest game;
	private GameScreen screen;
	private Label topText;
	private Label playerStats;
	private TextButton buyLandPlotBtn;
	private TextButton nextButton;

	public GameScreenActors(final RoboticonQuest game, GameScreen screen) {
		this.game = game;
		this.screen = screen;
		this.stage = screen.getStage();
	}

	public void initialiseButtons() {
		nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				buyLandPlotBtn.setVisible(false);
				game.nextPhase();
				textUpdate();
			}
		});

		nextButton.setPosition(this.stage.getWidth() - 80, 0);
		stage.addActor(nextButton);


		buyLandPlotBtn = new TextButton("Buy LandPlot", game.skin);
		buyLandPlotBtn.setVisible(false);
		buyLandPlotBtn.pad(2, 10, 2, 10);
		buyLandPlotBtn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				LandPlot selectedPlot = screen.getSelectedPlot();

				// TODO: purchase land
				if (selectedPlot.hasOwner()) {
					return ;
				}

				Player player = game.getPlayer();
				if (player.purchaseLandPlot(selectedPlot)) {
					TiledMapTileLayer.Cell playerTile = selectedPlot.getPlayerTile();
					playerTile.setTile(screen.getPlayerTile(player));
					textUpdate();
				}
			}
		});
		stage.addActor(buyLandPlotBtn);
	}

	/**
	 * Tile click callback event.
	 * @param plot          The landplot tileClicked.
	 * @param x             Current mouse x position
	 * @param y             Current mouse y position
	 */
	public void tileClicked(LandPlot plot, float x, float y) {
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

	public void nextUpdate() {
		if (nextButton != null) nextButton.remove();
		nextButton.setPosition(this.stage.getWidth() - 80, 0);
		stage.addActor(nextButton);
	}

	public void textUpdate() {
		if (this.topText != null) this.topText.remove();
		String phaseText = "Player " + (game.getPlayerInt() + 1) + "; Phase " + game.getPhase() + " - " + game.getPhaseString();
		this.topText = new Label(phaseText, game.skin);
		topText.setWidth(120);
		topText.setPosition(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(topText);

		if (this.playerStats != null) this.playerStats.remove();
		String statText = "Ore: " + game.getPlayer().getOre() + " Energy: " + game.getPlayer().getEnergy() + " Food: "
				+ game.getPlayer().getFood() + " Money: " + game.getPlayer().getMoney();
		this.playerStats = new Label(statText, game.skin);
		playerStats.setWidth(250);
		playerStats.setPosition(0, stage.getViewport().getWorldHeight() - 20);
		stage.addActor(playerStats);
	}

	public TextButton getBuyLandPlotBtn() {
		return buyLandPlotBtn;
	}
}
