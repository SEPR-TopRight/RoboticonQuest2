package io.github.teamfractal.entity;

public class LandPlot {
	int[] productionModifiers = {0, 0, 0};
	int[] productionAmounts;
	
	public LandPlot(int ore, int energy, int food) {
		this.productionAmounts = new int[] {ore, energy, food};
	}
	
	public void installRoboticon(Roboticon roboticon) throws Exception {
		switch(roboticon.getCustomisation()) {
		case ORE:    productionModifiers[0] += 1;
		             break;
		case ENERGY: productionModifiers[1] += 1;
		             break;
		default:     throw new Exception("Unknown resource type");
		}
	}

	public int[] produceResources() {
		int[] produced = new int[3];
		for(int i = 0; i < 3; i++) {
			produced[i] = productionAmounts[i] * productionModifiers[i];
		}
		return produced;
	}
}