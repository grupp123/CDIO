package dtu.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DTOplayer {

	private String name;
	private int position;
	private int balance;

	public DTOplayer(String name, int position, int balance) {
		this.name = name;
		this.position = position;
		this.balance = balance;		
	}

	public void name() {
		//getPlayerName(1);
	}

}

