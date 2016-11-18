package io.github.teamfractal.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LandPlot extends Sprite {
    private final static Texture landTexture;
    private int robotics;
    private float foodRate;
    private float energyRate;
    private float oreRate;
    // private Point location;
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
    	if (amount < 0 || amount > robotics)
    		return false;
    	
    	robotics -= amount;
    	
    	return true;
    }
    

    public synchronized Boolean installRobotic(int amount) {
    	if (amount < 0)
    		return false;
    	
    	robotics += amount;
    	
    	return true;
    }
    
}
