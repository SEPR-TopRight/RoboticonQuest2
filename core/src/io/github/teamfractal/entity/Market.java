package io.github.teamfractal.entity;

public class Market {

	int getFood() {
		return 16;
	}

	public int getEnergy() {
		return 16;
	}

	public int getOre() {
		return 0;
	}

	public int getRoboticon() {
		return 12;
	}

	public int getBuyPrice(ResourceType ore) {
		return 10;
	}
}