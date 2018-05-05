package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the pay chance card from the Matador boardgame.
 * @author Alexander Kjeldsen, s165477@student.dtu.dk
 *
 */
public class CardPay extends Card{
	
	int amount;
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		
		try {
		player.payMoney(amount);
		}
		finally {
		super.doAction(controller, player);
		}
	}
	
	/**
	 * Returns the amount to be paid upon drawing this card.
	 * @return the amount to be paid upon drawing this card as int.
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount to be paid upon drawing this card.
	 * @param amount the amount to be paid upon drawing this card.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
