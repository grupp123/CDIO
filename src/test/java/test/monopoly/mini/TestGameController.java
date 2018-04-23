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
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardNearestShip;

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

}
