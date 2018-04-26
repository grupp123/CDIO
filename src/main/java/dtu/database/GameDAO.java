package dtu.database;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.GameController;
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
			con.setAutoCommit(false);
			ResultSet rs = connector.doQuery("SELECT * FROM game where gameid = " + gameID);

			String str = rs.getString(1);

			game.setGameID(gameID);



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


	//-----------------private methods-----------------//

	private String getPlayerName(int id) throws Exception {
		ResultSet rs = connector.doQuery("SELECT playername FROM player WHERE id = " + id);
		try {
			if (!rs.first()) throw new Exception("id " + id + " findes ikke");
			return (rs.getString("name"));
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	private int getPlayerBalance(int id) throws Exception {
		ResultSet rs = connector.doQuery("SELECT balance FROM player WHERE id = " + id);
		try {
			if (!rs.first()) throw new Exception("id " + id + " findes ikke");
			return (rs.getInt("account"));
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	private void deleteTable(String game) throws Exception {
		connector.doUpdate("drop table " + game);
	}



	public String toString(String s) {
		String q = "";
		try {
			ResultSet rs = connector.doQuery(s);			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						System.out.print(" - ");
						q += " - ";
					}
					String columnValue = rs.getString(j);
					System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue + " " + rsmd.getColumnName(j);
				}
				System.out.println("");
				q += "\n";
			}
			System.out.println("");
			q += "\n";
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}

	} 


	private void print(String query) {
		try {
			ResultSet rs = connector.doQuery(query);			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.println(query);
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) System.out.print(" - ");
					String columnValue = rs.getString(j);
					System.out.print(columnValue + " " + rsmd.getColumnName(j));
				}
				System.out.println("");
			}
			System.out.println("");
		}catch (Exception e) {
			System.out.println(e);
		}
	}

	private void insert(Player p) throws Exception {
		try {
			connector.doUpdate("INSERT INTO Player(ID, name, account) "
					+ "VALUES(" +p.getId()+ ", '" + p.getName() + "', " + p.getBalance() + ")" );
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	private void createPlayerTable() throws Exception {
		try {
			connector.doUpdate("CREATE TABLE player ( id INTEGER PRIMARY KEY, name TEXT, account int(10))");
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	private void createFieldsTable() throws Exception {
		try {
			connector.doUpdate("CREATE TABLE fields ( number INTEGER, title TEXT, player INTEGER, FOREIGN KEY(player) REFERENCES player (id))");
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	public String activePlayerBalanceInGame(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = connector.doQuery("SELECT balance FROM player where gameid = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						q += ", ";
					}
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
				}
				q += ", ";
			}
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}

	}

	public String activePlayerposInGame(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = connector.doQuery("SELECT position FROM player where gameid = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						q += ", ";
					}
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
				}
				q += ", ";
			}
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}

	}

	public String allInfoActivePlayersInGame(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM player where gameid = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						q += " - ";
					}
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
				}
				q += "\n";
			}
			q += "\n";
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}

	}

	public String[] getInfoArrayPlayer(int i) throws SQLException{

		String[] q = new String[8];
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM player where playerid = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for (int j = 1; j <= columnsNumber; j++) {
				q[j-1] = rs.getString(j);
				System.out.print(columnsNumber); //Value + " " + rsmd.getColumnName(j));
				//q += columnValue;// + " " + rsmd.getColumnName(j);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return q;

	}






	public ArrayList<String> activeGames() throws SQLException {

		String q = "";
		ArrayList<String> l = new ArrayList<>();

		try {
			ResultSet rs = connector.doQuery("SELECT GameName FROM game");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						q += " - ";
					}
					l.add(rs.getString(j));
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
				}

				q += ", ";
			}
			//q += "\n";
			return l;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public int getGameIdFromName(String str) throws Exception {

		ResultSet rs = connector.doQuery("SELECT gameid FROM game WHERE gamename = '" + str + "';");
		try {
			if (!rs.first()) throw new Exception("id " + str + " findes ikke");
			return (rs.getInt(1));
		}
		catch (SQLException e) {throw new Exception(e); }
	}

	public ArrayList<String> getNames(int gameid) {
		ArrayList<String> l = new ArrayList<>();
		try {
			ResultSet rs = connector.doQuery("SELECT playername from player where gameid = " + gameid);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				l.add(rs.getString(1));
			}
			return l;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public ArrayList<Integer> getPositionOfPlayers(int gameID) {
		ArrayList<Integer> l = new ArrayList<>();
		try {
			ResultSet rs = connector.doQuery("SELECT position from car where gameid = " + gameID + ";");
			while (rs.next()) {
				int i = rs.getInt(1);
				Integer o = (i);
				l.add(o);
			}
			return l;
		} catch (Exception e) {
			
			System.out.println("SELECT playername from player where gameid = ... virker ikke " + e);
			return null;
		}
	}

	public ArrayList<Integer> getBalanceOfPlayers(int gameID) {
		ArrayList<Integer> l = new ArrayList<>();
		try {
			ResultSet rs = connector.doQuery("SELECT balance from player where gameid = " + gameID + ";");
			while (rs.next()) {
				int i = rs.getInt(1);
				Integer o = (i);
				l.add(o);
			}
			return l;
		} catch (Exception e) {
			
			System.out.println("SELECT playername from player where gameid = ... virker ikke " + e);
			return null;
		}
	}
}
