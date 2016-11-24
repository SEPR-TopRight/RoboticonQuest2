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

}