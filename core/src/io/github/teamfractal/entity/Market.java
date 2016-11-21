package io.github.teamfractal.entity;

import java.util.ArrayList;
import java.util.function.Function;

import io.github.teamfractal.entity.resource.Resource;
import io.github.teamfractal.entity.resource.ResourceType;

// TODO: Other resource buy/sell methods.
// TODO: Other resource parameter.

public class Market {
	/**
	 * Current resources present at the market.
	 */
	private int food;
	private int energy;
	private int ore;
	private ArrayList<Robotic> robotics;

	/**
	 * Resource bounded to a class.
	 */
	private Resource foodResource;
	private Resource energyResource;
	private Resource oreResource;
	
	/**
	 * Private instance of Market
	 */
	private static Market instance;
	
	/**
	 * Get an instance of the Market.
	 * New instance will be created in case of non initialised.
	 * @return Market
	 */
	static Market getInstance() {
		if (instance == null)
			instance = new Market();
		
		return instance;
	}
	
	/**
	 * Init the market.
	 */
	public Market() {
		food = 16;
		energy = 16;
		ore = 0;
		robotics = new ArrayList<>();
		for(int i = 0; i < 12; i++){
			robotics.add(new Robotic());
		}
		
		
		
		foodResource = new Resource(ResourceType.Food, 20, 10, 100,
				this::getResource, this::setResource);
		
		energyResource = new Resource(ResourceType.Energy, 20, 10, 100,
				this::getResource, this::setResource);
		
		oreResource = new Resource(ResourceType.Ore, 20, 10, 100,
				this::getResource, this::setResource);
	}

	/**
	 * Callback function - Get resource count from instance by its type.
	 * @param type The resource type.
	 * @return The resource count, or 0 if invalid type.
	 */
	int getResource(ResourceType type) {
		switch (type) {
		case Energy:
			return energy;

		case Food:
			return food;
			
		case Ore:
			return ore;

		default:
			return 0;
		
		}
	}

	/**
	 * Callback function - Set resource amount from instance by its type.
	 * @param type The resource type.
	 * @return true (Return value does nothing)
	 */
	boolean setResource(ResourceType type, int amount) {
		switch (type) {
		case Energy:
			energy = amount;
			break;

		case Food:
			food = amount;
			break;
			
		case Ore:
			ore = amount;
			break;
		default:
			break;
		}
		
		return true;
	}

	/**
	 * Purchase robotics from the market.
	 * @param player  The Player.
	 * @param amount  How many of robotics to purchase.
	 * @return Boolean, purchase success or not.
	 */
	public synchronized boolean buyRoboticon(Player player, int amount) {

    	// TODO: Fix this
	}
	
	/**
	 * Purchase food from the market.
	 * @param player  The Player.
	 * @param amount  How many of food to purchase.
	 * @return Boolean, purchase success or not.
	 */
	public synchronized boolean buyFood(Player player, int amount) {
		return foodResource.buyFromMarket(player, amount);
	}

	/**
	 * Purchase energy from the market.
	 * @param player  The Player.
	 * @param amount  How many of energy to purchase.
	 * @return Boolean, purchase success or not.
	 */
	public synchronized boolean buyEnergy(Player player, int amount) {
		return energyResource.buyFromMarket(player, amount);
	}
	
	/**
	 * Purchase ore from the market.
	 * @param player  The Player.
	 * @param amount  How many of ores to purchase.
	 * @return Boolean, purchase success or not.
	 */
	public synchronized boolean buyOre(Player player, int amount) {
		return oreResource.buyFromMarket(player, amount);
	}
	
	public synchronized boolean sellRoboticon(Player player, int amount) {

    	// TODO: Fix this
	}
	
	public synchronized boolean sellFood(Player player, int amount) {
		return foodResource.sellToMarket(player, amount);
	}
	
	public synchronized boolean sellEnergy(Player player, int amount) {
		return energyResource.sellToMarket(player, amount);
	}
	
	public synchronized boolean sellOre(Player player, int amount) {
		return oreResource.sellToMarket(player, amount);
	}
	
}
