package io.github.teamfractal.entity.enums;

import io.github.teamfractal.util.StringUtil;

/**
 * The possible Resource Type in the game.
 */
public enum ResourceType {
	ENERGY,
	ORE,
	FOOD,
	ROBOTICON,

	/**
	 *  Energy or Ore or Food or Roboticon.
	 *
	 *  Mainly for exception with {@link io.github.teamfractal.exception.InvalidResourceTypeException},
	 *  Should <i>not</i> be used for other purposes.
	 */
	CommonResource,
	/**
	 *  Energy or Ore or Food.
	 *
	 *  Mainly for exception with {@link io.github.teamfractal.exception.InvalidResourceTypeException},
	 *  Should <i>not</i> be used for other purposes.
	 */
	BasicResource,
	Unknown;

	public boolean isValidResource() {
		return this == ENERGY || this == ORE || this == FOOD || this == ROBOTICON;
	}

	@Override
	public String toString() {
		switch(this) {
			case ENERGY:
			case ORE:
			case FOOD:
			case ROBOTICON:
				return StringUtil.Capitalise(super.toString().toLowerCase());

			case BasicResource:
				return "<Common Resource: Energy | Ore | Food>";

			case CommonResource:
				return "<Common Resource: Energy | Ore | Food | Roboticon>";

			case Unknown:
				return "<Unknown>";
		}

		return "<Unknown type: " + super.toString() + ">";
	}
}


