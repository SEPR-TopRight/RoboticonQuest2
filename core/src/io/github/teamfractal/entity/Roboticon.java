package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;

public class Roboticon {
	private ResourceType customisation;
	private LandPlot installedLandplot;
	
	Roboticon() {
		customisation = ResourceType.Unknown;
	}

	ResourceType getCustomisation() {
		return this.customisation;
	}
	
	void setCustomisation(ResourceType type) {
		this.customisation = type;
	}

	synchronized boolean isInstalled() {
		return installedLandplot != null;
	}

	synchronized boolean setInstalledLandplot(LandPlot landplot) {
		if (!isInstalled()) {
			installedLandplot = landplot;
			return true;
		}

		return false;
	}
}
