package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

/**
 * Class that represents the endowment chance card from the Matador boardgame.
 * @author Nils Rasamoel
 *
 */
public class CardLegat extends Card{

	private int amount = 40000;

	/**
	 * Returns the amount of the endowment.
	 * @return the amount of the endowment as int.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of the endowment.
	 * @param amount the amount of the endowment.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		if(player.getAssetsValue()<15000) {
			player.receiveMoney(amount);
		}

		super.doAction(controller, player);
	}
}
