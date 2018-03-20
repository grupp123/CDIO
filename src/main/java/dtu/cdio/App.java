package dtu.cdio;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;
import gui_main.GUI;

public class App {
	public GUI gui = new GUI(generateFields());
		
	public static void main( String[] args )
	{
		new App().start();
	}

	public void start() {
		gui.getUserButtonPressed("hvor mange spillere?", "2", "3", "4", "5", "6");
		gui.getUserInteger("sælg huset", 10, 100);
		Connector c = new Connector();
		c.getConnection();
		print(c, "select * from student;");
		
		
		
	}
	
	
	public void print(Connector c, String query) {
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

	
	
	private GUI_Field[] generateFields() {
		String[] fieldText = new String[24];
		for (int i = 0; i < fieldText.length; i++) {
			fieldText[i] = "1";
		}
		GUI_Field[] fields = new GUI_Field[24];

		for (int i = 0; i < fields.length; i++) {
			GUI_Street gui_street = new GUI_Street();
			gui_street.setTitle(fieldText[i]);
			
			//gui_street.setSubText(fieldSubtext[i]);
			//gui_street.setRent(fieldRent[i]);
			gui_street.setDescription(fieldText[i]);
			fields[i] = gui_street;
		}
		fields[1].setBackGroundColor(Color.red);
		fields[2].setBackGroundColor(Color.red);
		fields[4].setBackGroundColor(Color.cyan);
		fields[5].setBackGroundColor(Color.cyan);
		fields[7].setBackGroundColor(Color.PINK);
		fields[8].setBackGroundColor(Color.PINK);
		fields[10].setBackGroundColor(Color.BLUE);
		fields[11].setBackGroundColor(Color.BLUE);
		fields[13].setBackGroundColor(Color.WHITE);
		fields[14].setBackGroundColor(Color.WHITE);
		fields[16].setBackGroundColor(Color.DARK_GRAY);
		fields[17].setBackGroundColor(Color.DARK_GRAY);
		fields[19].setBackGroundColor(Color.MAGENTA);
		fields[20].setBackGroundColor(Color.MAGENTA);
		fields[22].setBackGroundColor(Color.ORANGE);
		fields[23].setBackGroundColor(Color.ORANGE);
		return fields;
	}
}

	