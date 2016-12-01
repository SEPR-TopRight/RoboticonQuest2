package io.github.teamfractal.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
	private Player player;

	@Before
	public void setUp() {
		player = new Player();
	}

	//Money Tests
	@Test
	public void testPlayerInitialMoney() {
		assertEquals(100, player.getMoney());
	}

	/**
	 * Test to purchase and sell resource from the market.
	 */
	@Test
	public void testPlayerBuyResource() {
		Market market = new Market();
		market.setOre(16);
		//Purchase 5 ore
		player.purchaseResourceFromMarket(5, market, ResourceType.ORE);
		// Player should now have 5 more ores, and the market have 5 less ores.
		assertEquals(100 - 5 * market.getSellPrice(ResourceType.ORE), player.getMoney());
		assertEquals(5, player.getOre());
		assertEquals(11, market.getOre());
		//purchase 10 energy
		player.purchaseResourceFromMarket(10, market, ResourceType.ENERGY);
		assertEquals(95 - 10 * market.getSellPrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(10, player.getEnergy());
		assertEquals(6, market.getEnergy());
	}

	@Test
	public void testPlayerSellResource() throws Exception {
		Market market = new Market();
		player.purchaseResourceFromMarket(15, market, ResourceType.ORE);
		player.purchaseResourceFromMarket(15, market, ResourceType.ENERGY);
		//sell 5 ore
		player.sellResourceToMarket(5, market, ResourceType.ORE);
		assertEquals(70 + 5 * market.getBuyPrice(ResourceType.ORE), player.getMoney());
		assertEquals(10, player.getOre());
		assertEquals(5, market.getOre());
		//sell 5 energy
		player.sellResourceToMarket(5, market, ResourceType.ENERGY);
		assertEquals(75 + 5 * market.getBuyPrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(10, player.getEnergy());
		assertEquals(21, market.getEnergy());
	}

	@Test
	public void testPlayerCannotBuyMoreThanAllowed() throws Exception {
		Market market = new Market();
		// Attempt to purchase more ore than allowed
		try {
			player.purchaseResourceFromMarket(100, market, ResourceType.ORE);
		} catch (Exception exception1) {
			assertEquals(100, player.getMoney());
			assertEquals(0, player.getOre());
			// Attempt to purchase more energy than allowed
			try {
				player.purchaseResourceFromMarket(100, market, ResourceType.ENERGY);
			} catch (Exception exception2) {
				assertEquals(100, player.getMoney());
				assertEquals(0, player.getEnergy());
			}
		}
	}
	
	@Test
	public void testPlayerCannotSellMoreThanAllowed() throws Exception {
		Market market = new Market();
		player.purchaseResourceFromMarket(15, market, ResourceType.ORE);
		player.purchaseResourceFromMarket(15, market, ResourceType.ENERGY);
		// Attempt to sell more ore than allowed
		try {
			player.sellResourceToMarket(10, market, ResourceType.ORE);
		} catch (Exception exception1) {
			assertEquals(100 + 10 * market.getBuyPrice(ResourceType.ORE), player.getMoney());
			assertEquals(5, player.getEnergy());
			// Attempt to sell more energy than allowed
			try {
				player.sellResourceToMarket(10, market, ResourceType.ENERGY);
			} catch (Exception exception2) {
				assertEquals(100 + 10 * market.getBuyPrice(ResourceType.ENERGY), player.getMoney());
				assertEquals(5, player.getEnergy());
			}
		}
	}

	@Test
	public void testPlayerCanCustomiseRoboticon() {
		// Setup
		Roboticon roboticon = new Roboticon();
		player.customiseRoboticon(roboticon, ResourceType.ORE);
		assertEquals(ResourceType.ORE, roboticon.getCustomisation());

		Roboticon roboticon2 = new Roboticon();
		player.customiseRoboticon(roboticon2, ResourceType.ENERGY);
		assertEquals(ResourceType.ENERGY, roboticon2.getCustomisation());
	}

	@Test
	public void testPlayerCanCustomiseOwnedRoboticons() {
		Roboticon[] array = {new Roboticon(), new Roboticon()};
		player.roboticonList = new ArrayList<Roboticon>(Arrays.asList(array));
		player.customiseRoboticon(player.roboticonList.get(0), ResourceType.ORE);
		player.customiseRoboticon(player.roboticonList.get(1), ResourceType.ENERGY);
		assertEquals(ResourceType.ORE, player.roboticonList.get(0).getCustomisation());
		assertEquals(ResourceType.ENERGY, player.roboticonList.get(1).getCustomisation());
	}

}