package io.github.teamfractal.entity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import io.github.teamfractal.GdxInitializer;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.util.PlotManager;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class PlotMapTest extends GdxInitializer {
	private PlotManager plotMap;
	LwjglApplication app;

	@Before
	public void setUp() {
		TiledMap tmx = new TmxMapLoader().load("tiles/city.tmx");
		plotMap = new PlotManager(new RoboticonQuest());
		plotMap.setup(tmx.getTileSets(), tmx.getLayers());
	}
	
	@Test
	public void setUpTest() {
		assertEquals(plotMap.getPlot(1,1).hasOwner(), false);
		assertArrayEquals(plotMap.getPlot(1,1).productionModifiers,new int[] {0,0,0});
	}
}
