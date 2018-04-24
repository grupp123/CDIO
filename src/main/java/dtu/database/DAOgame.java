package dtu.database;

import java.time.Instant;

public class DAOgame implements DAOInterfaceGame {
	
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
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	}
	


