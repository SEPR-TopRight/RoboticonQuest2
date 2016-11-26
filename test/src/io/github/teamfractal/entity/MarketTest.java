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
		assertEquals(12, market.getRoboticon());
	}

	/**
	 * The market should start with correct price for player to buy.
	 * Prices listed here are subjected for change in later development.
	 *
	 * Ore: $10
	 * Food: $10
	 * Energy: $10
	 * Roboticon: $10
	 */
	@Test
	public void marketShouldHaveCorrectPricesForResources() {
		assertEquals(10, market.getBuyPrice(ResourceType.Ore));
		assertEquals(10, market.getBuyPrice(ResourceType.Energy));
		assertEquals(10, market.getBuyPrice(ResourceType.Food));
		assertEquals(10, market.getBuyPrice(ResourceType.Roboticon));
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
