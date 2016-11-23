package io.github.teamfractal.entity.resource;

/**
 * Tradable interface.
 */
public interface ITrade {
	/**
	 * Get the count of specific resource.
	 * @param type     The ResourceType.
	 * @return         The amount of the resource type.
	 */
	int getResource(ResourceType type);

	/**
	 *
	 * @param type
	 * @param amount
	 */
	void setResource(ResourceType type, int amount);
	boolean costMoney(double amount);
	void addMoney(double amount);
}
