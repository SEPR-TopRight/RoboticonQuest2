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
	buy resource start here
    */


	public boolean buyFood(player, int amount) {
		int foodPrice = 20;	// this 20 need to be change when write the method setFoodPrice
		double price = amount *  foodPrice;
		int playerMoney = player.getMoney();
		int playerFood = player.getFood();

		if (playerMoney >= price ){
			player.setMoney(playerMoney-price);
			player.setFood(playerFood+amount);
			return true;
		}
		else{
			return false;
		}
	}

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

	public boolean buyOre(player, int amount) {
		int OrePrice = 20;	// this 20 need to be change when write the method setOrePrice
		double price = amount *  OrePrice;
		int playerMoney = player.getMoney();
		int playerOre = player.getOre();

		if (playerMoney >= price ){
			player.setMoney(playerMoney-price);
			player.setOre(playerOre+amount);
			return true;
		}
		else{
			return false;
		}
	}



	public boolean buyRobotics(player, int amount) {
		int RoboticsPrice = 20;	// this 20 need to be change when write the method setRoboticsPrice
		double price = amount *  RoboticsPrice;
		int playerMoney = player.getMoney();
		int playerRobotics = player.getRobotics();

		if (playerMoney >= price ){
			player.setMoney(playerMoney-price);
			player.setRobotics(playerRobotics+amount);
			return true;
		}
		else{
			return false;
		}
	}




	/**
	sell resource start here
    */


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

	public boolean sellEnergy(player, int amount) {
		int EnergyPrice = 20;	// this 20 need to be change when write the method setEnergyPrice
		double price = amount *  EnergyPrice;
		int playerMoney = player.getMoney();
		int playerEnergy = player.getEnergy();

		if (playerEnergy >= amount ){
			player.setMoney(playerMoney+price);
			player.setEnergy(playerEnergy-amount);
			return true;
		}
		else{
			return false;
		}
	}

	public boolean sellOre(player, int amount) {
		int OrePrice = 20;	// this 20 need to be change when write the method setOrePrice
		double price = amount *  OrePrice;
		int playerMoney = player.getMoney();
		int playerOre = player.getOre();

		if (playerOre >= amount ){
			player.setMoney(playerMoney+price);
			player.setOre(playerOre-amount);
			return true;
		}
		else{
			return false;
		}
	}
	public boolean sellRobotics(player, int amount) {
		int RoboticsPrice = 20;    // this 20 need to be change when write the method setRoboticsPrice
		double price = amount * RoboticsPrice;
		int playerMoney = player.getMoney();
		int playerRobotics = player.getRobotics();

		if (playerRobotics >= amount) {
			player.setMoney(playerMoney + price);
			player.setRobotics(playerRobotics - amount);
			return true;
		} else {
			return false;
		}
	}


	/**	public synchronized boolean sellToMarket(ITrade player, int amount) {
	 int playercurrAmount = player.getResource(resourceType);
	 int currAmount = market.getResource(resourceType);

	 // Check if value is valid.
	 if (amount > 0 && amount <= playercurrAmount) {
	 double price = getSellPrice() * amount;

	 // Check if the player can afford
	 if (player.addMoney(price)) {
	 market.setResource(resourceType, currAmount + amount);
	 player.setResource(resourceType, player.getResource(resourceType) - amount);
	 return true;
	 }
	 }
	 return false;
	 }
	 }
	 */



	public int getBuyPrice(ResourceType ore) {
		return 10;
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
