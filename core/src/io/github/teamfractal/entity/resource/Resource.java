package io.github.teamfractal.entity.resource;

import io.github.teamfractal.entity.Player;

public class Resource {
	private static double SellRate = 1.2;

	private ResourceType resourceType;
	private double rate;
	private double minUnitPrice;
	private double constrain;
	private ITrade market;
	
	public Resource(ITrade market, ResourceType resourceType, double rate,
					double minUnitPrice, double constrain) {

		this.market = market;
		this.resourceType = resourceType;
		this.rate = rate;
		this.minUnitPrice = minUnitPrice;
		this.constrain = constrain;
	}

	public double getPurchasePrice() {
		int currAmount = market.getResource(resourceType);
		if (currAmount >= constrain) {
			return minUnitPrice;
		}
		
		return minUnitPrice + (1 - (double)currAmount / constrain) * rate;
	}
	
	public double getSellPrice() {
		return getPurchasePrice() * SellRate;
	}
	
	/**
	 * Purchase resource from the market.
	 * @param player  The Player
	 * @param amount  The amount of resource.
	 * @return Boolean: Purchase success or not.
	 */
	public synchronized boolean buyFromMarket(ITrade player, int amount) {
		int currAmount = market.getResource(resourceType);
		
		// Check if value is valid.
		if (amount > 0 && amount <= currAmount) {
			double price = getPurchasePrice() * amount;
			
			// Check if the player can afford
			if (player.costMoney(price)) {
				market.setResource(resourceType, currAmount - amount);
				player.setResource(resourceType, player.getResource(resourceType) + amount);
				return true;
			}
		}
		return false;
	}
}
