package test.monopoly.mini;


import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;


public class TestPlayer {

	Player player;
	
	
	@Before
	public void setUp() throws Exception {
		player = new Player();
		
	}
	
	
	
	
	@Test
	public void testGetColor() {
		player.getColor();
		Color actual = player.getColor();
		Color expected = null;
		assertEquals(actual, expected);
	}
	
	@Test
	public void testPosition() {
		player.getCurrentPosition();
		Space actual = player.getCurrentPosition();
		Object expected = null;
		assertEquals(actual, expected);
	}
	
	
	@Test
	public void testBalance() {
		player.getBalance();
		int actual = player.getBalance();
		int expected = 30000;
		assertEquals(actual, expected);
	}
	
	@Test
	public void testGetAssetsValue() {
		int balance = 30000, cost = 4000, houseP = 600;
		int value = player.getAssetsValue();
		
		RealEstate property = new RealEstate();
		property.setCost(cost);
		property.setRentLevels(new int[]{1,2,3,4,5});
		property.setHousePrice(houseP);
		
		player.addOwnedProperty(property);
		
		value += player.getAssetsValue();
		
		property.setHouses(2);
		
		value += player.getAssetsValue();
		
		int expected = balance*3 + cost + houseP*2;
		
		assertEquals(expected, value);
	}

}
