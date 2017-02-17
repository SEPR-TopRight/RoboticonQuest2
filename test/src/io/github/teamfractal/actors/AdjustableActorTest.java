package io.github.teamfractal.actors;

import org.junit.Test;
import static org.junit.Assert.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import io.github.teamfractal.actors.AdjustableActor;

public class AdjustableActorTest {
	
	private Skin skin;
	private String title, action;
	private int value, min, max;
	
	private AdjustableActor actor;
	
	@Test
	public void mainTest() {
		skin = new Skin(Gdx.files.internal("skin/neon-ui.json"),new TextureAtlas(Gdx.files.internal("skin/neon-ui.atlas")));
		title = "test";
		action = "action";
		value = 0;
		min = 0;
		max = 9;
		actor = new AdjustableActor(skin, title, action);
		verifyMembers();
	}
	
	private void verifyMembers() {
				assertEquals(actor.getTitle(), title);
				assertEquals(actor.getValue(), value);
				assertEquals(actor.getMin(), min);
				assertEquals(actor.getMax(), max);
	}

}
