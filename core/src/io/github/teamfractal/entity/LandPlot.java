package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.exception.InvalidResourceTypeException;
import io.github.teamfractal.exception.NotCommonResourceException;

public class LandPlot {
	private final int IndexOre = 0;
	private final int IndexEnergy = 1;
	private final int IndexFood = 2;

	/**
	 * Saved modifiers for LandPlot.
	 * [ Ore, Energy, Food ]
	 */
	int[] productionModifiers = {0, 0, 0};

	/**
	 * The base production amounts.
	 * [ Ore, Energy, Food ]
	 */
	int[] productionAmounts = {0, 0, 0};
	private boolean owned;

	/**
	 * Initialise LandPlot with specific base amount of resources.
	 *
	 * @param ore     Amount of ore
	 * @param energy  Amount of energy
	 * @param food    Amount of food
	 */
	public LandPlot(int ore, int energy, int food) {
		this.productionAmounts = new int[]{ore, energy, food};
		this.owned = false;
	}

	/**
	 * Get the type index from the {@link ResourceType}
	 * @param resource   The {@link ResourceType}
	 * @return           The index.
	 * @throws InvalidResourceTypeException Exception is thrown if the resource index is invalid.
	 */
	private int resourceTypeToIndex(ResourceType resource) {
		switch (resource) {
			case ORE:    return IndexOre;
			case FOOD:   return IndexFood;
			case ENERGY: return IndexEnergy;
		}

		throw new NotCommonResourceException(resource);
	}

	/**
	 * Install a roboticon to this LandPlot.
	 *
	 * @param roboticon    The roboticon to be installed.
	 */
	public synchronized boolean installRoboticon(Roboticon roboticon) {
		// Check if supplied roboticon is already installed.
		if (roboticon.isInstalled()) {
			return false;
		}

		int index = resourceTypeToIndex(roboticon.getCustomisation());
		if (roboticon.setInstalledLandplot(this)) {
			productionModifiers[index] += 1;
			return true;
		}

		return false;
	}

	/**
	 * Calculate the amount of resources to be produced.
	 *
	 * @return The amount of resources to be produced in an 2D array.
	 */
	public int[] produceResources() {
		int[] produced = new int[3];
		for (int i = 0; i < 3; i++) {
			produced[i] = productionAmounts[i] * productionModifiers[i];
		}
		return produced;
	}
	public boolean isOwned(){
		return this.owned;
	}
	public void setOwned(boolean owned){
		this.owned = owned;
	}
}