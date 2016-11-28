package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import org.junit.*;

public class LandPlotTest {
	private LandPlot plot;
	
	@Before
	public void setup() {
		plot = new LandPlot();
	}
	
	@Test
	public void testInstallRobiticon() {
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(plot.productionModifiers, new int[] {1, 0, 0});
		plot.installRoboticon(ResourceType.ENERGY);
		assertArrayEquals(plot.productionModifiers, new int[] {1, 1, 0});
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(plot.productionModifiers, new int[] {2, 1, 0});
	}
	
	@Test
	public void testProduceResources() {
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(plot.produceResources(), new int[] {3, 0, 0});
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(plot.produceResources(), new int[] {6, 0, 0});
	}

}