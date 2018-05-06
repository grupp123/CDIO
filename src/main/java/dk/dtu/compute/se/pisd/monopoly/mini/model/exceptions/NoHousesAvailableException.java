package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;


/**
 * An exception that indicates that there are no houses available to buy.
 * @author Ahad Imtiaz
 *
 */
@SuppressWarnings("serial")
public class NoHousesAvailableException extends Exception {

	private Game houses;
	
	/**
	 * An exception that indicates that there are no houses available to purchase
	 * 
	 * @param houses
	 */
	public NoHousesAvailableException(Game houses) {
		super("No houses available");
		this.houses = houses;
	}

	/**
	 * Returns houses
	 * 
	 * @return houses
	 */
	Game houses() {
		return houses;
	}
}
