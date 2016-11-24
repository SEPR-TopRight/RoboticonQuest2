package io.github.teamfractal.entity.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.teamfractal.entity.Market;
import io.github.teamfractal.entity.resource.*;

public class MarketTests {
	
	@Test
	public void testRoboticonProduction() {
		int oreBefore = market.getOre();
		int roboticonBefore = market.getRoboticons();
		market.generateRoboticon();
		int oreAfter = market.getOre();
		int roboticonAfter = market.getRoboticons();
		assertEquals(oreBefore - 2, oreAfter);
		assertEquals(roboticonBefore + 1, roboticonAfter);
	}
	
}
