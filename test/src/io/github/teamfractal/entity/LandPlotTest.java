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
		assertTrue(plot.installRoboticon(roboticon));
		//assertArrayEquals(new int[] {1, 0, 0}, plot.productionModifiers); //TODO

		Roboticon roboticon2 = new Roboticon(0);
		roboticon2.setCustomisation(ResourceType.ENERGY);
		assertTrue(plot.installRoboticon(roboticon2));
		//assertArrayEquals(new int[] {1, 1, 0}, plot.productionModifiers); //TODO

		Roboticon roboticon3= new Roboticon(0);
		roboticon3.setCustomisation(ResourceType.ORE);
		assertTrue(plot.installRoboticon(roboticon3));
		//assertArrayEquals(new int[] {2, 1, 0}, plot.productionModifiers); //TODO

		Roboticon roboticon4= new Roboticon(0);
		roboticon4.setCustomisation(ResourceType.FOOD);
		assertTrue(plot.installRoboticon(roboticon4));
		//assertArrayEquals(new int[] {2, 1, 1}, plot.productionModifiers); //TODO
	}

	@Test
	public void landPlotShouldNotReinstallRoboticon () {
		Roboticon roboticon = new Roboticon(0);

		roboticon.setCustomisation(ResourceType.ORE);
		assertTrue(plot.installRoboticon(roboticon));
		//assertArrayEquals(new int[] {1, 0, 0}, plot.productionModifiers); //TODO

		assertFalse(plot.installRoboticon(roboticon));
		//assertArrayEquals(new int[] {1, 0, 0}, plot.productionModifiers); //TODO
	}
	
	@Test
	public void testProduceResources() throws Exception {
		Roboticon roboticon = new Roboticon(0);
		roboticon.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon);
		//assertArrayEquals(new int[] {3, 0, 0}, plot.produceResources()); //TODO
		Roboticon roboticon2 = new Roboticon(0);
		roboticon2.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon2);
		//assertArrayEquals(new int[] {6, 0, 0}, plot.produceResources()); //TODO
	}

}