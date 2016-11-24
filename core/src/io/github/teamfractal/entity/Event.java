package io.github.teamfractal.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Event {
	 // 3 type of event and 3 none for nothing happen.
	String[] evenList = new String[]{"none", "none","none","drought","flood","earthquake"};

	// this function can random choose which even will happen
	public String whichEventHappen(){
	    int randomElementInEvenList = new Random().nextInt(evenList.length);
		return evenList[randomElementInEvenList];
	}
	
	
	
	// inside the round class have to use run the randomElementInEvenList 
	//		so every round will random choice one pot to have even happen.
	
	// need to write one new function to choose the land plot for happening even
	
}
