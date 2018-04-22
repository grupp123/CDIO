package test.monopoly.mini;


import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;


public class TestPlayer {

	Player balanceTest;
	Player positionTest;
	Player getColorTest;
	Player getNameTest;
	Player recieveMoneyTest;
	Player payMoneyTest;
	Player getOwnedPropertiesTest;
	Player addOwnedProperitesTest;
	Player removeOwnedPropertiesTest;
	Player reMoveAllPropertiesTest;
	Player getOwnedCardsTest;
	Player removeOwnedCardsTest;
	Player isBrokeTest;
	Player isInPrisonTest;
	Player getIdTest;
	
	
	@Before
	public void setUp() throws Exception {
		balanceTest = new Player();
		positionTest = new Player();
		getColorTest = new Player();
		getNameTest = new Player();
		recieveMoneyTest = new Player();
		payMoneyTest = new Player();
		getOwnedPropertiesTest = new Player();
		
	}
	
	
	
	
	@Test
	public void testGetColor() {
		getColorTest.getColor();
		Color actual = getColorTest.getColor();
		Color expected = null;
		assertEquals(actual, expected);
	}
	
	@Test
	public void testPosition() {
		positionTest.getCurrentPosition();
		Space actual = positionTest.getCurrentPosition();
		Object expected = null;
		assertEquals(actual, expected);
	}
	
	
	@Test
	public void testBalance() {
		balanceTest.getBalance();
		int actual = balanceTest.getBalance();
		int expected = 30000;
		assertEquals(actual, expected);
	}

}
