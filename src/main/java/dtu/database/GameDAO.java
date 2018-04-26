package dtu.database;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

public class GameDAO implements IGameDAO {
	
	private Connector connector;
	
	public GameDAO(Connector connector) {
		this.connector = connector;
	}

	@Override
	public boolean create(Game game) {
		java.sql.Connection con = connector.getConnection();
		int i = 0;
		try {
			//her opretter jeg spillet inde i databasen, og sørger for at få gameID 
			//tilbage så den kan puttes ind i game
			con.setAutoCommit(false);
			connector.doUpdate("insert into game(gameName) values('"+game.getGameName() +"');");
			ResultSet rs = connector.doQuery("select max(gameID) from game;");
			while(rs.next()) {
				i = rs.getInt("max(gameID)");
				
			}
			game.setGameID(i);
			
			//spillerne oprettes i databasen med deres ID, gameID og navn
			for (int j = 0; j<game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int id = p.getId();
			int gid = game.getGameID();
			String name = p.getName();
			connector.doUpdate("insert into player(playerID, gameID, playerName) values("+ id +", " + gid + ", '" + name + "');");
			}
			
			//nu oprettes de tilhørende biler til hver spiller
			for (int j = 0; j<game.getPlayers().size(); j++) {
				Player p = game.getPlayers().get(j);
				int playerid = p.getId();
				int color = p.getColor().getRGB();
				int gid = game.getGameID();
				String name = p.getName();
				connector.doUpdate("insert into car(carColor, playerID, gameID) values("+ color +", " + playerid + ", " + gid + ");");
				}
			//nu oprettes alle de ejelige felter 
			
			
			con.commit();
			con.setAutoCommit(true);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem med DB");
		}
		return false;
	}

	@Override
	public boolean update(Game game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean load(Game game, int gameID) {
		
		java.sql.Connection con = connector.getConnection();
		int i = 0;
		try {
			//her opretter jeg spillet inde i databasen, og sørger for at få gameID 
			//tilbage så den kan puttes ind i game
			con.setAutoCommit(false);
			connector.doUpdate("insert into game(gameName) values('"+game.getGameName() +"');");
			ResultSet rs = connector.doQuery("select max(gameID) from game;");
			while(rs.next()) {
				i = rs.getInt("max(gameID)");
				
			}
			game.setGameID(i);
			
			//spillerne oprettes i databasen med deres ID, gameID og navn
			for (int j = 0; j<game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int id = p.getId();
			int gid = game.getGameID();
			String name = p.getName();
			connector.doUpdate("insert into player(playerID, gameID, playerName) values("+ id +", " + gid + ", '" + name + "');");
			}
			
			//nu oprettes de tilhørende biler til hver spiller
			for (int j = 0; j<game.getPlayers().size(); j++) {
				Player p = game.getPlayers().get(j);
				int playerid = p.getId();
				int color = p.getColor().getRGB();
				int gid = game.getGameID();
				String name = p.getName();
				connector.doUpdate("insert into car(carColor, playerID, gameID) values("+ color +", " + playerid + ", " + gid + ");");
				}
			//nu oprettes alle de ejelige felter 
			
			
			con.commit();
			con.setAutoCommit(true);
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem med DB");
		}
		return false;
	}


	@Override
	public ArrayList<Integer> readGame(int Id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
