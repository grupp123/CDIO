package dtu.database;

import java.sql.ResultSet;

public class DAOplayer implements DAOInterfacePlayer {
	
private Connector c = new Connector();
	
	public DAOplayer(Connector c) {
		c = this.c;
	}
		

	@Override
	public void createPlayer(int gameID, String playerName) {
	try {
		String q = "call create_player("+gameID+ ", '" + playerName +"');";
		c.doUpdate(q);
	}
	catch(Exception ex) {
		ex.getStackTrace();
	}
		
	}

	@Override
	public int getMaxPlayerID() {
		ResultSet max; 
		int i = 11;
		try {
			String q = "select max(playerID) from player;";
			max = c.doQuery(q);
			i = max.getInt(1);
		}
		catch(Exception ex) {
			ex.getMessage();
		}
		return i;
	}

	
}
