package io.github.teamfractal.entity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Market {
	Market() {
		setFood(16);
		setEnergy(16);
		setOre(0);
		setRoboticon(12);
	}


	int getResourceBuyPrice(ResourceType resource) {
		return 1;
	}
	
	int getResourceSellPrice(ResourceType resource) {
		return 1;
	}

	private int food;
	private int energy;
	private int ore;
	private int roboticon;

	int getFood() {
		return food;
	}

	int getEnergy() {
		return energy;
	}

	int getOre() {
		return ore;
	}

	int getRoboticon() {
		return roboticon;
	}

	void setFood(int amount) {
		food = amount;
	}

	void setEnergy(int amount) {
		energy = amount;
	}

	void setOre(int amount) {
		ore = amount;
	}

	void setRoboticon(int amount) {
		roboticon = amount;
	}

	public int getResource(ResourceType type) {
		switch (type) {
			case ORE:
				return getOre();

			case ENERGY:
				return getEnergy();

			case ROBOTICON:
				return getRoboticon();

			default:
				throw new IllegalArgumentException("Unknown Resource type used.");
		}
	}

	/**
	 * Method to ensure the market have enough resources for user to purchase.
	 * @param type    the resource type.
	 * @param amount  the amount of resource to check.
	 */
	void checkResourcesMoreThanAmount (ResourceType type, int amount) {
		int resource = getResource(type);

		if (amount > resource){
			throw new ValueException("Error: not enough resources in the market.");
		}
	}

	int getBuyPrice(ResourceType type) {
		int price;
		switch (type) {
			case ORE:
				price = 20;
				return price;

			case ENERGY:
				price = 30;
				return price;

			case FOOD:
				price = 40;
				return price;

			case ROBOTICON:
				price = 100;
				return price;

			default:
				throw new IllegalArgumentException("Error: Resource type is incorrect.");
		}
	}

	int getSellPrice(ResourceType type) {
		int price;
		switch (type) {
			case ORE:
				price = 10;
				return price;

			case ENERGY:
				price = 10;
				return price;

			case FOOD:
				price = 10;
				return price;

			case ROBOTICON:
				price = 10;
				return price;

			default:
				throw new IllegalArgumentException("Error: Resource type is incorrect.");

		}
	}

	public int newPrice(){
		return 0;
	}
}








