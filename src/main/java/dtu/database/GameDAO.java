package dtu.database;

import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;

public class GameDAO implements IGameDAO {
	
	private Connector connector;
	
	public GameDAO(Connector connector) {
		this.connector = connector;
	}

	@Override
	public boolean create(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(Game game, int gameID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Integer> readGame(int Id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
