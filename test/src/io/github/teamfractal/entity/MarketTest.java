package io.github.teamfractal.entity;

import org.junit.*;

import static org.junit.Assert.*;

public class MarketTest {
	private Market market;

	/**
	 * Reset market to its default status.
	 */
	@Before
	public void Contractor() {
		market = new Market();
	}

	/**
	 * The market should start with correct amount of resources.
	 * 16 Food & Energy, 0 Ore, 12 Robotics
	 */
	@Test
	public void marketShouldInitWithCorrectValues() {
		assertEquals(16, market.getFood());
		assertEquals(16, market.getEnergy());
		assertEquals(0, market.getOre());
		assertEquals(12, market.getRobotics());
	}

	/**
	 * The market should provide a way that allows the player
	 * to purchase Resources from the market.
	 *
	 * After purchase, corresponding amount of resources should
	 * be reduced from the market resources listing.
	 */
	@Test
	public void marketShouldAllowPlayerToBuyResources() {
		player.setMoney(10000);
		player.setEnergy(0);
		player.setFood(0);
		player.setOre(0);
		player.setRobotics(0);

		assertEquals(true, market.buyEnergy(player,3));
		assertEquals(true, market.buyFood(player,3));
		assertEquals(true, market.buyOre(player,3));
		assertEquals(true, market.buyRobotics(player,3));

		assertEquals(false, market.buyEnergy(player,3));
		assertEquals(false, market.buyFood(player,3));
		assertEquals(false, market.buyOre(player,3));
		assertEquals(false, market.buyRobotics(player,3));
	}


	public void marketShouldAllowPlayerToBuyResources() {
		player.setMoney(10000);
		player.setEnergy(10);
		player.setFood(10);
		player.setOre(10);
		player.setRobotics(10);

		assertEquals(true, market.sellEnergy(player,3));
		assertEquals(true, market.sellFood(player,3));
		assertEquals(true, market.sellOre(player,3));
		assertEquals(true, market.sellRobotics(player,3));

		assertEquals(false, market.sellEnergy(player,11));
		assertEquals(false, market.sellFood(player,11));
		assertEquals(false, market.sellOre(player,11));
		assertEquals(false, market.sellRobotics(player,11));

	}
}
