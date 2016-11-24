package io.github.teamfractal.entity;

import io.github.teamfractal.entity.resource.ResourceType;

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
	}

	/**
	 * Reset variables for buy actions
	 */
	void resetBuy(){
		reset();

		// Give the player A LOT of money.
		player.addMoney(100000);
	}

	@org.junit.jupiter.api.Test
    void buyRoboticon() {
    }

    @org.junit.jupiter.api.Test
    void buyFood() {
    }

    @org.junit.jupiter.api.Test
    void buyEnergy() {

    }

    @org.junit.jupiter.api.Test
    void buyOre() {

    }

	@org.junit.jupiter.api.Test
	void sellOre () {
		reset();

		int playerOreBefore = 100;
		int oreToSell = 10;
		player.setOre(playerOreBefore);
		int marketOreBefore = market.getOre();
		market.sellOre(player, oreToSell);
		int playerOreAfter = player.getOre();

		// Check if 10 ores were sold to market.
		assertEquals(playerOreBefore - playerOreAfter, oreToSell);

	}
}