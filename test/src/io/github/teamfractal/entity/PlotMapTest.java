package io.github.teamfractal.entity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.Game;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import io.github.teamfractal.GdxInitializer;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.util.PlotManager;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class PlotMapTest extends GdxInitializer {
	private PlotManager plotMap;
	//LwjglApplication app;

	@Before
	public void setUp() {
		TiledMap tmx = new TmxMapLoader().load("tiles/city.tmx");
		plotMap = new PlotManager();
		plotMap.setup(tmx.getTileSets(), tmx.getLayers());
	}
	
	@Test
	public void setUpTest() {
		assertEquals(plotMap.getPlot(0,0).hasOwner(), false);
		assertEquals(plotMap.getPlot(0,0).getResource(ResourceType.ORE), 2);
		assertEquals(plotMap.getPlot(0,0).getResource(ResourceType.ENERGY), 2);
		assertEquals(plotMap.getPlot(0,0).getResource(ResourceType.FOOD), 2);
	}
	
}
