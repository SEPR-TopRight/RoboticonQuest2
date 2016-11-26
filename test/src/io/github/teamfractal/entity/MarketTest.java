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

	}
}
