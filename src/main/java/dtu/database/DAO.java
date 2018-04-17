package dtu.database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

public class DAO {
	Connector c;

	public DAO(Connector c) {
		this.c = c;
	}
	

	public String getPlayerName(int id) throws Exception {
		ResultSet rs = c.doQuery("SELECT * FROM player WHERE id = " + id);
		try {
			if (!rs.first()) throw new Exception("id " + id + " findes ikke");
			return (rs.getString("name"));
		}
		catch (SQLException e) {throw new Exception(e); }
	}
	
	public int getPlayerBalance(int id) throws Exception {
		ResultSet rs = c.doQuery("SELECT * FROM player WHERE id = " + id);
		try {
			if (!rs.first()) throw new Exception("id " + id + " findes ikke");
			return (rs.getInt("account"));
		}
		catch (SQLException e) {throw new Exception(e); }
	}
	
	public void deleteTable(String game) throws Exception {
		c.doUpdate("drop table " + game);
	}
	
	

	public String toString(String s) {
		String q = "";
		try {
			ResultSet rs = c.doQuery("SELECT * FROM player WHERE id = " + s);			
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




	//c.doQuery("use university");
	//ResultSet rs;
	//rs = c.doQuery("SELECT * FROM players WHERE id = " + s);
	//Player p = new Player();
	//p.setName(rs.getString(2));
	//p.setBalance(rs.getInt(3));
	//return p;



	public void print(String query) {
		try {
			ResultSet rs = c.doQuery(query);			
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
	
	public void insert(Player p) throws Exception {
		try {
			c.doUpdate("INSERT INTO Player(ID, name, account) "
					+ "VALUES(" +p.getId()+ ", '" + p.getName() + "', " + p.getBalance() + ")" );
		}
		catch (SQLException e) {throw new Exception(e); }
	}
	
	public void createPlayerTable() throws Exception {
		try {
			c.doUpdate("CREATE TABLE player ( id INTEGER PRIMARY KEY, name TEXT, account int(10))");
		}
		catch (SQLException e) {throw new Exception(e); }
	}
	
	public void createFieldsTable() throws Exception {
		try {
			c.doUpdate("CREATE TABLE fields ( number INTEGER, title TEXT, player INTEGER, FOREIGN KEY(player) REFERENCES player (id))");
		}
		catch (SQLException e) {throw new Exception(e); }
	}
	
	
	
}
