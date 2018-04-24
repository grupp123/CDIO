package dtu.database;

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

	
}
