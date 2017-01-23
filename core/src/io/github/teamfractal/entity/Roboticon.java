package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;

public class Roboticon {
	private int ID;
	private ResourceType customisation;
	private LandPlot installedLandPlot;
	
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

	public synchronized boolean isInstalled() {
		return installedLandPlot != null;
	}
	/**
	 * 
	 * @param sets land plot which roboticon is installed to
	 * @return true if roboticon is installed, false if not
	 */
	public synchronized boolean setInstalledLandplot(LandPlot landplot) {
		if (!isInstalled()) {
			installedLandPlot = landplot;
			return true;
		}

		System.out.println("Already installed to LandPlot! Cancel.");
		return false;
	}
}
