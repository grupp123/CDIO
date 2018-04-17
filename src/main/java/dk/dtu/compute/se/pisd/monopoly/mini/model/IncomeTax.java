package dk.dtu.compute.se.pisd.monopoly.mini.model;

import java.awt.Color;

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
	
	public void setPayByPercent(boolean payByPercent) {
		this.payByPercent = payByPercent;
	}

	private String text;
	
	public void setText(String text ) {
		this.text=text;
	}
	
	public String getText() {
		return this.text;
	}
	
	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}


}
