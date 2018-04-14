package dtu.cdio;

import java.sql.ResultSet;
import java.sql.SQLException;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;

public class DTO {

	Connector c;

	public DTO(Connector c) {
		this.c = c;
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




	public Connector getC() {
		return c;
	}
	public void setC(Connector c) {
		this.c = c;
	}

}
