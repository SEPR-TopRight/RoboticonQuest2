package io.github.teamfractal.entity.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import io.github.teamfractal.entity.Market;
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
	
	@Test
	public void testPlayerCanBuyRoboticon() {
		// Setup
		Player player = new Player();
		Market market = new Market();
		int playerMoneyBefore = player.getMoney();
		int roboticonPrice = market.getRoboticonPrice();
		int marketRoboticonsBefore = market.getNumberRoboticons();
		ArrayList<Roboticon> roboticonList = player.getRoboticonList();
		// Action
		Roboticon newRoboticon = player.buyRoboticon();
		// Tests
		assertEquals(playerMoneyBefore - roboticonPrice, player.getMoney());
		assertEquals(marketRoboticonsBefore - 1, market.getNumberRoboticons);
		assertArrayEquals(roboticonList.append(newRoboticon), player.getRoboticonList()); // I'm not sure about this test
	}
	
}
