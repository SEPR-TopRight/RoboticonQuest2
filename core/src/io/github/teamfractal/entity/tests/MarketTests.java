package io.github.teamfractal.entity.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.resource.*;

public class MarketTests {
	
	@Test
	public void testRoboticonProduction() {
		// setup
		Market market = new Market();
		int oreBefore = market.getOre();
		int roboticonBefore = market.getRoboticons();
		// Action
		market.generateRoboticon();
		// Tests
		assertEquals(oreBefore - 2, market.getOre());
		assertEquals(roboticonBefore + 1, market.getRoboticons());
	}
	
}
