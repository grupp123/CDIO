package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import java.awt.Color;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

/**
 * A specific property, which represents real estate on which houses
 * and hotels can be built. Note that has not details yet and needs
 * to be implemented.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RealEstate extends Property{
	
	// TODO to be implemented
	
	private Color color;
	
	private int houses = 0;
	private final int MAX_HOUSES = 5;
	private int housePrice;
	private boolean developped = false;
	
	public int getMAX_HOUSES() {
		return MAX_HOUSES;
	}

	public int getHousePrice() {
		return housePrice;
	}

	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	public int getHouses() {
		return houses;
	}

	public void setColor(Color color) {
		this.color=color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void addHouse() {
		houses++;
		developped = true;
		if (houses > MAX_HOUSES) {
			//TODO evt en exception?
			houses = MAX_HOUSES;
		}
		notify();
	}
	
	public void removeHouse() {
		houses--;
		if (houses < 0) {
			//TODO exception
			houses = 0;
		}
		if (houses == 0)
			developped = false;
		
		notify();
	}

}
