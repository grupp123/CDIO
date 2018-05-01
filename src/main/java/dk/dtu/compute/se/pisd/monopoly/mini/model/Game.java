package dk.dtu.compute.se.pisd.monopoly.mini.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.dtu.compute.se.pisd.designpatterns.Subject;
import dk.dtu.compute.se.pisd.monopoly.mini.model.exceptions.NoHousesAvailableException;

/**
 * Represents the top-level element of a Monopoly game's state. In order
 * to use this model with the MVC-pattern, it extends the
 * {@link dk.dtu.compute.se.pisd.designpatterns.Subject} of the observer
 * design pattern.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk, Nils Rasamoel
 *
 */
public class Game extends Subject {

	private List<Space> spaces = new ArrayList<Space>();

	private List<Card> cardDeck = new ArrayList<Card>();

	private List<Player> players = new ArrayList<Player>();

	private int gameID;

	private String gameName;

	private Player current;

	private final int PASSEDSTARTMONEY = 4000;

	public final int MAX_HOUSES = 52;

	public final int JAIL_BAIL_PRICE = 1000;

	public final int MAX_PRISON_TIME = 3;

	private int houses;


	/**
	 * Returns the gameID used in the database.
	 * @return the <code>int</code> gameID.
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * Used to set the gameID used in the database.
	 * @param gameID the game's id integer.
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * Returns the name of the game (gameName)
	 * @return <code>String</code> the game's name.
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Used to set the name of the game (gamename).
	 * @param gameName the game's name.
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}


	/**
	 * Returns the amount players receive upon passing start
	 * 
	 * @return an int with the value 4000 (money when you passing start)
	 */	
	public int getPassesStartMoney() {
		return PASSEDSTARTMONEY;
	}

	/**
	 * Returns a list of all the games spaces.
	 * 
	 * @return an unmodifiable list of the games spaces
	 */
	public List<Space> getSpaces() {
		return Collections.unmodifiableList(spaces);
	}

	/**
	 * Sets all the spaces of the game. Note that the provided
	 * list of spaces is copied, so that they cannot be changed
	 * without the game being aware of the change.
	 * 
	 * @param spaces the list of spaces
	 */
	public void setSpaces(List<Space> spaces) {
		this.spaces = new ArrayList<Space>(spaces);
		notifyChange();
	}

	/**
	 * Adds a space to the game at the end.
	 * 
	 * @param space the added space
	 */
	public void addSpace(Space space) {
		space.setIndex(spaces.size());
		spaces.add(space);
		notifyChange();
	}

	/**
	 * Returns a list of the cards in the current deck.
	 * 
	 * @return an unmodifiable list of all the cards currently in the deck
	 */
	public List<Card> getCardDeck() {
		return Collections.unmodifiableList(cardDeck);
	}

	/**
	 * Removes the topmost card from the deck and returns it.
	 * 
	 * @return the topmost card of the deck
	 */
	public Card drawCardFromDeck() {
		// TODO should be more defensive
		Card card = cardDeck.remove(0);
		notifyChange();
		return card;

	}

	/**
	 * Add the given card to the bottom of the deck.
	 * 
	 * @param card the card added to the bottom of the deck.
	 */
	public void returnCardToDeck(Card card) {
		cardDeck.add(card);
		notifyChange();
	}

	/**
	 * Uses the provided list of cards as the new deck. The
	 * list will be copied in order to avoid, changes on it
	 * without the game being aware of it.
	 *  
	 * @param cardDeck the new deck of cards
	 */
	public void setCardDeck(List<Card> cardDeck) {
		this.cardDeck = new ArrayList<Card>(cardDeck);
		notifyChange();
	}


	/**
	 * Shuffles the cards in the deck.
	 */
	public void shuffleCardDeck() {
		Collections.shuffle(cardDeck);
		// This notification is probably not needed, but for
		// completeness sake, we have it here
		notifyChange();
	}

	/**
	 * Returns all the players of the game as an unmodifiable list.
	 * 
	 * @return a list of the current players
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	/**
	 * Sets the list of players. The list of players is actually copied
	 * in order to avoid the list being modified without the game being
	 * aware of it.
	 * 
	 * @param players the list of players
	 */
	public void setPlayers(List<Player> players) {
		this.players = new ArrayList<Player>(players);
		notifyChange();
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param player the player to be added.
	 */
	public void addPlayer(Player player) {
		players.add(player);
		notifyChange();
	}

	/**
	 * Returns the current player of the game. This is the player
	 * who's turn it is to do the next move (or currently is doing a move).
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		if (current == null) {
			current = players.get(0);
		}
		return current;
	}

	/**
	 * Sets the current player. It is required that the player is a
	 * player of the game already; otherwise an IllegalArumentException
	 * will be thrown.
	 * 
	 * @param player the new current player
	 */
	public void setCurrentPlayer(Player player) throws IllegalArgumentException{
		if (player != null && players.contains(player)) {
			current = player;
		} else {
			throw new IllegalArgumentException("Player is not in the game!");
		}
		notifyChange();
	}

	public int getHouses() {
		return houses;
	}

	/**
	 * Adds the given number of houses to the house count in the game.
	 * 
	 * @param houses the nb of houses to be added.
	 * @throws NoHousesAvailableException thrown if the given value is higher than the nb of avaiable houses.
	 */
	public void addHouses(int houses) throws NoHousesAvailableException {
		if (this.houses + houses <= MAX_HOUSES) {
			this.houses += houses;
		}
		else {
			throw new NoHousesAvailableException(this);
		}
	}

	/**
	 * Removes card at index 20. This will only work if it was deliberately inserted at index 20 before the cards are shuffled.
	 */
	public void removeJailCard() {
		cardDeck.remove(20);
	}



}
