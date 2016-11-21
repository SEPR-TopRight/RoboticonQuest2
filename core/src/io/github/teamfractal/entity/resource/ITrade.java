package io.github.teamfractal.entity.resource;

public interface ITrade {
	int getResource(ResourceType type);
	void setResource(ResourceType type, int amount);
	boolean costMoney(double amount);
	boolean addMoney(double amount);
}
