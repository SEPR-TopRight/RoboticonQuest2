package io.github.teamfractal;

import java.util.Random;
import java.util.Scanner;

public class MinGame {
	
	public boolean WinGame(int number){

		
		int max = 3;
		int min = 1;
		Random rand = new Random();
		int value = rand.nextInt((max - min) + 1) + min;
		
		//here the enter number method have to change to 
		//   match the user interface method

		//System.out.println("Please enter number 1 to 3: ");
		//Scanner input = new Scanner(System.in);
		//int number = input.nextInt();
		
		//if win got gifts (can change)
		return number == value;
	}

	public int WinGameGift(boolean bIsWin){
		if (bIsWin){
			return 1000;
		}
		else {
			return 0;
		}

	}
	
}
