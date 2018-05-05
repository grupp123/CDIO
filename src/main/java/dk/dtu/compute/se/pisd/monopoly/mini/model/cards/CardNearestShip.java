package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the nearest ship chance card from the Matador boardgame.
 * @author Nils Rasamoel
 * @author Alexander Kjeldsen, s165477@student.dtu.dk
 *
 */
public class CardNearestShip extends Card{

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			int tar = 5;
			int pos = player.getCurrentPosition().getIndex();

			if(pos < 5) {
				tar = 5;
			} else if (pos < 15) {
				tar = 15;
			} else if (pos < 25) {
				tar = 25;
			} else if (pos < 35) {
				tar = 35;
			} else if (pos >= 35) {
				tar = 5;
			}
			controller.moveToIndex(player, tar);	
		} finally {
			super.doAction(controller, player);
		}
	}
}