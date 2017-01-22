package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class AdjustableActor extends Table {
	private final TextButton subButton;
	private final TextButton addButton;
	private final TextButton actButton;
	private final Label valueLabel;
	private final Label titleLabel;

	//<editor-fold desc="Getter / Setter">
	private int value = 0;
	private int min   = 0;
	private int max   = 9;
	private ChangeListener actionEvent;

	public String getTitle() {
		return titleLabel.getText().toString();
	}

	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
		ensureValueInRange();
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
		ensureValueInRange();
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		ensureValueInRange();
	}

	public void setActionEvent(ChangeListener actionEvent) {
		this.actionEvent = actionEvent;
	}
	//</editor-fold>

	/**
	 * Ensure that the value is in the range of min and max.
	 */
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

		updateValueDisplay();
	}

	/**
	 * The adjustable actor
	 * For an easy way of adjust values in a step of 1 / -1.
	 *
	 * @param skin    The skin file for the UI.
	 * @param title   The adjustable title.
	 * @param action  The action button text.
	 */
	public AdjustableActor(Skin skin, String title, String action) {
		subButton = new TextButton("<", skin);
		addButton = new TextButton(">", skin);
		actButton = new TextButton(action, skin);
		valueLabel = new Label("0", skin);
		titleLabel = new Label(title, skin);
		bindButtonEvents();

		/*
		 *   +---------------+
		 *   |   [ title ]   |
		 *   +---------------+
		 *   | < | value | > |
		 *   +---------------+
		 *   |   [ button ]  |
		 *   +---------------+
		 */

		titleLabel.setAlignment(Align.center);
		valueLabel.setWidth(80);
		valueLabel.setAlignment(Align.center);
		subButton.padLeft(5).padRight(5);
		addButton.padLeft(5).padRight(5);

		add(titleLabel).colspan(3).fillX().spaceBottom(10);
		row();

		add(subButton).align(Align.left);
		add(valueLabel).fillX();
		add(addButton).align(Align.right);
		row();

		add(actButton).colspan(3).fillX().spaceTop(10);
		row();
	}

	/**
	 * The adjustable actor
	 * For an easy way of adjust values in a step of 1 / -1.
	 *
	 * @param skin    The skin file for the UI.
	 * @param value   The default value.
	 * @param min     The minimum value.
	 * @param max     The maximum value.
	 * @param title   The adjustable title.
	 * @param action  The action button text.
	 */
	public AdjustableActor(Skin skin, int value, int min, int max, String title, String action) {
		this(skin, title, action);

		setMax(max);
		setMin(min);
		setValue(value);
	}

	/**
	 * Binds events to buttons.
	 */
	private void bindButtonEvents() {
		actButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionEvent != null)
					actionEvent.changed(event, actor);
			}
		});

		subButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				value --;
				ensureValueInRange();
			}
		});

		addButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				value ++;
				ensureValueInRange();
			}
		});
	}

	/**
	 * Update label text to current value.
	 */
	private void updateValueDisplay() {
		valueLabel.setText(String.valueOf(value));
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();

		// TODO: manipulate actor size?
	}
}
