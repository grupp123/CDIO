package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

/**
 * Represents a space, where the player has to pay tax.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class IncomeTax extends Space {

	boolean payByPercent = false;
	int tax;
	private String text;

	/**
	 * Sets the field's text
	 * @param text the text of the field
	 */
	public void setText(String text ) {
		this.text=text;
	}

	/**
	 * Returns the field's text.
	 * @return the field's text in String
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Returns the field's tax value.
	 * @return the field's tax value as int.
	 */
	public int getTax() {
		return tax;
	}

	/**
	 * Sets the field's tax value.
	 * @param tax the tax value in int.
	 */
	public void setTax(int tax) {
		this.tax = tax;
	}
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if (payByPercent) {
			controller.paymentToBank(player, player.getAssetsValue() / 10);
			payByPercent = false;
		}
		else {
			controller.paymentToBank(player, tax);
		}
	}

	/**
	 * Set to true if the tax should be percent based instead of fixed.
	 * @param payByPercent boolean value.
	 */
	public void setPayByPercent(boolean payByPercent) {
		this.payByPercent = payByPercent;
	}




}
