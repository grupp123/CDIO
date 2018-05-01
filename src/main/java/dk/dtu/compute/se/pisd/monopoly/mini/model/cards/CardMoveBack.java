package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the move back chance card from the Matador boardgame.
 * @author 
 *
 */
public class CardMoveBack extends Card{


	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		player.getCurrentPosition().setIndex(player.getCurrentPosition().getIndex()-3);
		super.doAction(controller, player);
	}
}

