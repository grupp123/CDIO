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
			c.doUpdate("use matadordemo");
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
						System.out.print(" - ");
						q += " - ";
					}
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
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

		
		
		
		
	
	
	public String activeGames() throws SQLException {

		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT GameID FROM game");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int j = 1; j <= columnsNumber; j++) {
					if (j > 1) {
						System.out.print(" - ");
						q += " - ";
					}
					String columnValue = rs.getString(j);
					//System.out.print(columnValue + " " + rsmd.getColumnName(j));
					q += columnValue;// + " " + rsmd.getColumnName(j);
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

	public void createPlayerInGame(int i, String s) {

		try {
			c.doUpdate("insert into player(gameid, playername) values (" + i + ", '" + s + "')" );
		} catch (Exception e) {
			System.out.println("error" + e);
		}
	} 

	public String getPlayerName(int i) throws SQLException{

		String q = "";
		try {
			ResultSet rs = c.doQuery("select playername from player where playerID = " + i);
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


}
