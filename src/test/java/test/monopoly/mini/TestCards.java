package test.monopoly.mini;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.MiniMonopoly;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardNearestShip;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class TestCards {

	Game game;
	GameController controller;
	CardNearestShip nearestShip;
	Player player;
	
	@Before
	public void setUp() throws Exception {
		//set en current position
		//teste hvilken rederi som er tætest på spilleren
		//det han skal rykke til når han trækker kortet
		game = MiniMonopoly.createGame();
		
		player = new Player();
		player.setName("Spiller 1"); 
		player.setColor(Color.black);
		player.setCurrentPosition(game.getSpaces().get(0));
		game.addPlayer(player);
		
		controller = new GameController(game);
		
		controller.initializeGUI();
		
		
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testCardNearestShip() {
		nearestShip = new CardNearestShip();
		nearestShip.setText("Ryk brikken frem til det nærmeste rederi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken");
		List<Card> cards = new ArrayList<Card>();
		cards.add(nearestShip);
		game.setCardDeck(cards);
		
		int[] expecteds = new int[] {5,15,25,25,35,5};
		int[] actuals = new int[expecteds.length];
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(2));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[0] = player.getCurrentPosition().getIndex();
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(7));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[1] = player.getCurrentPosition().getIndex();
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(17));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[2] = player.getCurrentPosition().getIndex();
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(22));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[3] = player.getCurrentPosition().getIndex();
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(33));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[4] = player.getCurrentPosition().getIndex();
		
		try {
			controller.moveToSpace(player, game.getSpaces().get(36));
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[5] = player.getCurrentPosition().getIndex();
		
		assertArrayEquals(expecteds, actuals);
	}

}
