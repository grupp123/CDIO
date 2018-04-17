package dk.dtu.compute.se.pisd.monopoly.mini.model;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class StateTax extends Space{

	private String text;
	private int tax;
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		controller.paymentToBank(player, tax);
	}


	
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
