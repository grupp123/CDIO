package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * Represents the pay for amount of properties and buildings chance card from the Matador boardgame.
 * @author Nils Rasamoel
 *
 */
public class CardPayForAmountPropertiesHotel extends Card {
	private int amountPerHouse;
	private int amountPerHotel;

	/**
	 * Returns the amount to be paid per hotel.
	 * @return the amount to be paid per hotel as int.
	 */
	public int getAmountPerHotel() {
		return amountPerHotel;
	}

	/**
	 * Returns the amount to be paid per house.
	 * @return the amount to be paid per house as int.
	 */
	public int getAmountPerHouse() {
		return amountPerHouse;
	}

	/**
	 * Sets the amount to be paid per hotel.
	 * @param amountPerHotel the amount to be paid per hotel.
	 */
	public void setAmountPerHotel(int amountPerHotel) {
		this.amountPerHotel = amountPerHotel;
	}

	/**
	 * Sets the amount to be paid per house.
	 * @param amountPerHouse the amount to be paid per house.
	 */
	public void setAmountPerHouse(int amountPerHouse) {
		this.amountPerHouse = amountPerHouse;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {

			int payment = 0;

			for(Property property : player.getOwnedProperties())
			{
				if (property instanceof RealEstate) {
					RealEstate estate = (RealEstate) property;
					int houses = estate.getHouses();
					if (houses == estate.getMAX_HOUSES()) {
						payment += (houses-1) * amountPerHouse;
						payment += amountPerHotel;
					}
					else {
						payment += houses * amountPerHouse;
					}

				}

			}

			controller.paymentToBank(player, payment);

		} finally {
			super.doAction(controller, player);
		}
	}
}
