package io.github.teamfractal.entity;

import java.util.ArrayList;

public class LandPlot {
    private ArrayList<Robotic> robotics;
    private float foodRate;
    private float energyRate;
    private float oreRate;
    private Player owner;

    public LandPlot() {
        super();
        
        foodRate = 1f;
        energyRate = 1f;
        oreRate = 1f;
    }
    
    public synchronized Boolean removeRobotic(int index) {
    	if (index >= 0 && index < robotics.size())
    		return false;

        Robotic r = robotics.get(index);
        if (r == null) return false;

        owner.addRobotic(r);
        robotics.remove(index);

    	return true;
    }
    

    public synchronized Boolean installRobotic(Player player, int index) {
	    Robotic robotic = player.getRoboticAndRemove(index);
	    if (robotic != null) {
		    robotics.add(robotic);
		    return true;
	    }

	    return false;
    }
}
