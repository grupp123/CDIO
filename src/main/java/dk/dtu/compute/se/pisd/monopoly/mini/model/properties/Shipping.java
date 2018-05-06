package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents the shipping field from the Matador gameboard.
 * @author Nils Rasamoel, Jacob Jørgensen
 *
 */
public class Shipping extends Property{

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
					if (property instanceof Shipping) {
						count++;
					}
				}
				rent = (int) (super.getRent()*Math.pow(2, count-1));

				controller.payment(player, rent, owner);
			}
		}

	}

	@Override
	public String getDescription() {
		String description = "Leje kr. "+this.getRent()
			+"\\nHvis 2 rederier ejes kr. "+this.getRent()*2
			+"\\nHvis 3 rederier ejes kr. "+this.getRent()*3
			+"\\nHvis 4 rederier ejes kr. "+this.getRent()*4
			+"\\n"
			+"\\nPantsætningsværdi kr. "+this.getMortgageValue();
		return description;
	}
	
	

}
