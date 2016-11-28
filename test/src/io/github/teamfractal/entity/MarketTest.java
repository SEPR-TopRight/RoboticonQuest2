package io.github.teamfractal.entity;

import org.junit.*;

import java.util.Random;

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
	 * The market should be able to set and get resources.
	 */
	@Test
	public void marketShouldAbleToGetAndSetResources() {
		Random rnd = new Random();
		int valueToTest = rnd.nextInt(100);
		market.setEnergy(valueToTest);
		market.setOre(valueToTest);
		market.setFood(valueToTest);
		market.setRoboticon(valueToTest);


		assertEquals(valueToTest, market.getEnergy());
		assertEquals(valueToTest, market.getOre());
		assertEquals(valueToTest, market.getFood());
		assertEquals(valueToTest, market.getRoboticon());
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
		assertEquals(10, market.getBuyPrice(ResourceType.ORE));
		assertEquals(10, market.getBuyPrice(ResourceType.ENERGY));
		assertEquals(10, market.getBuyPrice(ResourceType.FOOD));
		assertEquals(10, market.getBuyPrice(ResourceType.ROBOTICON));
	}
}
