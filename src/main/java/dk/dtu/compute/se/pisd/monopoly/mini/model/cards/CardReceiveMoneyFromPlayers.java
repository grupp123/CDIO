package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import java.util.List;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the receive money from players chance card from the Matador boardgame.
 * @author 
 *
 */
public class CardReceiveMoneyFromPlayers extends Card{

	private int amount;

	/**
	 * Returns the amount this card directs the bank to pay to the player.
	 *  
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount this card directs the bank to pay to the player.
	 * 
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			List<Player> players = controller.getPlayers();
			for(int i = 0 ; i < players.size() ; i++ ) {
				controller.payment(player, i, players.get(i));
				controller.paymentToBank(players.get(i), getAmount());
			}
			controller.paymentFromBank(player, amount * players.size());
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}

}





