package dk.dtu.compute.se.pisd.database;

import java.sql.SQLException;
import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
public interface IGameDAO {

	void create(Game game) throws SQLException;
	
	void update(Game game) throws SQLException;
	
	void load(Game game, int gameID) throws SQLException;
	
	int getGameIdFromName(String str) throws SQLException;

	ArrayList<String> activeGames() throws SQLException;
}
