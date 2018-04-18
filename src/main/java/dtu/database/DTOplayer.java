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
	
	public int getBalance() {
		return balance;
	}public String getName() {
		return name;
	}public void setBalance(int balance) {
		this.balance = balance;
	}public int getPosition() {
		return position;
	}public void setName(String name) {
		this.name = name;
	}public void setPosition(int position) {
		this.position = position;
	}

}

