package io.github.teamfractal.exception;

import io.github.teamfractal.entity.enums.ResourceType;

public class InvalidResourceTypeException extends RuntimeException {
	/**
	 * Constructs an <code>NotEnoughMoneyException</code> with no
	 * detail message.
	 */
	public InvalidResourceTypeException() {
		super();
	}

	public InvalidResourceTypeException(ResourceType type) {
		super("Invalid ResourceType(got ResourceType." + type.toString() + ")");
	}

	public InvalidResourceTypeException(ResourceType requiredType, ResourceType actualType) {
		super("Invalid Resource Type: \n" +
				"Required: " + requiredType.toString() + "\n" +
				"Actual  : " + actualType.toString());
	}

	public InvalidResourceTypeException(String details, ResourceType requiredType, ResourceType actualType) {
		super("Invalid Resource Type: \n" +
				"Required: " + requiredType.toString() + "\n" +
				"Actual  : " + actualType.toString() + "\n" +
				"Details : " + details);
	}
}
