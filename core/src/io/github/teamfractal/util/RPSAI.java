package io.github.teamfractal.util;

import java.util.Random;

public class RPSAI {

	public static enum moves {
		ROCK, PAPER, SCISSORS
	};

	public static enum results {
		TIE, WIN, LOSE
	};

	private moves humanMove = null;
	private moves AIMove = null;

	private static Random rand = new Random();

	// TODO: Do we need a constructor? Should this whole class be static?
	public RPSAI() {
		humanMove = null;
		AIMove = null;
	}

	public void setHumanMove(moves humanMove) {
		this.humanMove = humanMove;
		AIMove = moves.values()[rand.nextInt(3)];
	}

	public moves getHumanMove() {
		return humanMove;
	}

	public moves getAIMove() {
		return AIMove;
	}

	public results getResult() {
		/*************************************************************************
		 * Explanation of logic:
		 *
		 * <rock, paper, scissors> are designated <0, 1, 2> Let player choice be
		 * n and AI choice be m, and results be from human perspective
		 *
		 * All arithmetic is modulo 3
		 *
		 * m = n-1 -> win n+1 -> lose n -> tie
		 *
		 * ergo
		 *
		 * n-m = 0 -> tie 1 -> win 2 -> lose
		 *
		 * Of course if we only cared about the result of the game none of this
		 * would be necessary, but people want to see the computer's "choice" on
		 * screen
		 ************************************************************************/
		// The +3 is to prevent a negative, which behave not how we want with %.
		// Math.FloorMod would work, but was causing SDK issues on some
		// environments
		return humanMove == null ? null : results.values()[((humanMove.ordinal() - AIMove.ordinal()) + 3) % 3];
	}

}
