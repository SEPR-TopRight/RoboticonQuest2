package io.github.teamfractal.entity;

import io.github.teamfractal.exception.NotEnoughResourceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

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
		player.setMoney(1000);


		int playerMoney = player.getMoney();
		int orePrice = market.getSellPrice(ResourceType.ORE);
		//Purchase 5 ore
		player.purchaseResourceFromMarket(5, market, ResourceType.ORE);
		// Player should now have 5 more ores, and the market have 5 less ores.
		assertEquals(playerMoney - 5 * orePrice, player.getMoney());
		assertEquals(5, player.getOre());
		assertEquals(11, market.getOre());


		playerMoney = player.getMoney();
		int energyPrice = market.getSellPrice(ResourceType.ORE);
		//purchase 10 energy
		player.purchaseResourceFromMarket(10, market, ResourceType.ENERGY);
		assertEquals(playerMoney - 10 * energyPrice, player.getMoney());
		assertEquals(10, player.getEnergy());
		assertEquals(6, market.getEnergy());
	}

	@Test
	public void testPlayerSellResource() throws Exception {
		Market market = new Market();

		player.setMoney(1000);
		player.setResource(ResourceType.ORE, 15);
		player.setResource(ResourceType.ENERGY, 15);


		int orePrice = market.getBuyPrice(ResourceType.ORE);
		//sell 5 ore
		player.sellResourceToMarket(5, market, ResourceType.ORE);
		assertEquals(1000 + 5 * orePrice, player.getMoney());
		assertEquals(10, player.getOre());
		assertEquals(5, market.getOre());

		int energyPrice = market.getBuyPrice(ResourceType.ENERGY);
		player.setMoney(1000);
		//sell 5 energy
		player.sellResourceToMarket(5, market, ResourceType.ENERGY);
		assertEquals(1000 + 5 * energyPrice, player.getMoney());
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
	public void testPlayerCannotSellMoreEnergyThanAllowed() throws Exception {
		Market market = new Market();

		player.setEnergy(15);

		exception.expect(NotEnoughResourceException.class);
		player.sellResourceToMarket(20, market, ResourceType.ENERGY);
	}

	@Test
	public void testPlayerCannotSellMoreOreThanAllowed() throws Exception {
		Market market = new Market();

		player.setOre(15);

		exception.expect(NotEnoughResourceException.class);
		player.sellResourceToMarket(20, market, ResourceType.ORE);
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