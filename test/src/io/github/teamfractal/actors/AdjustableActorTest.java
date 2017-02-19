package io.github.teamfractal.actors;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import io.github.teamfractal.actors.AdjustableActor;

public class AdjustableActorTest {
	
	private Random rand = new Random();

	private Skin skin;
	private String title, action;
	private int value, min, max;

	private AdjustableActor actor;

	@Test
	public void mainTest() {
		// Ugly hack due to failure to change Gradle testing working dir
		skin = new Skin(Gdx.files.internal("../core/assets/skin/neon-ui.json"),
				new TextureAtlas(Gdx.files.internal("../core/assets/skin/neon-ui.atlas")));
		title = "test";
		action = "action";
		value = 0;
		min = 0;
		max = 9;
		actor = new AdjustableActor(skin, title, action);
		verifyMembers();
		
		title = "New title";
		newMinMaxValue();
		actor.setTitle(title);
		actor.setMin(min);
		actor.setMax(max);
		actor.setValue(value);
		verifyMembers();
		
		title = "Third title";
		action = "New action";
		newMinMaxValue();
		actor = new AdjustableActor(skin, value, min, max, title, action);
		verifyMembers();
	}

	private void verifyMembers() {
		assertEquals(actor.getTitle(), title);
		assertEquals(actor.getValue(), value);
		assertEquals(actor.getMin(), min);
		assertEquals(actor.getMax(), max);
	}
	
	private void newMinMaxValue() {
		min = rand.nextInt(100);
		ensureValueInRange();
		max = rand.nextInt(100);
		ensureValueInRange();
		value = rand.nextInt(100);
		ensureValueInRange();
	}
	
	// This is a copy and paste of the function while it is working as intended
	// Future changes will be tested against it
	private void ensureValueInRange() {
		if (min > max) {
			// Swap min and max range.
			int temp_min = min;
			min = max;
			max = temp_min;
		}

		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
	}

}
