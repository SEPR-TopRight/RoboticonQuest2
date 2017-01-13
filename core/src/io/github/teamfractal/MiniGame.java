package io.github.teamfractal;

import java.util.Random;
import java.util.Scanner;

public class MiniGame {
	private static final Random rand = new Random();
	public boolean WinGame(int guessedNumber){
		int max = 3;
		int min = 1;
		int generatedNumber = rand.nextInt((max - min) + 1) + min;

		return guessedNumber == generatedNumber;
	}

	public int getPrice(boolean bIsWin){
		if (bIsWin){
			return 1000;
		}
		else {
			return 0;
		}

	}
	
}
