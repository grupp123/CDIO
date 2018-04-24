package dtu.database;

public class DAOcar implements DAOInterfaceCar {
	
	private Connector c = new Connector();

	public DAOcar(Connector c) {
		c = this.c;
	}

	@Override
	public void createCar(int playerID, String color) {
		try {
			String q = "call create_car("+playerID+ ", '" + color +"');";
			c.doUpdate(q);
		}
		catch(Exception ex) {
			ex.getStackTrace();
		}
		
	}
	
	
	

}
