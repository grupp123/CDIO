package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
/**
 * Class that represents the state tax field on the Matador game board.
 * @author Nils Rasamoel
 *
 */
public class StateTax extends Space{

	private String text;
	private int tax;

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		controller.paymentToBank(player, tax);
	}
	
	/**
	 * Sets the text of the field.
	 * @param text text of the field.
	 */
	public void setText(String text ) {
		this.text=text;
	}
	
	/**
	 * Returns the field's text.
	 * @return the field's text as String.
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
	 * @param tax the field's tax value.
	 */
	public void setTax(int tax) {
		this.tax = tax;
	}

}
