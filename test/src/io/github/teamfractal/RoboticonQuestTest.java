package io.github.teamfractal;


import static org.junit.Assert.*;

import io.github.teamfractal.RoboticonQuest;

import org.junit.*;

public class RoboticonQuestTest {
	private RoboticonQuest game;
	
	@Before
	public void setUp(){
		game = new RoboticonQuest();
	}
	@Test
	public void phaseTest(){
		assert(game.getPhase() == 1);  //Test game starts in correct phase
		for(int i = 2; i < 6; i++){
			game.nextPhase();
			assert(i == game.getPhase());  //Test game will move from stages 1 to 5 without problem
		}
		game.nextPhase(); 
		assert(game.getPhase() == 1); //Test phase resets
	}
	
	@Test
	public void playerTest(){
		assert(game.getPlayerInt() == 0);  //Test game starts in correct player
		game.nextPlayer();
		assert(game.getPlayerInt() == 1);
		game.nextPlayer();
		assert(game.getPlayerInt() == 0);
	}
}
