package test.monopoly.mini;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPayForAmountPropertiesHotel;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.OutOfJail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

public class TestCards {

	Game game;
	GameController controller;
	CardNearestShip nearestShip;
	OutOfJail jailCard;
	Player player;
	
	@Before
	public void setUp() throws Exception {
		
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

	//set en current position
	//teste hvilken rederi som er tættest på spilleren
	//det han skal rykke til når han trækker kortet
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
	
	@Test
	public void testCardPayForAmountPropertiesHotelWithoutHouses() {
		CardPayForAmountPropertiesHotel cardPayHotel = new CardPayForAmountPropertiesHotel();
		cardPayHotel.setText("Oliepriserne er steget, og De skal betale: kr. 500 pr. hus, kr. 2.000 per. hotel");
		cardPayHotel.setAmountPerHotel(2000);
		cardPayHotel.setAmountPerHouse(500);
		List<Card> cards = new ArrayList<Card>();
		cards.add(cardPayHotel);
		
		CardPayForAmountPropertiesHotel propTax = new CardPayForAmountPropertiesHotel();
		propTax.setText("Ejendomsskatterne er steget, ekstraudgifterne er: kr. 800 pr. hus, kr. 2.300 per hotel");
		propTax.setAmountPerHotel(2300);
		propTax.setAmountPerHouse(800);
		cards.add(propTax);
		
		game.setCardDeck(cards);
		
		int[] expecteds = new int[] {30000,30000};
		int[] actuals = new int[expecteds.length];
		
		try {
			controller.takeChanceCard(player);
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[0] = player.getBalance();
		
		try {
			controller.takeChanceCard(player);
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[1] = player.getBalance();
				
		assertArrayEquals(expecteds, actuals);
	}
	

	@Test
	public void testCardPayForAmountPropertiesHotelWithHouses() {
		
		CardPayForAmountPropertiesHotel cardPayHotel = new CardPayForAmountPropertiesHotel();
		cardPayHotel.setText("Oliepriserne er steget, og De skal betale: kr. 500 pr. hus, kr. 2.000 per. hotel");
		cardPayHotel.setAmountPerHotel(2000);
		cardPayHotel.setAmountPerHouse(500);
		
		CardPayForAmountPropertiesHotel propTax = new CardPayForAmountPropertiesHotel();
		propTax.setText("Ejendomsskatterne er steget, ekstraudgifterne er: kr. 800 pr. hus, kr. 2.300 per hotel");
		propTax.setAmountPerHotel(2300);
		propTax.setAmountPerHouse(800);
		
		RealEstate r1 = new RealEstate();
		r1.setName("Roskildevej");
		r1.setCost(2000);
		r1.setRent(100);
		r1.setRentLevels(new int[] {100,600,1800,5400,8000,11000});
		r1.setHousePrice(1000);
		r1.setColor(Color.cyan);
		//game.addSpace(r1);
		
		RealEstate r2 = new RealEstate();
		r2.setName("Valby Langgade");
		r2.setCost(2000);
		r2.setRent(100);
		r2.setRentLevels(new int[] {100,600,1800,5400,8000,11000});
		r2.setHousePrice(1000);
		r2.setColor(Color.cyan);
		//game.addSpace(r2);
		
		RealEstate r3 = new RealEstate();
		r3.setName("Allégade");
		r3.setCost(2400);
		r3.setRent(150);
		r3.setRentLevels(new int[] {150,800,2000,6000,9000,12000});
		r3.setHousePrice(1000);
		r3.setColor(Color.cyan);
		//game.addSpace(r3);
		
		int[] expecteds = new int[] {22000,5500};
		int[] actuals = new int[expecteds.length];
		
		try {
			player.addOwnedProperty(r1);
			player.addOwnedProperty(r2);
			player.addOwnedProperty(r3);
			
			r1.addHouse();
			r1.addHouse();
			r1.addHouse();
			r1.addHouse();
			r1.addHouse();
			
			r2.addHouse();
			r2.addHouse();
			r2.addHouse();
			r2.addHouse();
			r2.addHouse();
			
			cardPayHotel.doAction(controller, player);
			
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[0] = player.getBalance();
		
		try {
			r3.addHouse();
			r3.addHouse();
			r3.addHouse();
			r3.addHouse();
			r3.addHouse();
			
			
			propTax.doAction(controller, player);
			
		} catch (PlayerBrokeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actuals[1] = player.getBalance();
				
		assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void testRemoveOutOfJailCard() {
		int newSize;
		Card[] arr = game.getCardDeck().toArray(new Card[0]);
		List<Card> cardDeck = new ArrayList<Card>();
		
		for (Card card : arr) {
			cardDeck.add(card);
		}
		
		//Jailcard indexes: 20 and 21;
		cardDeck.remove(20);
		cardDeck.remove(21);
		newSize = cardDeck.size();
		
		game.setCardDeck(cardDeck);
		
		assertEquals(newSize, game.getCardDeck().size());
	}

}
