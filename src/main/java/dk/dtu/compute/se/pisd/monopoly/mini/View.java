package dk.dtu.compute.se.pisd.monopoly.mini;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import dk.dtu.compute.se.pisd.designpatterns.Observer;
import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Chance;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.IncomeTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.StateTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Shipping;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Car;
import gui_fields.GUI_Car.Pattern;
import gui_fields.GUI_Car.Type;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Ownable;
import gui_fields.GUI_Player;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Tax;
import gui_main.GUI;

/**
 * This class implements a view on the Monopoly game based
 * on the original Matador GUI; it serves as a kind of
 * adapter to the Matador GUI. This class realizes the
 * MVC-principle on top of the Matador GUI. In particular,
 * the view implements an observer of the model in the
 * sense of the MVC-principle, which updates the GUI when
 * the state of the game (model) changes.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk, Jacob Jørgensen
 *
 */
public class View implements Observer {

	private Game game;
	private GUI gui;

	private Map<Player,GUI_Player> player2GuiPlayer = new HashMap<Player,GUI_Player>();
	private Map<Player,Integer> player2position = new HashMap<Player,Integer>();
	private Map<Space,GUI_Field> space2GuiField = new HashMap<Space,GUI_Field>();

	private boolean disposed = false;

	/**
	 * Constructor for the view of a game based on a game and an already
	 * running Matador GUI.
	 * 
	 * @param game the game
	 * @param gui the GUI
	 */
	public View(Game game) {
		this.game = game;
	}

	@Override
	public void update(Subject subject) {
		if (!disposed) {
			if (subject instanceof Player) {
				updatePlayer((Player) subject);
			}
			else if (subject instanceof Property) {
				if (subject instanceof RealEstate) {
					updateRealEstate((RealEstate)subject);
				}
				updateProperty((Property)subject);
			}

			// TODO update other subjects in the GUI
			//      in particular properties (sold, houses, ...)

		}
	}

	/**
	 * This method updates a player's state in the GUI. Right now, this
	 * concerns the players position and balance only. But, this should
	 * also include other information (being i prison, available cards,
	 * ...)
	 * 
	 * @param player the player who's state is to be updated
	 */
	private void updatePlayer(Player player) {
		GUI_Player guiPlayer = this.player2GuiPlayer.get(player);
		if (guiPlayer != null) {
			guiPlayer.setBalance(player.getBalance());

			GUI_Field[] guiFields = gui.getFields();
			Integer oldPosition = player2position.get(player);
			if (oldPosition != null && oldPosition < guiFields.length) {
				guiFields[oldPosition].setCar(guiPlayer, false);
			}
			int pos = player.getCurrentPosition().getIndex();
			if (pos < guiFields.length) {
				player2position.put(player,pos);
				guiFields[pos].setCar(guiPlayer, true);
			}

			if (player.isBroke()) {
				guiPlayer.setName(player.getName() + " (fallit)");
			} else if (player.isInPrison()) {
				guiPlayer.setName(player.getName() + " (i fængsel)");
			} else {

				guiPlayer.setName(player.getName());
			}
		}
	}

	/**
	 * Updates the realestate's state in the GUI (houses, hotel)
	 * @param realEstate the realestate which is to be updated.
	 */
	private void updateRealEstate(RealEstate realEstate) {
		GUI_Street guiEstate = (GUI_Street) this.space2GuiField.get(realEstate);
		if (guiEstate != null) {
			int houses = realEstate.getHouses();
			if (houses == realEstate.getMAX_HOUSES()) {
				guiEstate.setHotel(true);
			} else {
				guiEstate.setHotel(false);
				guiEstate.setHouses(houses);
			}

			guiEstate.setRent(Integer.toString(realEstate.getRent()));
		}
	}

	/**
	 * Updates the property's state in the GUI (Owner name and color and if mortgaged)
	 * @param property the property which is to be updated.
	 */
	private void updateProperty(Property property) {
		GUI_Ownable guiProperty = (GUI_Ownable) this.space2GuiField.get(property);
		Player owner = property.getOwner();
		if (owner != null) {
			guiProperty.setOwnerName(owner.getName());
			if (property.isMortgaged()) {
				guiProperty.setBorder(owner.getColor(), Color.white);
				guiProperty.setOwnableLabel("(Pantsat)");
			}
			else {
				guiProperty.setBorder(owner.getColor());
				guiProperty.setOwnableLabel("");
			}
		}
		else {
			guiProperty.setOwnerName("");
			guiProperty.setBorder(Color.black);
		}
	}

	/**
	 * Method used to unregister all Subjects.
	 */
	void dispose() {
		if (!disposed) {
			disposed = true;
			for (Player player: game.getPlayers()) {
				// unregister from the player as observer
				player.detach(this);
			}
		}
	}

