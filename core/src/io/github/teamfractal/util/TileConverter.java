package io.github.teamfractal.util;


import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Player;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.entity.enums.RoboticonType;

public class TileConverter {
	private static TiledMapTileSets tiles;
	private static RoboticonQuest game;

	public static void setup(TiledMapTileSets tiles, RoboticonQuest game) {
		TileConverter.tiles = tiles;
		TileConverter.game = game;
	}

	public static TiledMapTile getPlayerTile(Player player) {
		int playerIndex = game.getPlayerIndex(player);
		return tiles.getTile(68 + playerIndex);
	}

	public static TiledMapTile getRoboticonTile(RoboticonType resource) {
		int resourceIndex = 0;

		switch(resource) {
			case ENERGY:
				resourceIndex = 2;
				break;

			case ORE:
				resourceIndex = 3;
				break;

			case NO_CUST:
				resourceIndex = 1;
				break;

			case NONE:
				resourceIndex = 0;
				break;
		}

		return tiles.getTile(100 + resourceIndex);
	}

	public static TiledMapTile getRoboticonTile(ResourceType resource) {
		System.out.println("WARN: getRoboticonTile(ResourceType) is old! Use getRoboticonTile(RoboticonType) instead.");

		RoboticonType rt = RoboticonType.NONE;

		switch(resource) {
			case ENERGY:
				rt = RoboticonType.ENERGY;
				break;

			case ORE:
				rt = RoboticonType.ORE;
				break;

			case FOOD:
				break;

			case ROBOTICON:
				rt = RoboticonType.NO_CUST;
				break;

			case CUSTOMISATION:
				break;

			case Unknown:
				rt = RoboticonType.NO_CUST;
				break;
		}

		return getRoboticonTile(rt);
	}
}
