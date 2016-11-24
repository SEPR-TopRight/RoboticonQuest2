package io.github.teamfractal.entity;

import io.github.teamfractal.entity.resource.ResourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
	Market market;
	Player player;

	/**
	 * Reset variables.
	 */
	void reset () {
		market = new Market();
		player = new Player();

		// Give the player A LOT of money.
		player.addMoney(100000);
	}

	@Test
	void initResources(){
		reset();

		assertEquals(market.getOre(), 0);
		assertEquals(market.getEnergy(), 16);
		assertEquals(market.getFood(), 16);
		assertEquals(market.getRobotic(), 12);
	}

	@Test
	void sellOre () {
		reset();


		/// Setup variables
		int playerOreBefore = 100;
		int oreToSell = 10;
		player.setOre(playerOreBefore);
		int marketOreBefore = market.getOre();
		double moneyBefore = player.getMoney();

		/// Test body
		market.sellOre(player, oreToSell);

		/// Variables to be tested.
		int marketOreAfter = market.getOre();
		int playerOreAfter = player.getOre();
		double moneyAfter = player.getMoney();

		// Check if 10 ores were sold to market.
		assertEquals(playerOreBefore - playerOreAfter,  oreToSell);
		assertEquals(marketOreBefore - marketOreAfter, -oreToSell);

		// Check if player's money increased.
		assertTrue(moneyAfter > moneyBefore);
	}
}