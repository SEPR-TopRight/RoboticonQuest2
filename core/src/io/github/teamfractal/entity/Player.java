package io.github.teamfractal.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {
	private String name;
	private int food;
	private int energy;
	private int ore;
	
	private double gold; // Money
	private ArrayList<LandPlot> lands;
	private ArrayList<Robotic> roboticons;
	
	public Boolean buyPlot(LandPlot plot) {
		return false;
	}
	
	public Boolean buyRoboticon(int amount) {
		if (amount < 0)
			return false;

		if (!Market.getInstance().buyRoboticon(this, amount)) {
			return false;
		}

		for(int i = 0; i < amount; i++) {
			roboticons.add(new Robotic());
		}
		
		return true;
	}
	
	public synchronized Boolean installRoboticon(LandPlot plot, int amount) {
		if (plot == null || amount <= 0 || amount > roboticons.size()) {
			return false;
		}

    	// TODO: Fix this
		return plot.installRobotic(player, amount);
	}

	public synchronized boolean haveGold(double price) {
		return price > 0 && price <= gold;
	}
	
	public synchronized boolean costGold(double price) {
		if (haveGold(price)) {
			gold -= price;
			return true;
		}
		
		return false;
	}
}