	/**
	 * Creates and initiallizes the fields, cards and players in the GUI.
	 * @author Jacob Jørgesen,
	 * @return the created GUI object
	 */
	public GUI createGUI() {

		GUI_Field[] guiFields = new GUI_Field[game.getSpaces().size()];


		int i = 0 ;
		for (Space space: game.getSpaces()) {

			//System.out.println(i);

			if (space instanceof RealEstate) {
				RealEstate realestate = (RealEstate) space; 
				GUI_Street gui_street = new GUI_Street(
						realestate.getName(), 
						realestate.getSubText(), 
						realestate.getDescription(), 
						"husleje : " + realestate.getRent(), 
						realestate.getColor(), 
						Color.black
						);
				guiFields[i] = gui_street;
			} 
			else if (space instanceof Chance) {
				GUI_Chance gui_chance = new GUI_Chance();
				guiFields[i] = gui_chance;
				gui_chance.setBackGroundColor(space.getColor());
				gui_chance.setDescription(space.getDescription());

			} 
			else if (space instanceof Utility){
				Utility utility = (Utility) space;
				GUI_Brewery gui_brewery = new GUI_Brewery();
				gui_brewery.setTitle(utility.getName());
				gui_brewery.setSubText(utility.getSubText());
				gui_brewery.setDescription(utility.getDescription());
				gui_brewery.setBackGroundColor(Color.BLACK);
				gui_brewery.setForeGroundColor(Color.WHITE);
				guiFields[i] = gui_brewery;						
			} 
			else if (space instanceof Shipping) {
				Shipping shipping = (Shipping) space;
				GUI_Shipping gui_shipping = new GUI_Shipping();
				gui_shipping.setTitle(shipping.getName());
				gui_shipping.setSubText(shipping.getSubText());
				gui_shipping.setBackGroundColor(Color.white);
				gui_shipping.setDescription(shipping.getDescription());
				guiFields[i] = gui_shipping;
			} 
			else if (space instanceof IncomeTax){
				IncomeTax tax = (IncomeTax) space;
				GUI_Tax gui_tax = new GUI_Tax();
				gui_tax.setTitle(tax.getName());
				gui_tax.setSubText(tax.getSubText());
				gui_tax.setDescription(tax.getDescription());
				guiFields[i] = gui_tax;
			} 
			else if (space instanceof StateTax){
				StateTax tax = (StateTax) space;
				GUI_Tax gui_tax = new GUI_Tax();
				gui_tax.setTitle(tax.getName());
				gui_tax.setSubText(tax.getSubText());
				gui_tax.setDescription(tax.getDescription());
				guiFields[i] = gui_tax;
			}
			else {
				if(space.getIndex() == 10 || space.getIndex() == 30) {
					GUI_Jail gui_jail = new GUI_Jail();
					gui_jail.setSubText(space.getSubText());
					gui_jail.setDescription(space.getDescription());
					guiFields[i] = gui_jail;

				} 
				else if (space.getIndex() == 20) {
					GUI_Refuge gui_refuge = new GUI_Refuge();
					gui_refuge.setBackGroundColor(Color.WHITE);
					gui_refuge.setSubText(space.getSubText());
					guiFields[i] = gui_refuge;

				} 
				else {
					GUI_Field gui_field = new GUI_Start(
							space.getName(),
							space.getSubText(),
							space.getDescription(),
							space.getColor(),
							Color.BLACK
							);
					guiFields[i] = gui_field;

				}
			}
			space2GuiField.put(space, guiFields[i++]);

			// TODO we should also register with the properties as observer; but
			// the current version does not update anything for the spaces, so we do not
			// register the view as an observer for now
			space.attach(this);
		}

		gui = new GUI(guiFields);

		for (Player player: game.getPlayers()) {
			GUI_Car car = new GUI_Car(player.getColor(), Color.black, Type.CAR, Pattern.FILL);
			GUI_Player guiPlayer = new GUI_Player(player.getName(), player.getBalance(), car);
			player2GuiPlayer.put(player, guiPlayer);
			gui.addPlayer(guiPlayer);
			// player2position.put(player, 0);

			// register this view with the player as an observer, in order to update the
			// player's state in the GUI
			player.attach(this);




		}
		return gui;
	}

	/**
	 * Updates the players in the GUI.
	 */
	public void playerUpdate(){

		for (Player player: game.getPlayers()) {
			GUI_Car car = new GUI_Car(player.getColor(), Color.black, Type.CAR, Pattern.FILL);
			GUI_Player guiPlayer = new GUI_Player(player.getName(), player.getBalance(), car);
			player2GuiPlayer.put(player, guiPlayer);
			System.out.println(guiPlayer + "\n" + player);
			gui.addPlayer(guiPlayer);
			// player2position.put(player, 0);

			// register this view with the player as an observer, in order to update the
			// player's state in the GUI
			player.attach(this);

			updatePlayer(player);


		}
	}
}
