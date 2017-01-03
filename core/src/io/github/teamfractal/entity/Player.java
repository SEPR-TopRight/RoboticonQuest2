package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.PurchaseStatus;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.exception.InvalidResourceTypeException;
import io.github.teamfractal.exception.NotEnoughResourceException;

import java.util.ArrayList;

public class Player {
	//<editor-fold desc="Resource getter and setter">
	private int money = 100;
	private int ore = 0;
	private int energy = 0;
	private int food = 0;
	ArrayList<Roboticon> roboticonList;

	int getMoney() { return money; }
	int getOre() { return ore; }
	int getEnergy() { return energy; }
	int getFood() { return food; }

	/**
	 * Set the amount of money player has
	 * @param money                      The amount of new money.
	 * @throws IllegalArgumentException  If the new money if negative, this exception will be thrown.
	 */
	synchronized void setMoney(int money) throws IllegalArgumentException {
		if (money < 0) {
			throw new IllegalArgumentException("Error: Money can't be negative.");
		}

		this.money = money;
	}

	/**
	 * Set the amount of ore player has
	 * @param amount                     The new amount for ore.
	 * @throws IllegalArgumentException  If the new ore amount if negative, this exception will be thrown.
	 */
	synchronized void setOre(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Error: Ore can't be negative.");
		}

		this.ore = amount;
	}

	/**
	 * Set the amount of energy player has
	 * @param amount                     The new amount for energy.
	 * @throws IllegalArgumentException  If the new energy amount if negative, this exception will be thrown.
	 */

	synchronized void setEnergy(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Error: Energy can't be negative.");
		}

		this.energy = amount;
	}

	/**
	 * Set the amount of food player has
	 * @param amount                     The new amount for food.
	 * @throws IllegalArgumentException  If the new food amount if negative, this exception will be thrown.
	 */

	synchronized void setFood(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Error: Food can't be negative.");
		}

		this.food = amount;
	}

	/**
	 * Set the resource amount current player have.
	 * @param type   The {@link ResourceType}
	 * @param amount The new amount.
	 */
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
				throw new InvalidResourceTypeException(type);
		}
	}

	/**
	 * Get the resource amount current player have.
	 * @param type   The {@link ResourceType}
	 * @return       The amount of specified resource.
	 */
	int getResource(ResourceType type) {
		switch (type) {
			case ENERGY:
				return getEnergy();

			case ORE:
				return getOre();

			case FOOD:
				return getFood();


			default:
				throw new InvalidResourceTypeException(type);
		}
	}
	//</editor-fold>

	/**
	 * Action for player to purchase resources from the market.
	 *
	 * @param amount     Amount of resources to purchase.
	 * @param market     The market instance.
	 * @param resource   The resource type.
	 * @return           If the purchase was success or not.
	 */
	public PurchaseStatus purchaseResourceFromMarket(int amount, Market market, ResourceType resource) {
		if (!market.hasEnoughResources(resource, amount)) {
			return PurchaseStatus.FailMarketNotEnoughResource;
		}

		int cost = amount * market.getSellPrice(resource);
		int money = getMoney();
		if (cost > money) {
			return PurchaseStatus.FailPlayerNotEnoughMoney;
		}

		market.sellResource(resource, amount);
		setMoney(money - cost);
		setResource(resource, getResource(resource) + amount);
		return PurchaseStatus.Success;
	}

	/**
	 * Action for player to sell resources to the market.
	 *
	 * @param amount    Amount of resources to sell.
	 * @param market    The market instance.
	 * @param resource  The resource type.
	 */
	public void sellResourceToMarket(int amount, Market market, ResourceType resource) {
		int resourcePrice = market.getBuyPrice(resource);

		if (getResource(resource) >= amount) {
			market.buyResource(resource, amount);
			setResource(resource, getResource(resource) - amount);
			setMoney(getMoney() + amount * resourcePrice);
		} else {
			throw new NotEnoughResourceException("Player.sellResourceToMarket", resource, amount, getResource(resource));
		}
	}

	/**
	 * Apply roboticon customisation
	 * @param roboticon  The roboticon to be customised
	 * @param type       The roboticon customisation type.
	 * @return           The roboticon
	 */
	public Roboticon customiseRoboticon(Roboticon roboticon, ResourceType type) {
		roboticon.setCustomisation(type);
		return roboticon;
	}
}