package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * A specific property, which represents utilities which can
 * not be developed with houses or hotels.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Utility extends Property {
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		Player owner = super.getOwner();
		boolean mortgaged = super.isMortgaged();
		int rent, count = 0;
		if (!mortgaged) {
			if (owner == null) {
				controller.offerToBuy(this, player);
			} else if (!owner.equals(player)) {
				for(Property property : owner.getOwnedProperties()) {
					if (property instanceof Utility) {
						count++;
					}
				}
				//Rule says 100 times number of eyes or 200 if you own both utilities.
				rent = 100*count*player.get_throw();
				
				controller.payment(player, rent, owner);
			}
		}
	}

}
