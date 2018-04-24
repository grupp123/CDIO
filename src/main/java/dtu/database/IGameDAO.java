package dtu.database;

import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;

public interface IGameDAO {

	boolean create(Game game);
	
	boolean update(Game game);
	
	boolean load(Game game, int gameID);
	
	ArrayList<Integer> readGame(int Id);
	
	
}
