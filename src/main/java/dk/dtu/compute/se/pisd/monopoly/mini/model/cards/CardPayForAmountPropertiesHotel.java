package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class CardPayForAmountPropertiesHotel extends Card {
	private int amountPerHouse;
	private int amountPerHotel;
	
	public int getAmountPerHotel() {
		return amountPerHotel;
	}
	public int getAmountPerHouse() {
		return amountPerHouse;
	}
	public void setAmountPerHotel(int amountPerHotel) {
		this.amountPerHotel = amountPerHotel;
	}
	public void setAmountPerHouse(int amountPerHouse) {
		this.amountPerHouse = amountPerHouse;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			
			int payment = 0;
					
			for(Property property : player.getOwnedProperties())
			{
				payment = payment + property.getHotel() * amountPerHotel;
				payment = payment + property.getHouse() * amountPerHouse;
			}

			controller.paymentToBank(player, payment);
			
		} finally {
			super.doAction(controller, player);
		}
	}
}
