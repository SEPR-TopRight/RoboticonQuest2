package io.github.teamfractal.entities;

public class Market {
	private int food;
	private int energy;
	private int ore;
	private int robotics;
	private float sellRate;
	
	private float foodRate;
	private float foodConstran;
	private float foodMinPrice;
	
	private int robConstran;
	private int robMinPrice;
	private int robRate;
	
	private static Market instance;
	static Market getInstance() {
		if (instance == null)
			instance = new Market();
		
		return instance;
	}
	
	
	
	public Market() {
		food = 16;
		energy = 16;
		ore = 0;
		robotics = 12;
		
		sellRate = 1.1f;
		
		foodMinPrice = 10;
		foodConstran = 100;
		foodRate = 20;
		
		robMinPrice = 10;
		robConstran = 100;
		robRate = 20;
	}

	/**
	 * Food buy in price
	 * Range from { foodMinPrice } to { foodMinPrice +  foodRate }
	 * @return The Price
	 */
	float getFoodBuyPrice() {
		if (food >= foodConstran) {
			return foodMinPrice;
		}
		
		return foodMinPrice + (1 - (float)food / foodConstran) * foodRate;
	}
	
	float getFoodSellPrice() {
		return getFoodBuyPrice() * sellRate;
	}

	/**
	 * Robotics buy in price.
	 * @return The price for the robotic
	 */
	float getRobBuyPrice() {
		if (robotics >= robConstran) {
			return robMinPrice;
		}
		
		return robMinPrice + (1 - (float)robotics / robConstran) * robRate;
	}
	
	float getRobSellPrice() {
		return getRobBuyPrice() * sellRate;
	}


	public synchronized Boolean buyRoboticon(Player player, int amount) {
		if (amount <= 0 || amount > robotics) {
			return false;
		}
		
		float price = amount * getRobBuyPrice();
		if (player.costGold(price)) {
			robotics -= amount;
			return true;
		}
		
		return false;
	}
}
