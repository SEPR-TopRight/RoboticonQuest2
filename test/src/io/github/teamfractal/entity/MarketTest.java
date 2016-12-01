package io.github.teamfractal.entity;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.*;

public class MarketTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

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
	public void marketShouldHaveCorrectPricesForResources() throws Exception {
		assertEquals(20, market.getBuyPrice(ResourceType.ORE));
		assertEquals(30, market.getBuyPrice(ResourceType.ENERGY));
		assertEquals(40, market.getBuyPrice(ResourceType.FOOD));
		assertEquals(100, market.getBuyPrice(ResourceType.ROBOTICON));
	}


	/**
	 * player class can use this method to find out that the amount of resource
	 * player want to buy is avaliable in the market, if the amount of resource
	 * in the market is less than the amount of resources player want to buy then
	 * throw exception
	 */

	@Test
	public void marketCanCheckResourceMoreThanAmountYouWantToBuy() {
		try{
			//market.checkResourcesMoreThanAmount(ResourceType.FOOD, 1000000);
			market.checkResourcesMoreThanAmount(ResourceType.ORE, 1000000);
			market.checkResourcesMoreThanAmount(ResourceType.ENERGY, 1000000);
			market.checkResourcesMoreThanAmount(ResourceType.ROBOTICON, 1000000);
		}
		catch(Exception exception) {
			return;
		}

		fail("Shoud throw exception");
	}


}
