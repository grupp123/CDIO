package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;
import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the bail chance card from the Matador boardgame.
 * @author 
 *
 */
public class CardBail extends Card {

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			player.getOwnedCards().add(new CardBail());
		} finally {
			super.doAction(controller, player);
		}
	}



}

