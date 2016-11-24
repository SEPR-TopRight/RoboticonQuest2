package io.github.teamfractal.entity.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.teamfractal.entity.Player;

public class PlayerTests {

	@Test
	public void testPlayerCanCustomiseRoboticon() {
		// Setup
		Player player = new Player();
		Roboticon roboticon = new Roboticon();
		String type = "Ore";
		// Action
		Player.customiseRoboticon(roboticon, type);
		// Test
		assertEquals("Ore", roboticon.getType());
	}
	
	@Test
	public void testPlayerCanBuyOre() {
		// Setup
		Player player = new Player();
		Market market = new Market();
		int playerStartingOre = player.getOre();
		float playerStartingMoney = player.getMoney();
		int marketStartingOre = market.getOreStock();
		float orePrice = market.getOrePrice();
		// Action
		player.buyOreFromMarket(5);
		// Tests
		assertEquals(5, player.getOre());
		assertEquals(playerStartingMoney - (5 * orePrice), player.getMoney());
		
		assertEquals(marketStartingOre - 5, market.getOreStock());
	}
	
}
