package io.github.teamfractal.entity;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class PlotMap {
	private TiledMap tmx;
	private Map<String, LandPlot> landPlots;

	
	public PlotMap(TiledMap tmx){
		this.tmx = tmx;
		TiledMapTileLayer tileLayer = (TiledMapTileLayer) tmx.getLayers().get(0);
		landPlots = new HashMap<String, LandPlot>();
		TiledMapTile city = tmx.getTileSets().getTile(60);
		TiledMapTile forest = tmx.getTileSets().getTile(61);
		TiledMapTile water = tmx.getTileSets().getTile(9);
		for (int x = 0; x < tileLayer.getWidth(); x++){
			for (int y = 0; y < tileLayer.getHeight(); y++){
				
				TiledMapTile tileType = tileLayer.getCell(x, y).getTile();
				if (tileType == city){
					landPlots.put(x +" "+ y,new LandPlot(1,2,3));
				}
				else if (tileType == forest){
					landPlots.put(x +" "+ y,new LandPlot(2,3,1));
				}
				else if (tileType == water){
					landPlots.put(x +" "+ y, new LandPlot(3,1,2));
				}
				else{
					landPlots.put(x +" "+ y, new LandPlot(2,2,2));
				}
			}
		}
	}
	public Map<String, LandPlot> getPlots(){
		return landPlots;
	}
	public LandPlot getPlot(int x,int y){
		return landPlots.get(x + " " + y);
	}
}
