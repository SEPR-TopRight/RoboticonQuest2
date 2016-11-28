package io.github.teamfractal.entity;

public class Market {
	public int getResourcePrice(ResourceType resource) {
		return 1;
	}
	

	public int getFood() {
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



	/**
	 * 	buy resource start here
	 *
	 * @param amount   the number of resource you want to buy
	 */
	public void checkResourcesMoreThanAmount(ResourceType type, int amount) throws Exception{
		int resource;
		switch (type) {
			case ORE:
				 resource = getOre();
				break;
			case ENERGY:
				 resource = getEnergy();
				break;
			case ROBOTICON:
				 resource = getRoboticon();
				break;
			default:
				throw new Exception("Error: Resource type is incorrect.");
		}

		if (resource <= amount ){
			throw new Exception("Error: the resources in market is less than the amount you want to buy.");
		}

	}
/*      //buy resource
	public boolean buyEnergy(player, int amount) {
		int EnergyPrice = 20;	// this 20 need to be change when write the method setEnergyPrice
		double price = amount *  EnergyPrice;
		int playerMoney = player.getMoney();
		int playerEnergy = player.getEnergy();

		if (playerMoney >= price ){
			player.setMoney(playerMoney-price);
			player.setEnergy(playerEnergy+amount);
			return true;
		}
		else{
			return false;
		}
	}
*/



/*		//sell resource

	public boolean sellFood(player, int amount) {
		int foodPrice = 20;	// this 20 need to be change when write the method setFoodPrice
		double price = amount *  foodPrice;
		int playerMoney = player.getMoney();
		int playerFood = player.getFood();

		if (playerFood >= amount ){
			player.setMoney(playerMoney+price);
			player.setFood(playerFood-amount);
			return true;
		}
		else{
			return false;
		}
	}
*/





	public int getBuyPrice(ResourceType type) throws Exception {
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
				throw new Exception("Error: Resource type is incorrect.");

		}
	}


	public int getSellPrice(ResourceType type) throws Exception {
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
				throw new Exception("Error: Resource type is incorrect.");

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
