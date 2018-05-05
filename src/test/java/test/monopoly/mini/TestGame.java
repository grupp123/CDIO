package test.monopoly.mini;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPay;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.NoHousesAvailableException;
/**
 * Test for all gamelogic methods
 * 
 * @author 
 *
 */
public class TestGame {

	Game game;
	@Before
	public void setUp() throws Exception {
		game = new Game();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddHouses() {
		int fails = 0, expected = 4;
		int[] values = {60,5,40,15,7,4,1};
		
		for (int i : values) {
			try {
				game.addHouses(i);
			} catch (NoHousesAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fails++;
			}
		}
		
		assertEquals(expected, fails);
	}
	
	
	@Test
	public void testShuffleCardDeck() {
		List<Card> cards = new ArrayList<Card>();
		Card card;
		String txt;
		for (int i = 0; i < 12; i++) {
			if (i == 6 || i == 4) {
				txt = "CardMove ";
				card = new CardMove();
			}
			else {
				txt = "CardPay";
				card = new CardPay();
			}
			card.setText(txt+"nb: "+i);
			cards.add(card);
		}
		
		game.setCardDeck(cards);
		
		if (!cards.equals(game.getCardDeck()))
			fail("Should be identical!");
		
		game.shuffleCardDeck();
		
		if (cards.equals(game.getCardDeck()))
			fail("Should not be identical!");
		
	}
	
	@Test
	public void testSetCurrentPlayer() {
		int expected = 2, actual = 0;
		
		List<Player> players = new ArrayList<Player>();
		Player player;
		for (int i = 0; i < 4; i++) {
			player = new Player();
			player.setName("Player "+i+1);
			players.add(player);
		}
		
		game.setPlayers(players);
		
		game.setCurrentPlayer(players.get(2));
		if (!game.getCurrentPlayer().equals(players.get(2)))
			actual++;
		
		player = new Player();
		player.setName("Out of game");
		
		try {
			game.setCurrentPlayer(player);
			
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			actual++;
		}
		
		
		player = null;
		
		try {
			game.setCurrentPlayer(player);
			
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
			actual++;
		}
		
		assertEquals(expected, actual);
	}
	

}
