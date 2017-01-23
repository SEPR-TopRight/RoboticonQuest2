package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import org.junit.*;

import io.github.teamfractal.entity.enums.ResourceType;

public class RoboticonTest {
	private Roboticon roboticon;
	
	@Before
	public void setup() {
		roboticon = new Roboticon(1);
	}
	
	@Test
	public void initialisationTest(){
		assertEquals(roboticon.getCustomisation(), ResourceType.Unknown);
		assertFalse(roboticon.isInstalled());
	}
	
	@Test
	public void customisationTest(){
		roboticon.setCustomisation(ResourceType.ORE);
		assertEquals(roboticon.getCustomisation(), ResourceType.ORE);
	}
	
	@Test
	public void installationTest(){
		LandPlot plot = new LandPlot(0, 0, 0);
		plot.installRoboticon(roboticon);
		assertTrue(roboticon.isInstalled());
	}

	

}
