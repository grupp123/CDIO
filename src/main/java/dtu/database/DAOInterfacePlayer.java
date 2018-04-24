package dtu.database;

public interface DAOInterfacePlayer {
	
	public void createPlayer(int gameID, String playerName);
	
	public int getMaxPlayerID();

}
