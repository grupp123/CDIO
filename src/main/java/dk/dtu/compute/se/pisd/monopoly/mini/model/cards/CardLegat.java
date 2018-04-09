package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class CardLegat extends Card{

	private int amount = 40000;

	public int getAmount() {
		return amount;
	}public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		int totalAmount = 0;

		for(Property property : player.getOwnedProperties())
		{
			totalAmount = totalAmount + property.getHouse() * property.getCost();
			totalAmount = totalAmount + property.getHotel() * property.getCost()*5;
			totalAmount = totalAmount + property.getCost();
		}
		totalAmount = totalAmount + player.getBalance();
		
		if(totalAmount<15000) {
			player.receiveMoney(amount);
		}
		
		super.doAction(controller, player);
	}
}
