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

			case Unknown:
				return "<Unknown>";
		}

		return "<Unknown type: " + super.toString() + ">";
	}
}


