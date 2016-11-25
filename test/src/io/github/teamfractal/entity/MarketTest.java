package io.github.teamfractal.entity;

import io.github.teamfractal.entity.resource.ResourceType;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTest {
	Market market;
	Player player;

	/**
	 * Reset variables.
	 */
	public void reset () {
		market = new Market();
		player = new Player();

		// Give the player A LOT of money.
		player.addMoney(100000);
	}

	/**
	 * Player should be able to purchase robotics from market.
	 */
	@Test
	public void buyRoboticon() {
		reset();

        int roboticBefore = player.getResource(ResourceType.Robotic);
        market.buyRoboticon(player,3);
        int roboticAfter = player.getResource(ResourceType.Robotic);

        assertEquals(3, roboticAfter - roboticBefore);
    }

	/**
	 * Market upon initialize should have expected number of resources.
	 */
	@Test
	public void initResources(){
		reset();

		assertEquals(market.getOre(), 0);
		assertEquals(market.getEnergy(), 16);
		assertEquals(market.getFood(), 16);
		assertEquals(market.getRobotic(), 12);
	}

	/**
	 * Player should be able to purchase energy from market.
	 */
	@Test
	public void buyEnergy() {
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

	/**
	 * Player should be able to sell energy to market.
	 */
	@Test
	public void sellEnergy() {
		Market market = new Market();
		Player player = new Player();
		player.setResource(ResourceType.Energy, 3);

		market.sellEnergy(player,3);
		int energyAfter = player.getResource(ResourceType.Energy);


		assertEquals(0, energyAfter);
	}

	/**
	 * Player should be able to sell ore to market.
	 */
	@Test
	public void sellOre () {
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


	@Test
	public void testRoboticonProduction() {
		// setup
		Market market = new Market();
		int oreBefore = market.getOre();
		int roboticonBefore = market.getRoboticons();
		// Action
		market.generateRoboticon();
		// Tests
		assertEquals(oreBefore - 2, market.getOre());
		assertEquals(roboticonBefore + 1, market.getRoboticons());
	}
}