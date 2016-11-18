package io.github.teamfractal.entity.resource;

import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.teamfractal.entity.Player;

public class Resource {
	public ResourceType Type;
	public double Rate;
	public double Min;
	public double Constran;
	public Function<ResourceType, Integer> ResourceGetter;
	public BiFunction<ResourceType, Integer, Boolean> ResourceSetter;
	public static double SellRate = 1.2;
	
	public Resource(ResourceType type, double rate,
			double min, double constran,
			Function<ResourceType, Integer> resGetter,
			BiFunction<ResourceType, Integer, Boolean> resSetter) {
		
		Type = type;
		Rate = rate;
		Min = min;
		Constran = constran;
		ResourceGetter = resGetter;
		ResourceSetter = resSetter;
	}
	
	int getCurrAmount() {
		return ResourceGetter.apply(Type);
	}

	public double getBuyPrice() {
		int currAmount = getCurrAmount();
		if (currAmount >= Constran) {
			return Min;
		}
		
		return Min + (1 - (double)currAmount / Constran) * Rate;
	}
	
	public double getSellPrice() {
		return getBuyPrice() * SellRate;
	}
	
	public synchronized boolean buy(Player player, int amount) {
		int currAmount = getCurrAmount();
		if (amount < 0 || currAmount >= amount) {
			double price = getBuyPrice() * amount;
			if (player.costGold(price)) {
				ResourceSetter.apply(Type, currAmount + amount);
				return true;
			}
		}
		return false;
	}
}
