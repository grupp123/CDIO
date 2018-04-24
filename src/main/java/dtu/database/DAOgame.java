package dtu.database;

import java.time.Instant;

public class DAOgame implements DAOInterfaceGame {
	
	public static int gameID = 0;
	
	private Connector c = new Connector();
	
	public DAOgame(Connector c) {
		c = this.c;
		
		
		
	}

	@Override
	public void createGame(String gameName) {
		
		try
		{
		String q = "call create_game('" + gameName + "');";
		c.doUpdate(q);
		this.gameID++;
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}
	public static void getGameID() {
		gameID++;
	}
	public int getID() {
		return gameID;
	}
	}
	


