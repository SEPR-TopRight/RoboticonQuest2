package io.github.teamfractal.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {
	private String name;
	private int food;
	private int energy;
	private int ore;
	private int gold; // Money
	private ArrayList<LandPlot> lands;
	private int roboticons;
	
	public Boolean buyPlot(LandPlot plot) {
		return false;
	}
	
	public Boolean buyRoboticon(int amount) {
		if (amount < 0)
			return false;

		if (!Market.getInstance().buyRoboticon(amount)) {
			return false;
		}

		synchronized (this) {
			roboticons += amount;
		}
		
		return true;
	}
	
	public synchronized Boolean installRoboticon(LandPlot plot, int amount) {
		if (plot == null || amount <= 0 || amount > roboticons) {
			return false;
		}
		
		return plot.installRobotic(amount);
	}
}
