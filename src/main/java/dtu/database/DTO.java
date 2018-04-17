package dtu.database;

import java.util.ArrayList;

public class DTO {

	String name;
	int account;
	ArrayList<Integer> properties;

	public DTO(String name, int account) {
		this.name = name;
		this.account = account;
	}
	
	public DTO() {
		this.name = "";
		this.account = 0;
		this.properties = null;
	}
	



	
	public void addProperty(Integer p) {
		properties.add(p);
	}
	public int getAccount() {
		return account;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Integer> getProperties() {
		return properties;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProperties(ArrayList<Integer> properties) {
		this.properties = properties;
	}
	
}
