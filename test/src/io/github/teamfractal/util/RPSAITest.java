package io.github.teamfractal.util;

import org.junit.Test;
import static org.junit.Assert.*;

import io.github.teamfractal.util.RPSAI;
import static io.github.teamfractal.util.RPSAI.moves;
import static io.github.teamfractal.util.RPSAI.results;

public class RPSAITest {

	private final int ITERATIONS = 1000;

	private RPSAI rpsai;
	private moves AIMove;
	private results result;
	private int resultsTally[] = { 0, 0, 0 };

	@Test
	public void MainTest() {
		rpsai = new RPSAI();
		assertEquals(rpsai.getAIMove(), null);
		assertEquals(rpsai.getHumanMove(), null);
		assertEquals(rpsai.getResult(), null);

		// This may seem quite verbose, but the "clever" modular arithmetic
		// stuff is done in RPSAI, this is a boring and stupid, but definitely
		// correct test

		for (int i = 0; i < ITERATIONS; i++) {
			rpsai.setHumanMove(moves.ROCK);
			assertEquals(rpsai.getHumanMove(), moves.ROCK);
			AIMove = rpsai.getAIMove();
			result = rpsai.getResult();
			assertTrue(AIMove != null);
			assertTrue(result != null);
			switch (AIMove) {
			case ROCK:
				assertEquals(result, results.TIE);
				resultsTally[0]++;
				break;
			case PAPER:
				assertEquals(result, results.LOSE);
				resultsTally[1]++;
				break;
			case SCISSORS:
				assertEquals(result, results.WIN);
				resultsTally[2]++;
				break;
			}

			rpsai.setHumanMove(moves.PAPER);
			assertEquals(rpsai.getHumanMove(), moves.PAPER);
			AIMove = rpsai.getAIMove();
			result = rpsai.getResult();
			assertTrue(AIMove != null);
			assertTrue(result != null);
			switch (AIMove) {
			case ROCK:
				assertEquals(result, results.WIN);
				resultsTally[0]++;
				break;
			case PAPER:
				assertEquals(result, results.TIE);
				resultsTally[1]++;
				break;
			case SCISSORS:
				assertEquals(result, results.LOSE);
				resultsTally[2]++;
				break;
			}

			rpsai.setHumanMove(moves.SCISSORS);
			assertEquals(rpsai.getHumanMove(), moves.SCISSORS);
			AIMove = rpsai.getAIMove();
			result = rpsai.getResult();
			assertTrue(AIMove != null);
			assertTrue(result != null);
			switch (AIMove) {
			case ROCK:
				assertEquals(result, results.LOSE);
				resultsTally[0]++;
				break;
			case PAPER:
				assertEquals(result, results.WIN);
				resultsTally[1]++;
				break;
			case SCISSORS:
				assertEquals(result, results.TIE);
				resultsTally[2]++;
				break;
			}
		}

		// Now check statistics. Each move count should be within 10% of
		// iterations. There is no need to check results as well, as the
		// previous testing has already verified that they are being computed
		// correctly

		for (int i = 0; i < 3; i++) {
			assertTrue(Math.abs(resultsTally[i] - ITERATIONS) < ITERATIONS / 10);
		}
	}

}
