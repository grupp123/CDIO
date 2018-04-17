package dtu.database;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

public class App {
	public GUI gui = new GUI(generateFields());

	public static void main( String[] args ) throws Exception
	{

new App().start();
	}

public void start() throws Exception {
	
	gui.getUserButtonPressed("vælg", "start nyt spil", "fortsæt gamle spil");
	
	
	Connector c = new Connector();
	DAOdemo d = new DAOdemo(c);
	
	d.useMatadorDemo();
	
	System.out.println(d.activeGames());
	
	
	int i = gui.getUserInteger("følgende spil kan spilles: \n" + d.activeGames());
	String s = gui.getUserString("skriv dit navn");
	d.createPlayerInGame(i, s);
	int o = gui.getUserInteger("følgende spiller uploades: \n" + d.activePlayers());
	
	System.out.println(d.getPlayerName(o));
	GUI_Player p = new GUI_Player(d.getPlayerName(o));
	gui.addPlayer(p);
	
	
	
//	Player p = new Player();
//	p.setName(gui.getUserString("select name"));
//	p.setBalance(gui.getUserInteger("how much money?"));
//	p.setId(1);
	
	
	
	
	
	
	
	
	
	
	
	
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

