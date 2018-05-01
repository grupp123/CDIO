package dk.dtu.compute.se.pisd.monopoly.mini;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.IncomeTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.OutOfJail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.NoHousesAvailableException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dtu.database.Connector;
import dtu.database.GameDAO;
import gui_main.GUI;

/**
 * The overall controller of a Monopoly game. It provides access
 * to all basic actions and activities for the game. All other
 * activities of the game, should be implemented by referring
 * to the basic actions and activities in this class. This is
 * necessary, since the GUI does not support MVC yet.
 * 
 * Note that this controller is far from being finished and many
 * things could be done in a much nicer and cleaner way! But, it
 * shows the general idea of how the model, view (GUI), and the
 * controller could work with each other, and how different parts
 * of the games activities can be separated from each other, so
 * that different parts can be added and extended independently
 * from each other.
 * 
 * Note that it is crucial that all changes to the
 * {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Game} need to
 * be made through this controller, since the GUI does not
 * follow the MVC pattern. For fully implementing the game, it will
 * be necessary to add more of these basic actions in this class.
 * 
 * The action methods of the
 * {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Space} and
 * the {@link dk.dtu.compute.se.pisd.monopoly.mini.model.Card}
 * can be implemented based on the basic actions and activities
 * of this game controller. Then, the game controller takes care
 * of updating the GUI.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

	private Game game;

	private GUI gui;

	private View view;

	private boolean disposed = false;

	private Connector c = new Connector();

	private GameDAO dao = new GameDAO(c);

	private static int CurrentMaxGameID;

	private static int CurrentMaxPlayerID;

	/**
	 * Constructor for a controller of a game.
	 * 
	 * @param game
	 */
	public GameController(Game game) {
		super();
		this.game = game;

	}

	/**
	 * Choose to load a game or make a new one.
	 */

	public void chooseNewOrLoadGame() {
		boolean newGame = gui.getUserLeftButtonPressed("", "Nyt spil", "Load spil");
		if(newGame) {
			try {
				createPlayers();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				loadGame();
			} catch (Exception e) {
				System.err.println(e);
				
			}
		}
	}
	
	/**
	 * Load game.
	 * @throws Exception
	 */

	private void loadGame() throws Exception {
		GameDAO dao = new GameDAO(c);
		ArrayList<String> spil = dao.activeGames();
		if (spil.size()==0) {
			createPlayers();
		}
		String[] spilstr = new String[spil.size()];
		
		for (int i = 0; i < spil.size(); i++) {
			spilstr[i] = spil.get(i);
		}
		String str = gui.getUserSelection("vælg spil", spilstr);
		int gameid = dao.getGameIdFromName(str);
		game.setGameID(gameid);
		
		dao.load(game, game.getGameID());
		
		view.playerUpdate();
		gui.getUserButtonPressed("GameID er: " + game.getGameID(), "game succesfully loaded");
		
	}

	private void createPlayers() throws SQLException {
		CurrentMaxGameID = 0;
		CurrentMaxPlayerID = 0;
		//Navnet skal være på mindst 1 tegn
		String regexMorethanZero =".+";
		int y = 1;
		do {
			String gameName = gui.getUserString("Hvad vil du kalde dit spil? (mindst et tegn)");
			game.setGameName(gameName);	
			if(game.getGameName().matches(regexMorethanZero)){
				y = 0;
			}
		}
		while(y==1);

		int numberofplayers ;
		String valg = gui.getUserSelection("Vælg antal spillere", "2","3","4","5","6");
		numberofplayers = Integer.parseInt(valg);

		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Color> chosenColors = new ArrayList<Color>();
		ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW,
				Color.BLACK, Color.WHITE));
		ArrayList<String> colorString = new ArrayList<String>(Arrays.asList("Blå", "Rød", "Grøn", "Gul", "Sort", "Hvid"));

		for (int i = 0; i < numberofplayers; i++) {
			String name = gui.getUserString("Indtast navn: ");

			if (name.equals("")) 
				name = "Spiller "+(i+1);
			else if (names.contains(name)) 
				name += " "+(i+1);
			names.add(name);

			String pickedColor = gui.getUserSelection("Vælg din bils farve", colorString.toArray(new String[0]));
			CurrentMaxPlayerID++;


			int colorIndex = colorString.indexOf(pickedColor);
			Color color = colorList.get(colorIndex);
			chosenColors.add(color);

			colorString.remove(colorIndex);
			colorList.remove(colorIndex);

		}
		Player[] players = new Player[numberofplayers];
		for (int i = 0; i < numberofplayers; i++) {
			players[i] = new Player();
			players[i].setName(names.get(i)); 
			players[i].setCurrentPosition(game.getSpaces().get(0));
			players[i].setColor(chosenColors.get(i));
			players[i].setId(i);
			players[i].setId(i);
			game.addPlayer(players[i]);
		}

		view.playerUpdate();
		dao.create(game);
		String p = "GameID er: " + game.getGameID();
		gui.getUserString(p);


	}

	/**
	 * This method will initialize the GUI. It should be called after
	 * the players of the game are created. As of now, the initialization
	 * assumes that the spaces of the game fit to the fields of the GUI;
	 * this could eventually be changed, by creating the GUI fields 
	 * based on the underlying game's spaces (fields).
	 */
	public void initializeGUI() {		
		this. view = new View(game);

		gui = view.createGUI();
	}

	/**
	 * The main method to start the game with the given player.
	 * The game is started with the current player of the game;
	 * this makes it possible to resume a game at any point.
	 */
	public void play() {
		List<Player> players =  game.getPlayers();
		Player c = game.getCurrentPlayer();

		int current = 0;
		for (int i=0; i<players.size(); i++) {
			Player p = players.get(i);
			if (c.equals(p)) {
				current = i;
			}
		}

		boolean terminated = false;
		while (!terminated) {
			Player player = players.get(current);
			if (!player.isBroke()) {
				gui.getUserButtonPressed(player.getName()+", klik ok for at kaste","Kast");
				try {
					this.makeMove(player);
				} catch (PlayerBrokeException e) {
					// We could react to the player having gone broke
				}
			}

			// Check whether we have a winner
			Player winner = null;
			int countActive = 0;
			for (Player p: players) {
				if (!p.isBroke()) {
					countActive++;
					winner = p;
				}
			}
			if (countActive == 1) {
				gui.showMessage(
						"Spiller " + winner.getName() +
						" har vundet med " + winner.getBalance() +"kr.");
				break;
			} else if (countActive < 1) {
				// This can actually happen in very rare conditions and only
				// if the last player makes a stupid mistake (like buying something
				// in an auction in the same round when the last but one player went
				// bankrupt)
				gui.showMessage(
						"Alle spillerne er gået fallit.");
				break;

			}

			if (!player.isBroke()) {
				trade(player);
			}

			current = (current + 1) % players.size();
			game.setCurrentPlayer(players.get(current));
			if (current == 0) {
				String selection = gui.getUserSelection(
						"Runden er slut. Vil I fortsætte med at spille?",
						"ja",
						"nej");
				if (selection.equals("nej")) {
					List<Player> ranking = playerRanking(game.getPlayers());
					gui.showMessage("Rangordning for mest velhavende: "+ ranking.toString());
					selection = gui.getUserSelection("Vil du gemme spillet?", "ja", "nej");
					if (selection.equals("ja")) {
					}
					else {
						gui.showMessage("Vinderen er: "+ranking.get(0).toString()+". Spillet lukkes når du trykker ok.");
					}
					terminated = true;
				}
			}
			dao.update(game);
		}

		dispose();
	}

	/**
	 * This method implements a activity of a single move of the given player.
	 * It throws a {@link dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.PlayerBrokeException}
	 * if the player goes broke in this move. Note that this is still a very
	 * basic implementation of the move of a player; many aspects are still
	 * missing.
	 * 
	 * @param player the player making the move
	 * @throws PlayerBrokeException if the player goes broke during the move
	 */
	public void makeMove(Player player) throws PlayerBrokeException {

		boolean castDouble;
		int doublesCount = 0;
		do {
			int die1 = (int) (1 + 6.0*Math.random());
			int die2 = (int) (1 + 6.0*Math.random());
			player.set_throw(die1+die2);
			castDouble = (die1 == die2);
			gui.setDice(die1, die2);

			if (player.isInPrison()) {
				player.addPrisonTime();
				if (castDouble) {
					player.setInPrison(false);
					gui.showMessage(player.getName() + " forlader fængslet da han har kastet to ens!");
				} 
				else {
					String selection = gui.getUserSelection(player.getName()+", du kastede ikke to ens, hvad vil du?", "Betal kaution", "Brug frikort", "Vent til næste tur");

					if (selection.equals("Betal kaution")) {
						paymentToBank(player, game.JAIL_BAIL_PRICE);
						if (!player.isBroke()) {
							player.setInPrison(false);
						}
					}
					else if (selection.equals("Brug frikort")) {
						boolean cardUsed = false;
						for(Card card : player.getOwnedCards()){
							if(card instanceof OutOfJail)
							{
								player.setInPrison(false);
								player.removeOwnedCard(card);
								cardUsed = true;
								break;
							}
						}
						if (cardUsed) {
							gui.showMessage(player.getName() + " forlader fængslet da han har brugt sit frikort!");
						}
						else {
							gui.showMessage(player.getName() + " forbliver i fængslet da han ikke har noget frikort!");
						}
					}
					else {
						gui.showMessage(player.getName() + " forbliver i fængslet og venter til næste tur!");
					}

					if (player.isInPrison() && player.getPrisonTime() >= game.MAX_PRISON_TIME) {
						gui.showMessage(player.getName() + " har været i fængselet for længe, du tvinges nu til at betale kaution af "+game.JAIL_BAIL_PRICE+"kr.");
						paymentToBank(player, game.JAIL_BAIL_PRICE);
						if (!player.isBroke()) {
							player.setInPrison(false);
						}
					}
				}
			}



			if (castDouble) {
				doublesCount++;
				if (doublesCount > 2) {
					gui.showMessage("Spiller " + player.getName() + " har kastet to ens tre gange nu og havner derfor i fængslet!");
					gotoJail(player);
					return;
				}
			}

			if (!player.isInPrison()) {
				// make the actual move by computing the new position and then
				// executing the action moving the player to that space
				int pos = player.getCurrentPosition().getIndex();
				List<Space> spaces = game.getSpaces();
				int newPos = (pos + die1 + die2) % spaces.size();
				Space space = spaces.get(newPos);
				moveToSpace(player, space);
				if (castDouble) {
					gui.showMessage("Spiller " + player.getName() + " kastet to ens og får en ekstra tur.");
				}
			}
		} while (castDouble);
	}

	/**
	 * This method implements the activity of moving the player to the new position,
	 * including all actions associated with moving the player to the new position.
	 * 
	 * @param player the moved player
	 * @param space the space to which the player moves
	 * @throws PlayerBrokeException when the player goes broke doing the action on that space
	 */
	public void moveToSpace(Player player, Space space) throws PlayerBrokeException {
		int posOld = player.getCurrentPosition().getIndex();
		player.setCurrentPosition(space);

		if (posOld > player.getCurrentPosition().getIndex()) {
			gui.showMessage("Spiller " + player.getName() + " modtager 4.000kr. for at lande på eller passere Start!");
			this.paymentFromBank(player, game.getPassesStartMoney());
		}		
		gui.showMessage("Spiller " + player.getName() + " lander på " + space.getIndex() + ": " +  space.getName() + ".");

		// If the space is the IncomeTax one, we give 2 choices for payment
		if (space instanceof IncomeTax) {
			String msg = player.getName()+", du skal betale indkomstskat af 4.000kr. eller 10% af alle dine værdier (huse, hoteller, grunde og balance)";
			((IncomeTax)space).setPayByPercent(gui.getUserLeftButtonPressed(msg, "Betal 10%", "Betal 4.000kr.")); 
		}
		// Execute the action associated with the respective space. Note
		// that this is delegated to the field, which implements this action
		space.doAction(this, player);
	}	

	public void moveToIndex(Player player, int index) throws PlayerBrokeException {
		moveToSpace(player,game.getSpaces().get(index));
	}

	/**
	 * The method implements the action of a player going directly to jail.
	 * 
	 * @param player the player going to jail
	 */
	public void gotoJail(Player player) {
		gui.showMessage("Spiller " + player.getName() + "Gå direkte i fængslet uden at passere Start");
		player.setCurrentPosition(game.getSpaces().get(10));
		player.setInPrison(true);
	}

	/**
	 * The method implementing the activity of taking a chance card.
	 * 
	 * @param player the player taking a chance card
	 * @throws PlayerBrokeException if the player goes broke by this activity
	 */
	public  void takeChanceCard(Player player) throws PlayerBrokeException{
		Card card = game.drawCardFromDeck();
		gui.displayChanceCard(card.getText());
		gui.showMessage("Spiller " + player.getName() + " trækker et chancekort.");

		try {
			card.doAction(this, player);
		} finally {
			gui.displayChanceCard("done");
		}
	}

	/**
	 * This method implements the action returning a drawn card to the
	 * bottom of the deck.
	 * 
	 * @param card returned card
	 */
	public void returnChanceCardToDeck(Card card) {
		game.returnCardToDeck(card);
	}

	/**
	 * This method implements the activity where a player can obtain
	 * cash by selling houses back to the bank, by mortgaging own properties,
	 * or by selling properties to other players. This is called, whenever
	 * the player does not have enough cash available for an action. If
	 * he does not at least free the given amount of money, he will be broke;
	 * this is to help the player make the right choices to free enough money.
	 *  
	 * @param player the player
	 * @param amount the amount the player should have available after the act
	 */
	public void obtainCash(Player player, int amount) {
		int oldBalance, newBalance, needed, earned;
		oldBalance = player.getBalance();
		needed = amount-oldBalance;
		gui.showMessage(player.getName()+", du mangler "+needed+"kr. du får nu mulighed for at handle.");
		do {
			obtainCash(player);
			newBalance = player.getBalance();
			earned = newBalance-oldBalance;
		}
		while (!gui.getUserLeftButtonPressed("Du har optjent "+earned+"kr. hvad vil du?", "Fortsæt", "Sælg mere"));

		if (needed <= earned) {
			gui.showMessage(player.getName()+", du har fået optjent nok!");
			return;
		}
		needed = amount-newBalance;
		gui.showMessage(player.getName()+", du fik ikke optjent nok penge og mangler "+needed+"kr.");

	}

	/**
	 * The activity that gives the player the opportunity to sell houses, properties and mortgage properties.
	 * @param player the player
	 */
	public void obtainCash(Player player) {
		String selection = gui.getUserSelection("Hvad vil du optjene penge med?","Bolig","Grund","Pantsæt");

		if (selection.equals("Bolig")) {
			tradeSellHouse(player);
		}
		else if (selection.equals("Grund")) {
			tradeSellProperty(player);
		}
		else {
			tradeCashInMortgage(player);
		}
	}

	/**
	 * The activity that gives the player the opportunity to buy houses, properties and pay mortgage.
	 * @param player the player
	 */
	public void spendCash(Player player) {
		String selection = gui.getUserSelection("Hvad vil du bruge penge på?","Bolig","Spillers grund","Pant");

		if (selection.equals("Bolig")) {
			tradeBuyHouse(player);
		}
		else if (selection.equals("Pant")) {
			tradePayMortgage(player);
		}
		else {
			tradeBuyProperty(player);
		}
	}

	/**
	 * This method implements the activity of offering a player to buy
	 * a property. This is typically triggered by a player arriving on
	 * an property that is not sold yet. If the player chooses not to
	 * buy, the property will be set for auction.
	 * 
	 * @param property the property to be sold
	 * @param player the player the property is offered to
	 * @throws PlayerBrokeException when the player chooses to buy but could not afford it
	 */
	public void offerToBuy(Property property, Player player) throws PlayerBrokeException {

		String choice = gui.getUserSelection(
				"Spiller " + player.getName() +
				": Vil du købe " + property.getName() +
				" for " + property.getCost() + "kr.?",
				"ja",
				"nej");

		if (choice.equals("ja")) {
			try {
				paymentToBank(player, property.getCost());
			} catch (PlayerBrokeException e) {
				// if the payment fails due to the player being broke,
				// the an auction (among the other players is started
				auction(property);
				// then the current move is aborted by casting the
				// PlayerBrokeException again
				throw e;
			}
			player.addOwnedProperty(property);
			property.setOwner(player);
			return;
		}

		// In case the player does not buy the property an auction
		// is started
		auction(property);
	}

	/**
	 * This method implements a payment activity to another player,
	 * which involves the player to obtain some cash on the way, in case he does
	 * not have enough cash available to pay right away. If he cannot free
	 * enough money in the process, the player will go bankrupt.
	 * 
	 * @param payer the player making the payment
	 * @param amount the payed amount
	 * @param receiver the beneficiary of the payment
	 * @throws PlayerBrokeException when the payer goes broke by this payment
	 */
	public void payment(Player payer, int amount, Player receiver) throws PlayerBrokeException {
		if (payer.getBalance() < amount) {
			obtainCash(payer, amount);
			if (payer.getBalance() < amount) {
				playerBrokeTo(payer,receiver);
				throw new PlayerBrokeException(payer);
			}
		}
		gui.showMessage("Spiller " + payer.getName() + " betaler " +  amount + "kr. til spiller " + receiver.getName() + ".");
		payer.payMoney(amount);
		receiver.receiveMoney(amount);
	}

	/**
	 * This method implements the action of a player receiving money from
	 * the bank.
	 * 
	 * @param player the player receiving the money
	 * @param amount the amount
	 */
	public void paymentFromBank(Player player, int amount) {
		player.receiveMoney(amount);
	}

	/**
	 * This method implements the activity of a player making a payment to
	 * the bank. Note that this might involve the player to obtain some
	 * cash; in case he cannot free enough cash, he will go bankrupt
	 * to the bank. 
	 * 
	 * @param player the player making the payment
	 * @param amount the amount
	 * @throws PlayerBrokeException when the player goes broke by the payment
	 */
	public void paymentToBank(Player player, int amount) throws PlayerBrokeException{
		if (amount > player.getBalance()) {
			obtainCash(player, amount);
			if (amount > player.getBalance()) {
				playerBrokeToBank(player);
				throw new PlayerBrokeException(player);
			}

		}
		gui.showMessage("Spiller " + player.getName() + " betaler " +  amount + "kr. til banken.");
		player.payMoney(amount);
	}

	/**
	 * This method implements the activity of auctioning a property.
	 * 
	 * @param property the property which is for auction
	 */
	public void auction(Property property) {
		int currentBid = property.getCost()/2;
		int minNextBid = 100;
		boolean isABid = false;
		Player auctionWinner;
		List<Player> bidders = new ArrayList<Player>();
		gui.showMessage("Der afholdes en auktion for: "+property.getName());
		//Loops through all the players
		for (Player player : game.getPlayers()) {
			if (!player.isBroke()) {
				String msg = "Spiller "+player.getName()+" vil du deltage i auktionen?";
				//Ask if the player wants to bid if not he is removed from the list.
				if (gui.getUserLeftButtonPressed(msg, "ja", "nej")) {
					bidders.add(player);
				}
			}
		}

		while (bidders.size() > 1)
		{
			for(Player player : bidders.toArray(new Player[0])) {
				String msg = "Det nuværende bud er "+currentBid+"kr. "+player.getName()+", vil du byde over?";
				if (bidders.size() <= 1) {
					break;
				}
				else if (gui.getUserLeftButtonPressed(msg, "ja", "nej")) {
					currentBid = gui.getUserInteger("Indtast dit bud. Det skal være minimum "
							+minNextBid+"kr. højere end "+currentBid, currentBid+minNextBid, Integer.MAX_VALUE);
				}
				else {
					//Player is removed from the auction if he chooses no
					bidders.remove(player);
				}
			}
		}

		if (bidders.size() > 0) {
			auctionWinner = bidders.get(0);

			gui.showMessage("Tillykke "+auctionWinner.getName()+", du har vundet auktionen! "+
					"Du har købt: "+property.getName()+" for "+currentBid+"kr.");

			try {
				paymentToBank(auctionWinner, currentBid);
			} catch (PlayerBrokeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			auctionWinner.addOwnedProperty(property);
		}
		else {
			gui.showMessage("Ingen deltager i auktionen, denne annulleres derfor.");
		}

	}

	/**
	 * Action handling the situation when one player is broke to another
	 * player. All money and properties are the other player.
	 *  
	 * @param brokePlayer the broke player
	 * @param benificiary the player who receives the money and assets
	 */
	public void playerBrokeTo(Player brokePlayer, Player benificiary) {
		int amount = brokePlayer.getBalance();
		benificiary.receiveMoney(amount);
		brokePlayer.setBalance(0);
		brokePlayer.setBroke(true);


		for (Property property: brokePlayer.getOwnedProperties()) {
			//Check for houses and remove them all
			if (property instanceof RealEstate) {
				if (((RealEstate)property).isDevelopped()) {
					((RealEstate)property).clearHouses();
				}
			}

			property.setOwner(benificiary);
			benificiary.addOwnedProperty(property);
		}	
		brokePlayer.removeAllProperties();

		while (!brokePlayer.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(brokePlayer.getOwnedCards().get(0));
		}

		gui.showMessage("Spiller " + brokePlayer.getName() + "gik fallit og overfører alle hans "
				+ "likvider til " + benificiary.getName());
	}

	/**
	 * Action handling the situation when a player is broke to the bank.
	 * 
	 * @param player the broke player
	 */
	public void playerBrokeToBank(Player player) {

		player.setBalance(0);
		player.setBroke(true);

		for (Property property: player.getOwnedProperties()) {
			//Check for houses and remove them all
			if (property instanceof RealEstate) {
				if (((RealEstate)property).isDevelopped()) {
					((RealEstate)property).clearHouses();
				}
			}
			//Check if there is mortgage and remove if so
			if (property.isMortgaged()) {
				property.setMortgaged(false);
			}
			property.setOwner(null);
		}
		player.removeAllProperties();

		gui.showMessage("Spiller " + player.getName() + " gik fallit.");

		while (!player.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(player.getOwnedCards().get(0));
		}
	}

	public List<Player> getPlayers(){
		return game.getPlayers();
	}

	/**
	 * Method for disposing of this controller and cleaning up its resources.
	 */
	public void dispose() {
		if (!disposed) {
			disposed = true;
			view.dispose();
			// TODO we should also dispose of the GUI here. But this works only
			//      for my private version on the GUI and not for the GUI currently
			//      deployed via Maven (or other  official versions)
		}
	}

	/**
	 * Starts a trade dialogue.
	 * @param player the trading player
	 */
	public void trade(Player player) {
		boolean choice = gui.getUserLeftButtonPressed(player.getName()+" har du lyst til at handle?", "Ja", "Nej");
		//If the user chooses no, no trade action will happen...	
		while (choice) {
			String selection = gui.getUserSelection("Hvad vil du?", "Optjene penge", "Spendere penge");

			//If user chooses to spend money
			if (selection.equals("Spendere penge")) {
				spendCash(player);
			}
			//If user chooses to obtain money
			else {
				obtainCash(player);
			}

			choice = gui.getUserLeftButtonPressed(player.getName()+" har du lyst til at handle, igen?", "Ja", "Nej");
		}

	}

	/**
	 * Starts the mortgage dialogue
	 * @param player the mortgaging player
	 */
	private void tradeCashInMortgage(Player player) {
		Map<String,Property> mortgageableProperties = new HashMap<String,Property>();
		for (Property property: player.getOwnedProperties()) {
			if (!property.isMortgaged()) {
				if (property instanceof RealEstate) {
					if (!((RealEstate)property).isDevelopped())
						mortgageableProperties.put(property.getName(),property);
				}
				else {
					mortgageableProperties.put(property.getName(),property);
				}	
			}

		}

		if (mortgageableProperties.isEmpty()) {
			gui.showMessage("Du har ingen grunde der kan pantsættes.");
			return;
		}

		Property chosenProperty = pickProperty(mortgageableProperties, "Vælg den grund du vil pantsætte");

		if (chosenProperty != null) {
			chosenProperty.setMortgaged(true);
			paymentFromBank(player, chosenProperty.getMortgageValue());
		}


	}

	/**
	 * Starts the pay mortgage dialogue
	 * @param player the paying player
	 */
	private void tradePayMortgage(Player player) {
		Map<String,Property> demortgageableProperties = new HashMap<String,Property>();
		for (Property property: player.getOwnedProperties()) {
			if (property.isMortgaged()) {
				demortgageableProperties.put(property.getName(),property);
			}

		}

		if (demortgageableProperties.isEmpty()) {
			gui.showMessage("Du har ingen pantsatte grunde.");
			return;
		}

		Property chosenProperty = pickProperty(demortgageableProperties, "Vælg den grund du vil betale pant for.");

		if (chosenProperty != null) {
			int amount = (int) (chosenProperty.getMortgageValue()*1.1f);
			try {
				paymentToBank(player, amount);
			} catch (PlayerBrokeException e) {
				e.printStackTrace();
			}
			chosenProperty.setMortgaged(false);
		}
	}


	/**
	 * Starts a buy house dialogue.
	 * @param buyer the buying player
	 */
	private void tradeBuyHouse(Player buyer) {
		Map<String,Property> developableProperties = new HashMap<String,Property>();
		for (Property property: buyer.getOwnedProperties()) {
			if (property instanceof RealEstate) {
				if (!property.isMortgaged()) {
					if (isColorgroupComplete(buyer.getOwnedProperties(), property.getColor())){
						if (((RealEstate)property).getHouses() < ((RealEstate)property).getMAX_HOUSES())
							developableProperties.put(property.getName(),property);
					}
				}
			}	
		}

		if (developableProperties.isEmpty()) {
			gui.showMessage("Du har ingen grunde at bygge på.");
			return;
		}

		RealEstate chosenProperty = (RealEstate)pickProperty(developableProperties, "Vælg den grund du vil udvikle.");

		if (chosenProperty != null) {
			try {
				paymentToBank(buyer, chosenProperty.getHousePrice());
				game.addHouses(1);
				chosenProperty.addHouse();
			} catch (PlayerBrokeException e) {
				e.printStackTrace();
			} catch (NoHousesAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				gui.showMessage("Der er ikke flere ledige bygninger, prøv igen senere.");
				paymentFromBank(buyer, chosenProperty.getHousePrice());
			}
			
		}
	}

	/**
	 * Starts a buy property dialogue.
	 * @param buyer the buying player
	 */
	private void tradeBuyProperty(Player buyer) {
		Map<String, Player> players = new HashMap<String, Player>();

		for (Player player: game.getPlayers()) {
			//Skal lige testes
			if (!player.equals(buyer)) {
				players.put(player.getName(), player);
			}
		}

		String[] choices = players.keySet().toArray(new String[0]);

		String choice = gui.getUserSelection("Vælg den spiller du vil købe fra.", choices);

		Player seller = players.get(choice);

		Map<String,Property> buyableProperties = new HashMap<String,Property>();
		for (Property property: seller.getOwnedProperties()) {
			if (property instanceof RealEstate) {
				if (!((RealEstate)property).isDevelopped())
					buyableProperties.put(property.getName(),property);
			}
			else {
				buyableProperties.put(property.getName(),property);
			}	
		}

		if (buyableProperties.isEmpty()) {
			gui.showMessage(seller.getName()+", har ingen salgsbare grunde.");
			return;
		}

		Property chosenProperty = pickProperty(buyableProperties, "Vælg den grund du vil købe.");

		if (chosenProperty != null) {
			Integer price = gui.getUserInteger("Indtast din pris.");

			moveOwnership(buyer, chosenProperty, price, seller);
		}
	}

	/**
	 * Starts sell house dialogue.
	 * @param seller selling player
	 */
	private void tradeSellHouse(Player seller) {
		Map<String,Property> undevelopableProperties = new HashMap<String,Property>();
		for (Property property: seller.getOwnedProperties()) {
			if (property instanceof RealEstate) {
				if (((RealEstate)property).isDevelopped())
					undevelopableProperties.put(property.getName(),property);
				//TODO tjek for om man har alle grunde af samme farve.
			}	
		}

		if (undevelopableProperties.isEmpty()) {
			gui.showMessage("Du har ingen grunde med huse.");
			return;
		}

		RealEstate chosenProperty = (RealEstate)pickProperty(undevelopableProperties, "Vælg den grund du vil uudvikle.");

		if (chosenProperty != null) {
			chosenProperty.removeHouse();
			paymentFromBank(seller, chosenProperty.getHousePrice());
		}

	}

	/**
	 * Starts a sell Property dialogue.
	 * @param seller the selling player
	 */
	private void tradeSellProperty(Player seller) {
		Map<String, Player> players = new HashMap<String, Player>();

		for (Player player: game.getPlayers()) {
			//Skal lige testes
			if (!player.equals(seller)) {
				players.put(player.getName(), player);
			}
		}

		String[] choices = players.keySet().toArray(new String[0]);

		String choice = gui.getUserSelection("Vælg den spiller du vil sælge til.", choices);

		Player buyer = players.get(choice);

		Map<String,Property> sellableProperties = new HashMap<String,Property>();
		for (Property property: seller.getOwnedProperties()) {
			if (property instanceof RealEstate) {
				if (!((RealEstate)property).isDevelopped())
					sellableProperties.put(property.getName(),property);
			}
			else {
				sellableProperties.put(property.getName(),property);
			}	
		}
		if (sellableProperties.isEmpty()) {
			gui.showMessage("Du har ingen salgsbare grunde.");
			return;
		}
		Property chosenProperty = pickProperty(sellableProperties, "Vælg den grund du vil sælge.");

		if (chosenProperty != null) {
			Integer price = gui.getUserInteger("Indtast din pris.");

			moveOwnership(buyer, chosenProperty, price, seller);
		}
	}

	/**
	 * Handles the transaction of the ownership of a property from the seller to the buyer at the given prices.
	 * @param buyer player buying the property
	 * @param property the asset
	 * @param price the agreed price
	 * @param seller player selling the property
	 */
	private void moveOwnership(Player buyer, Property property, int price, Player seller) {
		try {
			payment(buyer, price, seller);
		} catch (PlayerBrokeException e) {
			e.printStackTrace();
		}

		seller.removeOwnedProperty(property);
		buyer.addOwnedProperty(property);
		property.setOwner(buyer);


	}

	/**
	 * Starts a list picking dialog with the given properties from the Map.
	 * @param properties Map with the properties and names as key value
	 * @param msg the dialog message shown to the player
	 * @return the picked property or <code>null</code> if pick is aborted.
	 */
	private Property pickProperty(Map<String, Property> properties, String msg) {
		String[] choices = properties.keySet().toArray(new String[0]);

		String choice;
		Property pickedProperty;

		do {
			choice = gui.getUserSelection(msg, choices);

			pickedProperty = properties.get(choice);

			choice = gui.getUserButtonPressed("Er du sikker?", "Ja","Vælg anden","Anuller");
		}
		while(choice.equals("Vælg anden"));

		if (!choice.equals("Ja")) {
			pickedProperty = null;
		}

		return pickedProperty;
	}

	/**
	 * Used to check weather the player owns all the streets of same colorgroup
	 * @param properties
	 * @param colorGroup
	 * @return
	 */
	private boolean isColorgroupComplete (Set<Property> properties, Color colorGroup) {
		int count = 0;

		for (Property property : properties) {
			if (property instanceof RealEstate) {
				if (property.getColor().equals(colorGroup)) {
					count++;
				}
			}
		}

		if (count == 3) {
			return true;
		}
		else if (count == 2) {
			if (colorGroup.equals(Color.darkGray)||colorGroup.equals(Color.cyan)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param players
	 * @return
	 */
	public List<Player> playerRanking(List<Player> players) {
		List<Player> ranking = new ArrayList<Player>();
		int max = 0;
		Player maxPlayer = null;
		while(ranking.size() < players.size()) {
			for (Player player : players) {
				if (!ranking.contains(player)) {
					int netWorth = player.getAssetsValue();
					if(max <= netWorth) {
						max = netWorth;
						maxPlayer = player;
					}
				}
			}
			if (maxPlayer != null) ranking.add(maxPlayer);
			max = 0;
		}

		return ranking;

	}

}
