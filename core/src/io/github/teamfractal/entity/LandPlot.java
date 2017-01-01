package io.github.teamfractal.entity;

public class LandPlot {
	private final int IndexOre = 0;
	private final int IndexEnergy = 1;
	private final int IndexFood = 2;

	/**
	 * Saved modifiers for LandPlot.
	 */
	int[] productionModifiers = {0, 0, 0};

	/**
	 * The base production amounts.
	 */
	int[] productionAmounts = {0, 0, 0};

	/**
	 * Initialise LandPlot with specific base amount of resources.
	 *
	 * @param ore     Amount of ore
	 * @param energy  Amount of energy
	 * @param food    Amount of food
	 */
	public LandPlot(int ore, int energy, int food) {
		this.productionAmounts = new int[]{ore, energy, food};
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

		switch (roboticon.getCustomisation()) {
			case ORE:
				productionModifiers[IndexOre] += 1;
				break;

			case ENERGY:
				productionModifiers[IndexEnergy] += 1;
				break;

			case FOOD:
				productionModifiers[IndexFood] += 1;
				break;

			default:
				throw new IllegalArgumentException("Unknown resource type");
		}

		roboticon.setInstalled(this);

		return true;
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
}