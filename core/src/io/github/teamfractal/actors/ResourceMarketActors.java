package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.screens.ResourceMarketScreen;

public class ResourceMarketActors extends Table {
	private final AdjustableActor oreBuy;
	private final AdjustableActor oreSell;
	private final AdjustableActor energyBuy;
	private final AdjustableActor energySell;
	private RoboticonQuest game;
	private Integer buyOreAmount;
	private Integer sellOreAmount;
	private Integer buyEnergyAmount;
	private Label phaseInfo;
	private Label playerStats;
	private ResourceMarketScreen screen;
	private TextButton nextButton;
	private Label marketStats;
	private Integer sellEnergyAmount;

	/**
	 * Get price in string format
	 *
	 * @param resource   The resource type.
	 * @param bIsSell    <code>true</code> if is for sell,
	 *                   or <code>false</code> if is for buy in.
	 * @return           The formatted string for the resource.
	 */
	private String getPriceString(ResourceType resource, boolean bIsSell) {
		// getBuyPrice: market buy-in price (user sell price)
		// getSellPrice: market sell price (user buy price)
		return resource.toString() + ": "
				+ (bIsSell
					? game.market.getBuyPrice(resource)
					: game.market.getSellPrice(resource))
				+ " Gold";
	}

	private void updateAdjustable(AdjustableActor adjustableActor, ResourceType resource, boolean bIsSell) {
		if (bIsSell) {
			adjustableActor.setMax(game.getPlayer().getResource(resource));
		} else {
			adjustableActor.setMax(game.market.getResource(resource));
		}

		adjustableActor.setTitle(getPriceString(resource, bIsSell));
	}

	/**
	 * Generate an adjustable actor for sell/buy.
	 *
	 * @param resource   The resource type.
	 * @param bIsSell    <code>true</code> if is for sell,
	 *                   or <code>false</code> if is for buy in.
	 * @return           The adjustable actor generated.
	 */
	private AdjustableActor addAdjustable(final ResourceType resource, final boolean bIsSell) {
		final Player player = game.getPlayer();
		final AdjustableActor adjustableActor = new AdjustableActor(game.skin, getPriceString(resource, bIsSell),
				(bIsSell ? "Sell" : "Buy") + " " + resource.toString());
		updateAdjustable(adjustableActor, resource, bIsSell);
		adjustableActor.setActionEvent(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (bIsSell) {
					// Sell from player to market.
					player.sellResourceToMarket(adjustableActor.getValue(), game.market, resource);
				} else {
					// Player buy from market.
					player.purchaseResourceFromMarket(adjustableActor.getValue(), game.market, resource);
				}

				ResourceMarketActors.this.widgetUpdate();
			}
		});
		add(adjustableActor);
		return adjustableActor;
	}

	public ResourceMarketActors(final RoboticonQuest game, ResourceMarketScreen screen) {
		// debug();
		center();

		this.game = game;
		this.screen = screen;

		Stage stage = screen.getStage();

		phaseInfo = new Label("", game.skin);
		phaseInfo.setAlignment(Align.right);

		nextButton = new TextButton("Next ->", game.skin);
		nextButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.nextPhase();
			}
		});
		stage.addActor(phaseInfo);
		stage.addActor(nextButton);



		// Row: player and market stats.
		playerStats = new Label("", game.skin);
		marketStats = new Label("", game.skin);
		marketStats.setAlignment(Align.right);

		add(playerStats);
		add().spaceRight(20);
		add(marketStats);
		rowWithHeight(20);

		// Row: Label of Sell and Buy
		Market market = game.market;
		Skin skin = game.skin;
		Label buyLabel  = new Label("Buy",  skin);
		Label sellLabel = new Label("Sell", skin);

		buyLabel.setAlignment(Align.center);
		sellLabel.setAlignment(Align.center);


		add(buyLabel);
		add();
		add(sellLabel);
		rowWithHeight(10);

		// Row: Ore buy/sell
		oreBuy = addAdjustable(ResourceType.ORE, false);
		add();
		oreSell = addAdjustable(ResourceType.ORE, true);
		rowWithHeight(10);

		// Row: Energy buy/sell
		energyBuy = addAdjustable(ResourceType.ENERGY, false);
		add();
		energySell = addAdjustable(ResourceType.ENERGY, true);
		rowWithHeight(10);

		widgetUpdate();
	}

	/**
	 * Add an empty row to current table.
	 * @param height  The height for that empty row.
	 */
	private void rowWithHeight(int height) {
		row();
		add().spaceTop(height);
		row();
	}

	/**
	 * Updates all widgets on screen
	 */
	public void widgetUpdate() {
		// update player stats, phase text, and the market stats.
		String phaseText =
				"Player " + (game.getPlayerInt() + 1) + "; " +
				"Phase " + game.getPhase() + " - " + game.getPhaseString();

		String statText =
				"Ore: "    + game.getPlayer().getOre()    + "  " +
				"Energy: " + game.getPlayer().getEnergy() + "  " +
				"Food: "   + game.getPlayer().getFood()   + "  " +
				"Money: "  + game.getPlayer().getMoney();

		String marketStatText =
				"Ore: " +    game.market.getResource(ResourceType.ORE   ) + "  " +
				"Energy: " + game.market.getResource(ResourceType.ENERGY) + "  " +
				"Food: " +   game.market.getResource(ResourceType.FOOD  );

		phaseInfo.setText(phaseText);
		playerStats.setText(statText);
		marketStats.setText(marketStatText);

		updateAdjustable(oreBuy, ResourceType.ORE, false);
		updateAdjustable(oreSell, ResourceType.ORE, true);

		updateAdjustable(energyBuy, ResourceType.ENERGY, false);
		updateAdjustable(energySell, ResourceType.ENERGY, true);
	}

	public void screenResize(float width, float height) {
		// Bottom Left
		phaseInfo.setPosition(0, height - 20);
		phaseInfo.setWidth(width - 10);

		// TOP LEFT
		// playerStats.setPosition(10, height - 20);

		// TOP RIGHT
		// marketStats.setWidth(width - 10);
		// marketStats.setPosition(0, height - 20);

		// Bottom Right
		nextButton.setPosition(width - nextButton.getWidth() - 10, 10);

		setWidth(width);
	}
}
