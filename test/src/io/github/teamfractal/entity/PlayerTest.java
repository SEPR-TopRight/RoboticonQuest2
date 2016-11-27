package io.github.teamfractal.entity;

import static org.junit.Assert.*;

import org.junit.*;

public class PlayerTest {
	private Player player;
	
	@Before
	public void setUp(){
		player = new Player();
	}
	
	//Money Tests
	@Test
	public void testPlayerInitialMoney(){
		assertEquals(100, player.getMoney());
	}
	
	// Ore Tests
	@Test
	public void testPlayerBuyOre() throws Exception{
		Market market = new Market();
		//Purchase 5 ore
		player.purchaseResourceFromMarket(5, market, ResourceType.ORE);
		assertEquals(100 - 5 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
		assertEquals(5, player.getOre()); 
		//purchase 10 more ore
		player.purchaseResourceFromMarket(10, market, ResourceType.ORE);
		assertEquals(100 - 15 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
		assertEquals(15, player.getOre()); 
		//purchase more ore than it can afford
		try{
			player.purchaseResourceFromMarket(100, market, ResourceType.ORE);
		}
		catch(Exception exception){
			assertEquals(100 - 15 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
			assertEquals(15, player.getOre()); 
		}
	}

		
	@Test
	public void testPlayerSellOre() throws Exception{
		Market market = new Market();
		player.ore = 15;
		//sell 5 ore
		player.sellResourceToMarket(5, market, ResourceType.ORE);
		assertEquals(100 + 5 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
		assertEquals(10, player.getOre());
		//sell 5 more ore
		player.sellResourceToMarket(5, market, ResourceType.ORE);
		assertEquals(100 + 10 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
		assertEquals(5, player.getOre());
		//sell more ore than available
		try{
		player.sellResourceToMarket(10, market, ResourceType.ORE);
		}
		catch(Exception exception) { 
		assertEquals(100 + 10 * market.getResourcePrice(ResourceType.ORE), player.getMoney());
		assertEquals(5, player.getOre());
		}
	} 
	
	//Energy Tests
	@Test
	public void testPlayerBuyEnergy() throws Exception{
		Market market = new Market();
		//Purchase 5 Energy
		player.purchaseResourceFromMarket(5, market, ResourceType.ENERGY);
		assertEquals(100 - 5 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(5, player.getEnergy()); 
		//purchase 10 more Energy
		player.purchaseResourceFromMarket(10, market, ResourceType.ENERGY);
		assertEquals(100 - 15 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(15, player.getEnergy()); 
		//purchase more Energy than it can afford
		try{
			player.purchaseResourceFromMarket(100, market, ResourceType.ENERGY);
		}
		catch(Exception exception){
			assertEquals(100 - 15 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
			assertEquals(15, player.getEnergy()); 
		}
	}
	
	
	@Test
	public void testPlayerSellEnergy() throws Exception{
		Market market = new Market();
		player.energy = 15;
		//sell 5 Energy
		player.sellResourceToMarket(5, market, ResourceType.ENERGY);
		assertEquals(100 + 5 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(10, player.getEnergy());
		//sell 5 more Energy
		player.sellResourceToMarket(5, market, ResourceType.ENERGY);
		assertEquals(100 + 10 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(5, player.getEnergy());
		//sell more Energy than available
		try{
		player.sellResourceToMarket(10, market, ResourceType.ENERGY);
		}
		catch(Exception exception) { 
		assertEquals(100 + 10 * market.getResourcePrice(ResourceType.ENERGY), player.getMoney());
		assertEquals(5, player.getEnergy());
		}
	} 
	
	
}