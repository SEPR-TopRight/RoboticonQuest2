package io.github.teamfractal.entity;

import org.junit.*;

import static org.junit.Assert.*;

public class MarketTest {
	private Market market;

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
		assertEquals(12, market.getRobotics());
	}
}
