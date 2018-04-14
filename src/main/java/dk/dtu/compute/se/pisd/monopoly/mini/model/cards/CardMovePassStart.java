package dk.dtu.compute.se.pisd.monopoly.mini.model.cards;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;

public class CardMovePassStart extends Card{
	
	private int goToIndex;
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		
		if(player.getCurrentPosition().getIndex()>=goToIndex) {
			player.getCurrentPosition().setIndex(goToIndex);
			player.receiveMoney(2000);
		} else if (player.getCurrentPosition().getIndex() < goToIndex) {
			player.getCurrentPosition().setIndex(goToIndex);
		}
		
		super.doAction(controller, player);
	}
	public int getGoToIndex() {
		return goToIndex;
	}
	public void setGoToIndex(int goToIndex) {
		this.goToIndex = goToIndex;
	}
}
