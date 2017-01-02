package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;

public class Roboticon {
	private ResourceType customisation;
	private LandPlot installedLandplot;
	
	public Roboticon() {
		
	}

	public ResourceType getCustomisation() {
		return this.customisation;
	}
	
	public void setCustomisation(ResourceType type) {
		this.customisation = type;
	}

	public boolean isInstalled() {
		return installedLandplot != null;
	}

	public void setInstalled(LandPlot landplot) {
		if (!isInstalled()) {
			installedLandplot = landplot;
		}
	}
}
