package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import org.junit.*;

public class LandPlotTest {
	private LandPlot plot;
	
	@Before
	public void setup() {
		plot = new LandPlot(3, 0, 0);
	}
	
	@Test
	public void testInstallRobiticon() {
		Roboticon roboticon = new Roboticon();
		roboticon.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon);
		assertArrayEquals(new int[] {1, 0, 0}, plot.productionModifiers);
		Roboticon roboticon2 = new Roboticon();
		roboticon2.setCustomisation(ResourceType.ENERGY);
		plot.installRoboticon(roboticon2);
		assertArrayEquals(new int[] {1, 1, 0}, plot.productionModifiers);
		Roboticon roboticon3= new Roboticon();
		roboticon3.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon3);
		assertArrayEquals(new int[] {2, 1, 0}, plot.productionModifiers);
	}
	
	/*@Test
	public void testProduceResources() {
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(new int[] {3, 0, 0}, plot.produceResources());
		plot.installRoboticon(ResourceType.ORE);
		assertArrayEquals(new int[] {6, 0, 0}, plot.produceResources());
	}*/

}