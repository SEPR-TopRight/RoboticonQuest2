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
		resetBuy();

        int roboticBefore = player.getResource(ResourceType.Robotic);
        market.buyRoboticon(player,3);
        int roboticAfter = player.getResource(ResourceType.Robotic);

        assertEquals(3, roboticAfter - roboticBefore);
    }

    @org.junit.jupiter.api.Test
    void buyFood() {
    }



    @org.junit.jupiter.api.Test
    void buyOre() {

    }

	@org.junit.jupiter.api.Test
	void buyEnergy() {
		Market market = new Market();
		Player player = new Player();
		player.setResource(ResourceType.Energy, 0);

		player.addMoney(100000);

		market.buyEnergy(player,3);
		int energyAfter = player.getResource(ResourceType.Energy);

		// check if player energy increase,
		// this should increase 3 since there is 0 energy in the beginning
		assertEquals(3, energyAfter);


	}

	@org.junit.jupiter.api.Test
	void sellEnergy() {
		Market market = new Market();
		Player player = new Player();
		player.setResource(ResourceType.Energy, 3);

		market.sellEnergy(player,3);
		int energyAfter = player.getResource(ResourceType.Energy);


		assertEquals(0, energyAfter);

	}

}