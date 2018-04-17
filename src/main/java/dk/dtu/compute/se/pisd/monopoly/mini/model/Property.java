package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * A property which is a space that can be owned by a player.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Property extends Space {
	
	private int cost;
	private int rent;
	
	private boolean mortgaged;
	private int mortageValue;
	
	private Player owner;
	
	
	public boolean isMortaged() {
		return mortgaged;
	}
	public void setMortaged(boolean mortaged) {
		this.mortgaged = mortaged;
		notifyChange();
	}
	public int getMortageValue() {
		return mortageValue;
	}
	public void setMortageValue(int mortageValue) {
		this.mortageValue = mortageValue;
	}
	

	/**
	 * Returns the cost of this property.
	 * 
	 * @return the cost of this property
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Sets the cost of this property.
	 * 
	 * @param cost the new cost of this property
	 */
	public void setCost(int cost) {
		this.cost = cost;
		setMortageValue(cost/2);
		notifyChange();
	}

	/**
	 * Returns the rent to be payed for this property.
	 * 
	 * @return the rent for this property
	 */
	public int getRent() {
		if (mortgaged) {
			return 0;
		}
		return rent;
	}

	/**
	 * Sets the rent for this property.
	 * 
	 * @param rent the new rent for this property
	 */
	public void setRent(int rent) {
		this.rent = rent;
		notifyChange();
	}

	/**
	 * Returns the owner of this property. The value is <code>null</code>,
	 * if the property currently does not have an owner.
	 * 
	 * @return the owner of the property or <code>null</code>
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Sets the owner of this property  to the given owner (which can be 
	 * <code>null</code>).
	 * 
	 * @param player the new owner of the property
	 */
	public void setOwner(Player player) {
		this.owner = player;
		notifyChange();
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if (!mortgaged) {
			if (owner == null) {
				controller.offerToBuy(this, player);
			} else if (!owner.equals(player)) {
				controller.payment(player, rent, owner);
			}
		}
		
	}

}
