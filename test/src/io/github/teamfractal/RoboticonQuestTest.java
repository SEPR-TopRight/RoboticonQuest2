package io.github.teamfractal;

// Slight cargo-culting to attempt to fix errors
import org.junit.runner.RunWith; // May be superfluous
import de.tomgrill.gdxtesting.GdxTestRunner; // May be superfluous

import static org.junit.Assert.*;

import io.github.teamfractal.RoboticonQuest;

import org.junit.*;

@RunWith(GdxTestRunner.class) // May be superfluous
public class RoboticonQuestTest extends GdxInitializer {
	private RoboticonQuest game;
	
	@Before
	public void setUp(){
//		game = new RoboticonQuest();
//		game.create();
	}
	@Test
	public void phaseTest(){
//		assertEquals(0, game.getPhase());  //Test game starts in correct phase
//		for(int i = 1; i < 5; i++){
//			game.nextPhase();
//			assertEquals(i, game.getPhase());  //Test game will move from stages 1 to 5 without problem
//		}
//		game.nextPhase(); 
//		assertEquals(1, game.getPhase()); //Test phase resets
	}
	
	@Test
	public void playerTest(){
//		assert(game.getPlayerInt() == 0);  //Test game starts in correct player
//		game.nextPlayer();
//		assert(game.getPlayerInt() == 1);
//		game.nextPlayer();
//		assert(game.getPlayerInt() == 0); //Test game changes players correctly
	}
}
