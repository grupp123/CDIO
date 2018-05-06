package dk.dtu.compute.se.pisd.database;

import java.sql.SQLException;
import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;

 /**
  * this class implements the DAO and connects the program to the database
  *  
  * @author Alexander Kjeldsen, s165477@student.dtu.dk, Jacob Jørgensen
  *
  */


public interface IGameDAO {
	
	/**
	 * creates a new reference to update and load in the database.
	 * @param game
	 * @author Jacob Jørgensen
	 * @throws SQLException
	 */

	void create(Game game) throws SQLException;
	
	/**
	 * updates each game automatically and saves the progress to the database.
	 * this is so that the user doesn't have to get prompted. 
	 * @param game
	 * @author Jacob Jørgensen
	 * @throws SQLException
	 */
	
	void update(Game game) throws SQLException;
	
	/**
	 * loads the database from a user prompt. gets a user selection of available games  to load.
	 * 
	 * @param game
	 * @param gameID
	 * @author Jacob Jørgensen
	 * @throws SQLException
	 */
	
	void load(Game game, int gameID) throws SQLException;
	
	/**
	 * used for the method load. retrieves gameid to load and update the database for a specific id.
	 * @param str
	 * @return
	 * @throws SQLException
	 */	
	int getGameIdFromName(String str) throws SQLException;

	ArrayList<String> activeGames() throws SQLException;
}
