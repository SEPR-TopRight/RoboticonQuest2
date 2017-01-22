package io.github.teamfractal.util;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;

public class PlotManager {
	private LandPlot[][] plots;
	private final RoboticonQuest game;
	private TiledMapTileSets tiles;
	private TiledMapTileLayer mapLayer;
	private TiledMapTileLayer playerOverlay;
	private TiledMapTileLayer roboticonOverlay;
	private int width;
	private int height;
	private TiledMapTile cityTile;
	private TiledMapTile waterTile;
	private TiledMapTile forestTile;
	private TiledMapTile hillTile1;
	private TiledMapTile hillTile2;
	private TiledMapTile hillTile3;
	private TiledMapTile hillTile4;

	public PlotManager(RoboticonQuest game) {
		this.game = game;
	}

	public void setup(TiledMapTileSets tiles, MapLayers layers) {
		this.tiles = tiles;
		this.mapLayer = (TiledMapTileLayer)layers.get("MapData");
		this.playerOverlay = (TiledMapTileLayer)layers.get("PlayerOverlay");
		this.roboticonOverlay = (TiledMapTileLayer)layers.get("RoboticonOverlay");

		this.cityTile = tiles.getTile(60);
		this.waterTile = tiles.getTile(9);
		this.forestTile = tiles.getTile(61);
		this.hillTile1 = tiles.getTile(3);
		this.hillTile2 = tiles.getTile(4);
		this.hillTile2 = tiles.getTile(5);
		this.hillTile2 = tiles.getTile(6);

		width = mapLayer.getWidth();
		height = mapLayer.getHeight();
		plots = new LandPlot[width][height];
	}

	public LandPlot getPlot(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;

		LandPlot p = plots[x][y];
		if (p == null) {
			p = createLandPlot(x, y);
		}

		return p;
	}

	private LandPlot createLandPlot(int x, int y) {
		int ore, energy, food;
		TiledMapTile tile = mapLayer.getCell(x, y).getTile();

		if (tile == cityTile){
			ore = 1;
			energy = 2;
			food = 3;
		}
		else if (tile == forestTile){
			ore = 2;
			energy = 3;
			food = 1;
		}
		else if (tile == waterTile){
			ore = 3;
			energy = 1;
			food = 2;
		}
		else if (tile == hillTile1 || tile == hillTile2 ||tile == hillTile3 || tile == hillTile4 ){
			ore = 3;
			energy = 2;
			food = 1;
			
		}
		else{
			ore = 2;
			energy = 2;
			food = 2;
		}


		LandPlot p = new LandPlot(ore, energy, food);
		p.setupTile(this, x, y);
		plots[x][y] = p;
		return p;
	}

	public TiledMapTileLayer getMapLayer() {
		return mapLayer;
	}

	public TiledMapTileLayer getPlayerOverlay() {
		return playerOverlay;
	}

	public TiledMapTileLayer getRoboticonOverlay() {
		return roboticonOverlay;
	}
}