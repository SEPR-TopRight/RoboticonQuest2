package io.github.teamfractal.entity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;

public class Player {
	private int money = 100;
	private int ore = 0;
	private int energy = 0;
	private int food = 0;
	ArrayList<Roboticon> roboticonList;
	
	int getMoney() { return money; }
	int getOre() { return ore; }
	int getEnergy() { return energy; }
	int getFood() { return food; }

	synchronized void setMoney(int money) {
		if (money < 0) {
			throw new IllegalArgumentException("Error: Money can't be negative.");
		}

		this.money = money;
	}

	synchronized void setOre(int ore) {
		if (ore < 0) {
			throw new IllegalArgumentException("Error: Ore can't be negative.");
		}

		this.ore = ore;
	}
	synchronized void setEnergy(int energy) {
		if (energy < 0) {
			throw new IllegalArgumentException("Error: Energy can't be negative.");
		}

		this.energy = energy;
	}
	synchronized void setFood(int food) {
		if (food < 0) {
			throw new IllegalArgumentException("Error: Food can't be negative.");
		}

		this.food = food;
	}

	void setResource(ResourceType type, int amount) {
		switch (type) {
			case ENERGY:
				setEnergy(amount);
				break;

			case ORE:
				setOre(amount);
				break;

			case FOOD:
				setFood(amount);
				break;

			default:
				throw new IllegalArgumentException("Invalid resource type.");
		}
	}

	int getResource(ResourceType type) {
		switch (type) {
			case ENERGY:
				return getEnergy();

			case ORE:
				return getOre();

			case FOOD:
				return getFood();


			default:
				throw new IllegalArgumentException("Invalid resource type.");
		}
	}

	/**
	 * Action for player to purchase resource from the market.
	 *
	 * @param amount     Amount of resource to purchase.
	 * @param market     The market instance.
	 * @param resource   The resource type.
	 */
	public void purchaseResourceFromMarket(int amount, Market market, ResourceType resource) {
		market.checkResourcesMoreThanAmount(resource, amount);

		int cost = amount * market.getBuyPrice(resource);
		int money = getMoney();
		if (money >= cost) {
			setMoney(money - cost);
			setResource(resource, getResource(resource) + amount);
			market.sellResource(resource, amount);
		}
		else {
			throw new ValueException("Error: Not enough money");
		}
		
	}

	public void sellResourceToMarket(int amount, Market market, ResourceType resource) throws Exception {
		int resourcePrice = market.getSellPrice(resource);
		
		switch(resource) {
		case ORE: 
			if (ore >= amount) {
				money += amount * resourcePrice;
				ore -= amount;
			} 
			else {
				throw new Exception("Error: Not enough ore available for sale");
			}
			break;
			
		case ENERGY:
			if (energy >= amount) {
				money += amount * resourcePrice;
				energy -= amount;
			}
			else {
				throw new Exception("Error: Not enough energy available for sale");
			}
			break;
		default: throw new Exception("Error: Resource specified is not a resource");
		
		}
		
	}
	
	public Roboticon customiseRoboticon(Roboticon roboticon, ResourceType type) {
		roboticon.setCustomisation(type);
		return roboticon;
	}
	
}