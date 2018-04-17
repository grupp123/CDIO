package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;

@SuppressWarnings("serial")
public class NoHotelAvailableException extends Exception {

	private Game hotels;

	/**
	 * An exceptions that indicates that there are no hotels available to purchase
	 * 
	 * @param hotels
	 */
	public NoHotelAvailableException(Game hotels) {
		super("No hotels available");
		this.hotels = hotels;
	}

	/**
	 * Returns hotels
	 * 
	 * @return hotels
	 */
	Game getHotels() {
		return hotels;
	}

}
