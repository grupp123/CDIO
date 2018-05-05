package test.monopoly.mini;

import static org.junit.Assert.*;
import java.awt.Color;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
/**
 * Test gamecontroller methods
 * @author 
 *
 */
public class TestGameController {

	Game game;
	GameController controller;

	@Before
	public void setUp() throws Exception {
		game = MiniMonopoly.createGame();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayerRanking() {
		Player player;
		for (int i = 1; i < 7; i++) {
			player = new Player();
			player.setName("Spiller "+i); 
			player.setColor(Color.black);
			player.setCurrentPosition(game.getSpaces().get(0));
			game.addPlayer(player);
		}

		List<Player> players = game.getPlayers();

		players.get(0).setBalance(22000);
		players.get(1).setBalance(1000);
		players.get(2).setBalance(40001);
		players.get(3).setBalance(0);
		players.get(4).setBalance(93777);
		players.get(5).setBalance(30000);

		controller = new GameController(game);

		controller.initializeGUI();

		String[] expecteds = new String[] {"Spiller 5",
				"Spiller 3", "Spiller 6", "Spiller 1", "Spiller 2", "Spiller 4"};

		String[] actuals = new String[expecteds.length];

		int i = 0;
		for (Player p : controller.playerRanking(game.getPlayers())) {
			actuals[i] = p.getName();
			System.out.println(actuals[i]);
			i++;
		}



		assertArrayEquals(expecteds, actuals);


	}

	@Test
	public void jail() {
		Player p = new Player();
		
		p.setName("peter");
		p.setCurrentPosition(game.getSpaces().get(0));
		game.addPlayer(p);

		GameController c = new GameController(game);
		c.initializeGUI();

		boolean beforeController = p.isInPrison();
		c.gotoJail(p);
		boolean afterController = p.isInPrison();
		assertTrue(beforeController==false && afterController==true);	
	}

	@Test
	public void auction() {

		int index = 1;

		Player p = new Player();
		p.setName("peter");
		p.setCurrentPosition(game.getSpaces().get(0));
		game.addPlayer(p);

		GameController c = new GameController(game);
		c.initializeGUI();

		Property pp = null;
		if (game.getSpaces().get(index) instanceof Property) {
			pp = (Property) game.getSpaces().get(index);
		}
		
		c.auction(pp);

		assertTrue(pp.getOwner()==p);	
	}
	
	@Test
	public void payment() throws PlayerBrokeException {
		
		int index = 1;
		int amount = 1000;
		int bal = 2000;
		
		Player p1 = new Player();
		p1.setName("peter");
		p1.setBalance(bal);
		p1.setCurrentPosition(game.getSpaces().get(index));
		game.addPlayer(p1);
		
		Player p2 = new Player();
		p2.setName("finn");
		p2.setBalance(bal);
		p2.setCurrentPosition(game.getSpaces().get(index));
		game.addPlayer(p2);
		
		controller = new GameController(game);
		
		controller.initializeGUI();
		
		controller.payment(p1, amount, p2);
		
		assertTrue(p1.getBalance()+2000==p2.getBalance());
		
	}
	
	@Test
	public void move() throws PlayerBrokeException {
		
		int index = 1;
		int incre = 1;
		int bal = 2000;
		
		Player p1 = new Player();
		p1.setName("peter");
		p1.setBalance(bal);
		p1.setCurrentPosition(game.getSpaces().get(index));
		game.addPlayer(p1);
		
		controller = new GameController(game);
		
		controller.initializeGUI();
		controller.moveToSpace(p1, game.getSpaces().get(index+incre));
		
		assertTrue(p1.getCurrentPosition().getIndex()==index+incre);
	}
	
	@Test
	public void paymentFromBank() throws PlayerBrokeException {
		
		int index = 1;
		int incre = 1000;
		int bal = 2000;
		
		Player p1 = new Player();
		p1.setName("peter");
		p1.setBalance(bal);
		p1.setCurrentPosition(game.getSpaces().get(index));
		game.addPlayer(p1);
		
		controller = new GameController(game);
		
		controller.initializeGUI();
		controller.paymentFromBank(p1, incre);
		assertTrue(p1.getBalance()==bal+incre);
	}


}
