package io.github.teamfractal.entity;

import java.util.function.Function;

import io.github.teamfractal.entity.resource.Resource;
import io.github.teamfractal.entity.resource.ResourceType;

// TODO: Other resource buy/sell methods.
// TODO: Other resource parameter.

public class Market {
	private int food;
	private int energy;
	private int ore;
	private int robotics;
	
	private static Market instance;
	static Market getInstance() {
		if (instance == null)
			instance = new Market();
		
		return instance;
	}
	
	private Resource foodResource;
	private Resource energyResource;
	private Resource oreResource;
	private Resource roboticResource;
	
	public Market() {
		food = 16;
		energy = 16;
		ore = 0;
		robotics = 12;
		
		
		
		foodResource = new Resource(ResourceType.Food, 20, 10, 100,
				this::getResource, this::setResource);
		
		energyResource = new Resource(ResourceType.Energy, 20, 10, 100,
				this::getResource, this::setResource);
		
		oreResource = new Resource(ResourceType.Ore, 20, 10, 100,
				this::getResource, this::setResource);
		
		roboticResource = new Resource(ResourceType.Robotic, 20, 10, 100,
				this::getResource, this::setResource);
	}

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

	public boolean buyRoboticon(Player player, int amount) {
		if (amount <= 0) return false;
		
		
		
		return false;
	}
}
