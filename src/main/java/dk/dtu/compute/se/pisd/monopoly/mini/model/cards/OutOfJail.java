package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;
import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class OutOfJail extends Card {

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			player.getOwnedCards().add(new OutOfJail());
		} finally {
			super.doAction(controller, player);
		}
	}



}

