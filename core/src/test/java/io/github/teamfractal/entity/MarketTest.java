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
		reset();

        int roboticBefore = player.getResource(ResourceType.Robotic);
        market.buyRoboticon(player,3);
        int roboticAfter = player.getResource(ResourceType.Robotic);

        assertEquals(3, roboticAfter - roboticBefore);
    }

    @org.junit.jupiter.api.Test
    void buyFood() {
	    Market market = new Market();
	    Player player = new Player();
	    player.addMoney(100000);

	    int roboticBefore = player.getResource(ResourceType.Robotic);
	    market.buyRoboticon(player,3);
	    int roboticAfter = player.getResource(ResourceType.Robotic);

	    assertEquals(3, roboticAfter - roboticBefore);
    }



    @org.junit.jupiter.api.Test
    void buyOre() {

    }

	@org.junit.jupiter.api.Test
	void buyEnergy() {
		Market market = new Market();
		Player player = new Player();
		player.addMoney(100000);

		int energyBefore = player.getResource(ResourceType.Energy);
		market.buyEnergy(player,3);
		int energyAfter = player.getResource(ResourceType.Energy);

		assertEquals(3, energyAfter - energyBefore);

	}

	@org.junit.jupiter.api.Test
	void sellEnergy() {
		Market market = new Market();
		Player player = new Player();
		player.setResource(ResourceType.Energy, 3);

		int energyBefore = player.getResource(ResourceType.Energy);
		market.sellEnergy(player,3);
		int energyAfter = player.getResource(ResourceType.Energy);

		assertEquals(0, energyAfter - energyBefore);

	}

}