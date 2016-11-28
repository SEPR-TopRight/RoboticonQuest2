package io.github.teamfractal.entity;

public class Player {
	private int money = 100;
	private int ore = 0;
	private int energy = 0;
	
	public int getMoney(){
		return money;
	}
	
	public int getOre(){
		return ore;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void purchaseResourceFromMarket(int amount, Market market, ResourceType resource) throws Exception {
		if (money >= amount * market.getResourcePrice(resource)) {
			money -= amount * market.getResourcePrice(resource);
			
			switch(resource) {
			case ORE: 		ore += amount;
					  		break;
			case ENERGY: 	energy += amount;
						 	break;
			default: 		throw new Exception("Error: Resource specified is not a resource");
					 		
			}
		}
		else {
			throw new Exception("Error: Not enough money");
		}
		
	}

	public void sellResourceToMarket(int amount, Market market, ResourceType resource) throws Exception {
		
		int resourcePrice = market.getResourcePrice(resource);
		
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
	
}