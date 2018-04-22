package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;



public class CardNearestShip extends Card{

	private Space target ;

	public Space getTarget() {
		return target;
	}

	public void setTarget(Space target) {
		this.target = target;
	}

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