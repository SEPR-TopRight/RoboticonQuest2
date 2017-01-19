package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;

public class Roboticon {
	private int ID;
	private ResourceType customisation;
	private LandPlot installedLandplot;
	
	Roboticon(int ID) {
		this.ID = ID;
		customisation = ResourceType.Unknown;
	}
	
	public int getID () {
		return this.ID;
	}

	public ResourceType getCustomisation() {
		return this.customisation;
	}
	
	void setCustomisation(ResourceType type) {
		this.customisation = type;
	}

	synchronized boolean isInstalled() {
		return installedLandplot != null;
	}

	public synchronized boolean setInstalledLandplot(LandPlot landplot) {
		if (!isInstalled()) {
			installedLandplot = landplot;
			return true;
		}

		return false;
	}
}
