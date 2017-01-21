package io.github.teamfractal.util;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;

public class PlotManager {
	private LandPlot[][] plots;
	private final RoboticonQuest game;
	private TiledMapTileLayer mapLayer;
	private TiledMapTileLayer playerOverlay;
	private TiledMapTileLayer roboticonOverlay;
	private int width;
	private int height;

	public PlotManager(RoboticonQuest game) {
		this.game = game;
	}

	public void setup(MapLayers layers) {
		this.mapLayer = (TiledMapTileLayer)layers.get("MapData");
		this.playerOverlay = (TiledMapTileLayer)layers.get("PlayerOverlay");
		this.roboticonOverlay = (TiledMapTileLayer)layers.get("RoboticonOverlay");

		width = mapLayer.getWidth();
		height = mapLayer.getHeight();
		plots = new LandPlot[width][height];
	}

	public LandPlot getPlot(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return null;

		LandPlot p = plots[x][y];
		if (p == null) {
			p = new LandPlot(0, 0, 0);
			p.setupTile(this, x, y);
			plots[x][y] = p;
		}

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