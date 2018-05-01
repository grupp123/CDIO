package dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions;

import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * An exception indicating a problem with adding or removing a house on a RealEstate object.
 * @author Nils Rasamoel
 *
 */
@SuppressWarnings("serial")
public class HousesOnRealEstateException extends Exception {
	private RealEstate estate;
	
	/**
	 * Constructor for exception.
	 * @param estate the RealEstate
	 */
	public HousesOnRealEstateException(RealEstate estate) {
		super("Too many or too few houses on the property for this action!\n"
				+ "You have "+estate.getHouses()+" houses on the property: "
				+estate.getName());
		this.estate = estate;
	}
	
	/**
	 * Returns the RealEstate which threw the exception.
	 * @return the RealEstate which threw the exception.
	 */
	public RealEstate getEstate() {
		return this.estate;
	}
}
