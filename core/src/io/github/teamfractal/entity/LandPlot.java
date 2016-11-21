package io.github.teamfractal.entity;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LandPlot extends Sprite {
    private final static Texture landTexture;
    private ArrayList<Robotic> robotics;
    private float foodRate;
    private float energyRate;
    private float oreRate;
    private Player owner;

    static {
        landTexture = new Texture("land.png");
        // TODO: Create land image.
    }

    public LandPlot() {
        super();
        
        foodRate = 1f;
        energyRate = 1f;
        oreRate = 1f;
    }
    
    public synchronized Boolean removeRobotic(int amount) {
    	if (amount < 0 || amount > robotics.size())
    		return false;
    	
    	// TODO: Fix this
    	robotics -= amount;
    	
    	return true;
    }
    

    public synchronized Boolean installRobotic(Player player, int amount) {
    	if (amount < 0)
    		return false;

    	// TODO: Fix this
    	robotics += amount;
    	
    	return true;
    }
    
}
