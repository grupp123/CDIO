package dk.dtu.compute.se.pisd.monopoly.mini;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import dk.dtu.compute.se.pisd.designpatterns.Observer;
import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Chance;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Tax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.differentSpaces.FreeParking;
import dk.dtu.compute.se.pisd.monopoly.mini.model.differentSpaces.Go;
import dk.dtu.compute.se.pisd.monopoly.mini.model.differentSpaces.Jail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Brewery;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Shipping;
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Car;
import gui_fields.GUI_Car.Pattern;
import gui_fields.GUI_Car.Type;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Jail;
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
 * @author Ekkart Kindler, ekki@dtu.dk
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
		/*		this.gui = gui;
		GUI_Field[] guiFields = gui.getFields();

		int i = 0;
		for (Space space: game.getSpaces()) {

			space.
			// TODO, here we assume that the games fields fit to the GUI's fields;
			// the GUI fields should actually be created according to the games
			// fields
			//space2GuiField.put(space, guiFields[i++]);

			// TODO we should also register with the properties as observer; but
			// the current version does not update anything for the spaces, so we do not
			// register the view as an observer for now
		}

		// create the players in the GUI
		for (Player player: game.getPlayers()) {
			GUI_Car car = new GUI_Car(player.getColor(), Color.black, Type.CAR, Pattern.FILL);
			GUI_Player guiPlayer = new GUI_Player(player.getName(), player.getBalance(), car);
			player2GuiPlayer.put(player, guiPlayer);
			gui.addPlayer(guiPlayer);
			// player2position.put(player, 0);

			// register this view with the player as an observer, in order to update the
			// player's state in the GUI
			player.attach(this);

			updatePlayer(player);
		}*/
	}

	@Override
	public void update(Subject subject) {
		if (!disposed) {
			if (subject instanceof Player) {
				updatePlayer((Player) subject);
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
				guiPlayer.setName(player.getName() + " (broke)");
			} else if (player.isInPrison()) {
				guiPlayer.setName(player.getName() + " (in prison)");
			} else {
				guiPlayer.setName(player.getName());
			}
		}
	}

	void dispose() {
		if (!disposed) {
			disposed = true;
			for (Player player: game.getPlayers()) {
				// unregister from the player as observer
				player.detach(this);
			}
		}
	}

	public GUI createGUI(Game game2) {

		GUI_Field[] guiFields = new GUI_Field[game.getSpaces().size()];
		
		System.out.println(game.getSpaces().size());


		int i = 0 ;
		for (Space space: game.getSpaces()) {
			
			System.out.println(i);
			
			if (space instanceof Chance) {
				GUI_Chance gui_chance = new GUI_Chance();
				guiFields[i] = gui_chance;
				gui_chance.setBackGroundColor(Color.BLUE);
				gui_chance.setDescription("Chancekort");
				
			} else if (space instanceof RealEstate) {
				RealEstate realestate = (RealEstate) space; 
				// GUI_Street gui_Street = new GUI_Street(realestate.getName());
				GUI_Street gui_street = new GUI_Street(realestate.getName(), "" + realestate.getCost() + "", "" + realestate.getRent() + "", "" + realestate.getRent() + "", Color.white, Color.BLUE);
				gui_street.setBackGroundColor(((RealEstate) space).getColor());
				guiFields[i] = gui_street;
			} // ...
			else if (space instanceof Shipping){
				Shipping utility = (Shipping) space;
				GUI_Shipping gui_shipping = new GUI_Shipping();
				gui_shipping.setTitle(space.getName());
				gui_shipping.setBackGroundColor(Color.WHITE);
				gui_shipping.setSubText(""+((Shipping) space).getCost());
				guiFields[i] = gui_shipping;
				
			} else if (space instanceof Brewery){
				Brewery utility = (Brewery) space;
				GUI_Brewery gui_brewery = new GUI_Brewery();
				gui_brewery.setBackGroundColor(Color.BLACK);
				gui_brewery.setForeGroundColor(Color.WHITE);
				gui_brewery.setTitle(space.getName());
				gui_brewery.setSubText(""+((Brewery) space).getCost());
				guiFields[i] = gui_brewery;

			} else if (space instanceof Tax){
				Tax tax = (Tax) space;
				GUI_Tax gui_tax = new GUI_Tax();
				guiFields[i] = gui_tax;
				gui_tax.setBackGroundColor(Color.gray);
				gui_tax.setTitle("skat");
				gui_tax.setSubText(((Tax) space).getText());
				gui_tax.setDescription(((Tax) space).getText());
				
			}else if(space instanceof Go) {
				Go go = (Go) space;
				GUI_Start gui_start = new GUI_Start();
				gui_start.setTitle(space.getName());
				gui_start.setSubText("");
				gui_start.setForeGroundColor(Color.WHITE);
				gui_start.setBackGroundColor(Color.RED);
				guiFields[i] = gui_start;
				gui_start.setDescription(go.getText());
				
			}else if(space instanceof Jail) {
				Jail jail = (Jail) space;
				GUI_Jail gui_jail = new GUI_Jail();
				gui_jail.setSubText(space.getName());
				guiFields[i] = gui_jail;
				
			}else if(space instanceof FreeParking) {
				FreeParking freeparking = (FreeParking) space;
				GUI_Refuge gui_refuge = new GUI_Refuge();
				gui_refuge.setSubText(space.getName());
				gui_refuge.setBackGroundColor(Color.WHITE);
				guiFields[i] = gui_refuge;
			}
			else {
				GUI_Field gui_field = new GUI_Field(Color.red, Color.red, space.getName(), "e", "r") {
				};
				guiFields[i] = gui_field;
			}

			space2GuiField.put(space, guiFields[i++]);

			// TODO we should also register with the properties as observer; but
			// the current version does not update anything for the spaces, so we do not
			// register the view as an observer for now
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