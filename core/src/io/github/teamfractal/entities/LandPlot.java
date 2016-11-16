package io.github.teamfractal.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Jixun on 16/11/2016.
 */
public class LandPlot extends Sprite {
    private final static Texture landTexture;

    static {
        landTexture = new Texture("land.png");
    }

    public LandPlot() {
        super();
    }
}
