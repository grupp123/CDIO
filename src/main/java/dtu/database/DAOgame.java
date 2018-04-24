package dtu.database;

import java.sql.ResultSet;

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
		
		@Override
	public int getMaxGameID() {
			ResultSet max;
			int i = 0;
		try {
			String q = "select max(gameID) from game;";
			max = c.doQuery(q);
			i = max.getInt("max(gameID)");
			System.out.println(i);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		return i;
		
	}
	
	}
	


