package io.github.teamfractal.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class PlotMapTest {
	private PlotMap plotMap;


	@Before
	public void setUp() {
		TiledMap tmx = new TmxMapLoader().load("tiles/city.tmx");
		plotMap = new PlotMap(tmx);
	}
	
	@Test
	public void setUpTest() {
		assertEquals(plotMap.getPlot(1,1).hasOwner(), false);
		assertEquals(plotMap.getPlot(1,1).productionModifiers,new int[] {0,0,0});
		
	}

}
