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
	private Color color;
	private int[] rentLevels;
	private int houses = 0;
	private final int MAX_HOUSES = 5;
	private int housePrice;
	private boolean developped = false;

	/**
	 * Returns the maximum amount of houses allowed on a real estate field.
	 * @return the maximum amount of houses allowed as int.
	 */
	public int getMAX_HOUSES() {
		return MAX_HOUSES;
	}

	/**
	 * Returns the field's price per house.
	 * @return the field's price per house as int.
	 */
	public int getHousePrice() {
		return housePrice;
	}

	/**
	 * Sets the field's price per house.
	 * @param housePrice the field's price per house.
	 */
	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Returns the amount of houses on the field.
	 * @return the amount of houses on the field as int.
	 */
	public int getHouses() {
		return houses;
	}

	/**
	 * Sets the field's color.
	 */
	public void setColor(Color color) {
		this.color=color;
	}

	/**
	 * Returns the field's color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Adds a house to the field (if possible).
	 */
	public void addHouse() {
		houses++;
		developped = true;
		if (houses > MAX_HOUSES) {
			//TODO evt en exception?
			houses = MAX_HOUSES;
		}
		setRent(rentLevels[houses]);
		notifyChange();
	}

	/**
	 * Sets the amount of houses on the field.
	 * @param houses hte amount of houses on the field.
	 */
	public void setHouses(int houses) {
		this.houses = houses;
	}

	/**
	 * Removes a house from the field (if possible).
	 */
	public void removeHouse() {
		houses--;
		if (houses < 0) {
			//TODO exception
			houses = 0;
		}
		if (houses == 0)
			developped = false;
		setRent(rentLevels[houses]);
		notifyChange();
	}

	/**
	 * Returns the boolean value representing whether or not the field is developed.
	 * @return <code>true</code> if field has houses, if not then <code>false</code>.
	 */
	public boolean isDevelopped() {
		return developped;
	}

	/**
	 * Clears the houses from the field. Sets it to zero.
	 */
	public void clearHouses() {
		houses = 0;
		developped = false;
	}

	/**
	 * Sets an array representing the rent levels of the field. One per amount of houses.
	 * @param rentLevels an array representing the rent levels of the field.
	 */
	public void setRentLevels(int[] rentLevels) {
		this.rentLevels = rentLevels;
	}


}
