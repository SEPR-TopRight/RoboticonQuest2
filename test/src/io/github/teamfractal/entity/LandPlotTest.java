package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import io.github.teamfractal.entity.enums.ResourceType;
import org.junit.*;

public class LandPlotTest {
	private LandPlot plot;
	
	@Before
	public void setup() {
		plot = new LandPlot(3, 0, 0);
	}
	
	@Test
	public void testInstallRobiticon() throws Exception {
		Roboticon roboticon = new Roboticon(0);

		roboticon.setCustomisation(ResourceType.ORE);
		plot = new LandPlot(3, 0, 0);
		assertTrue(plot.installRoboticon(roboticon));
		assertArrayEquals(new int[] {2, 1, 1}, plot.productionModifiers);

		Roboticon roboticon2 = new Roboticon(0);
		roboticon2.setCustomisation(ResourceType.ENERGY);
		plot = new LandPlot(3, 0, 0);
		assertTrue(plot.installRoboticon(roboticon2));
		assertArrayEquals(new int[] {1, 2, 1}, plot.productionModifiers);

		Roboticon roboticon3= new Roboticon(0);
		roboticon3.setCustomisation(ResourceType.ORE);
		plot = new LandPlot(3, 0, 0);
		assertTrue(plot.installRoboticon(roboticon3));
		assertArrayEquals(new int[] {2, 1, 1}, plot.productionModifiers);

		Roboticon roboticon4= new Roboticon(0);
		roboticon4.setCustomisation(ResourceType.FOOD);
		plot = new LandPlot(3, 0, 0);
		assertTrue(plot.installRoboticon(roboticon4));
		assertArrayEquals(new int[] {1, 1, 2}, plot.productionModifiers);
	}

	@Test
	public void landPlotShouldNotReinstallRoboticon () {
		Roboticon roboticon = new Roboticon(0);

		roboticon.setCustomisation(ResourceType.ORE);
		plot = new LandPlot(3, 0, 0);
		assertTrue(plot.installRoboticon(roboticon));
		assertArrayEquals(new int[] {2, 1, 1}, plot.productionModifiers);

		assertFalse(plot.installRoboticon(roboticon));
		assertArrayEquals(new int[] {2, 1, 1}, plot.productionModifiers);
	}
	
	@Test
	public void testProduceResources() throws Exception {
		Roboticon roboticon = new Roboticon(0);
		roboticon.setCustomisation(ResourceType.ORE);
		plot = new LandPlot(3, 0, 0);
		plot.installRoboticon(roboticon);
		assertArrayEquals(new int[] {6, 0, 0}, plot.produceResources()); //TODO
		Roboticon roboticon2 = new Roboticon(0);
		roboticon2.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon2);
		assertArrayEquals(new int[] {12, 0, 0}, plot.produceResources()); //TODO
	}

}