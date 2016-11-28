package io.github.teamfractal.entity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class Market {
	public int getResourcePrice(ResourceType resource) {
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

	public int getResource(ResourceType type) throws IllegalArgumentException {
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
	 *
	 * @param amount   the amount of resource you want to buy
	 */
	public void checkResourcesMoreThanAmount(ResourceType type, int amount)
			throws IllegalArgumentException, ValueException {

		int resource = getResource(type);

		if (amount > resource){
			throw new ValueException("Error: not enough resources in the market.");
		}
	}

	int getBuyPrice(ResourceType type) throws IllegalArgumentException {
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

	int getSellPrice(ResourceType type) throws IllegalArgumentException {
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
}







/**
 *
 * This is for player class

	int money;
	int ore;
	int robotics;
	int food;
	int energy;

	public int getMoney(){
		return money;
	}

	public int getFood(){
		return food;
	}

	public int getOre(){
		return ore;
	}

	public int getEnergy(){
		return energy;
	}

	public int getRobotics(){
		return robotics;
	}



 */
