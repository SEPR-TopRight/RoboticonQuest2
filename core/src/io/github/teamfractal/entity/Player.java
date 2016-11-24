package io.github.teamfractal.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.teamfractal.entity.resource.ITrade;
import io.github.teamfractal.entity.resource.ResourceType;

public class Player extends Sprite implements ITrade {
	private String name;
	private int food;
	private int energy;
	private int ore;

	public Player() {
		robotics = new ArrayList<Robotic>();
	}

	private double money;
	private List<LandPlot> lands;
	private List<Robotic> robotics;
	
	public boolean buyPlot(LandPlot plot) {
		return false;
	}

	public synchronized boolean haveMoney(double amount) {
		return amount > 0 && amount <= money;
	}

	void addRobotic(Robotic robotic) {
		robotics.add(robotic);
	}

	@Override
	public synchronized int getResource(ResourceType type) {
		switch (type) {
			case Energy:
				return energy;

			case Food:
				return food;

			case Ore:
				return ore;

			case Robotic:
				return robotics.size();

			default:
				return 0;
		}
	}

	@Override
	public void setResource(ResourceType type, int amount) {
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

			// Player can customise their robotics.
			// To sell specific robotic, use `Player.sellRobotic(index)`.
			case Robotic:
				synchronized (this) {
					int delta = robotics.size() - amount;

					// delta == 0: No change.
					// delta  < 0: Add new robotics to inventory.
					// delta  > 0: Remove robotics from inventory.

					if (delta == 0) break;

					if (delta < 0) {

						do {
							robotics.add(new Robotic());
							delta ++;
						} while (delta < 0);

					} else {
						// delta > 0

						do {
							robotics.remove(0);
							delta --;
						} while (delta > 0);
					}
				}
				break;

			default:
				break;
		}
	}

	@Override
	public synchronized boolean costMoney(double amount) {
		if (haveMoney(amount)) {
			money -= amount;
			return true;
		}

		return false;
	}

	@Override
	public synchronized void addMoney(double amount) {
		money += amount;
	}

	Robotic getRoboticAndRemove(int index) {
		if (index >= 0 && index < robotics.size()) {
			Robotic r = robotics.get(index);
			robotics.remove(index);
			return r;
		}

		return null;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getOre() {
		return ore;
	}

	public double getMoney() {
		return money;
	}
}
