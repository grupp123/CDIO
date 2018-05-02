package dk.dtu.compute.se.pisd.database;

public class Repository {

	private static Repository instance;
	private Connector connector;
	private GameDAO gameDAO;
	
	private Repository(){
		connector = new Connector();
	}
	
	public static Repository getInstance() {
		if (instance == null) {
			instance = new Repository();
		}
		return instance;
	}
	
	public IGameDAO game() {
		if (gameDAO == null) {
			gameDAO = new GameDAO(connector);
		}
		return gameDAO;
	}
	
}
