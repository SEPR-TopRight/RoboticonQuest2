package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.Robotic;

import java.util.ArrayList;

public class PlayerTest {
	@Test
	public void testPlayerCanCustomiseRoboticon() {
		// Setup
		Player player = new Player();
		Robotic roboticon = new Robotic();
		String type = "Ore";
		// Action
		player.customiseRoboticon(roboticon, type);
		// Test
		assertEquals("Ore", roboticon.getType());
	}
	
	@Test
	public void testPlayerCanBuyOre() {
		// Setup
		Player player = new Player();
		Market market = new Market();
		player.addMoney(100000);
		market.setOre(10);
		int playerStartingOre = player.getOre();
		double playerStartingMoney = player.getMoney();
		int marketStartingOre = market.getOre();
		double orePrice = market.getOrePrice();
		// Action
		market.buyOre(player, 5);
		// Tests
		assertEquals(5, player.getOre() - playerStartingOre);
		assertEquals(playerStartingMoney - (5 * orePrice), player.getMoney(), 0);
		
		assertEquals(marketStartingOre - 5, market.getOre());
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
