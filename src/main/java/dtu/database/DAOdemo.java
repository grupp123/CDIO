package dtu.database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

public class DAOdemo {
	Connector c;


	public DAOdemo(Connector c) {
		this.c = c;
	}

	public void useMatadorDemo() throws SQLException {
		try {
			c.doUpdate("use MatadorDemo");
		} catch (Exception e) {
			System.out.println("SQL FEJL - database ikke oprettet" + e);
		}
	}

	public void startGame() throws SQLException{
		try {
			c.doUpdate("INSERT INTO game VALUES()");
		} catch (Exception e) {
			System.out.println("game ikke oprettet" + e);		
		}
	}

	public String activePlayers() throws SQLException{

		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT playerid, playername FROM player");
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



	public String activePlayersInGame(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT playerid, playername FROM player where gameid = " + i);
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

	public String allInfoActivePlayersInGame(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT * FROM player where gameid = " + i);
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
			ResultSet rs = c.doQuery("SELECT * FROM player where playerid = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
				for (int j = 1; j <= columnsNumber; j++) {
					q[j-1] = rs.getString(j);
					System.out.print(columnsNumber); //Value + " " + rsmd.getColumnName(j));
					//q += columnValue;// + " " + rsmd.getColumnName(j);
			}
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}






	public String activeGames() throws SQLException {

		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT GameID FROM game");
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

				q += ", ";
			}
			//q += "\n";
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}

	}

	public void createPlayerInGame (int i, String s) throws SQLException{

		try {
			c.doUpdate("insert into player(gameid, playername) values (" + i + ", '" + s + "')" );
		} catch (Exception e) {
			System.out.println("error" + e);
		}
	} 


	public void deleteGame(int i) throws SQLException {

		try {
			c.doUpdate("delete from game where gameid = " + i);
		} catch (Exception e) {
			System.out.println("error game dosent exist " + e);
		}
	} 

	public void deletePlayer(int i) throws SQLException {
		try {
			c.doUpdate("delete from player where playerid = " + i);
		} catch (Exception e) {
			System.out.println("error game dosent exist " + e);
		}
	} 



	public String getPlayerName(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = c.doQuery("select playername from player where playerID = " + i);
			while (rs.next()) {
					String columnValue = rs.getString(1);
					q += columnValue;
			}
			return q;
		}catch (Exception e) {
			System.out.println(e);
			return "error";
		}
	}

	public int getPlayerBalance(int i) throws SQLException{

		int q = 0;
		try {
			ResultSet rs = c.doQuery("select balance from player where playerID = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				int columnValue = rs.getInt(1);
				q = columnValue;
			}
			return q;
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	
	public int getPlayerPosition(int i) throws SQLException{

		int q = 0;
		try {
			ResultSet rs = c.doQuery("select position from player where playerID = " + i);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				int columnValue = rs.getInt(1);
				q = columnValue;
			}
			return q;
		} catch (Exception e) {
			System.out.println(e);
			return -1;
		}
	}



}
