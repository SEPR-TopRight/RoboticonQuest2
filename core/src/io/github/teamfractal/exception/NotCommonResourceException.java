package io.github.teamfractal.exception;

import io.github.teamfractal.entity.enums.ResourceType;

public class NotCommonResourceException extends RuntimeException {
	public NotCommonResourceException(ResourceType actualType) {
		this(actualType, false);
	}

	public NotCommonResourceException(ResourceType actualType, boolean bIncludeRoboticon) {
		super("Invalid Resource Type: \n" +

				"Requires: Ore | Food | Energy"
					+ (bIncludeRoboticon ? " | Roboticon" : "") + "\n" +

				"Actual  : " + actualType.toString());
	}
}
