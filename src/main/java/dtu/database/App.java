package dtu.database;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.parser.Parser;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;

public class App {
	public GUI gui = new GUI(generateFields());

	public static void main( String[] args ) throws Exception
	{
		//new App().start();
		Connector c = new Connector();
		DAOdemo d = new DAOdemo(c);
		d.useMatadorDemo();

		System.out.println(d.activeGames());		

		
		System.out.println(d.getInfoArrayPlayer(2));
		System.out.println(d.allInfoActivePlayersInGame(1));

		for (int i = 1; i < 6; i++) {
			System.out.println(d.getInfoArrayPlayer(i).toString());
			String[] sa = d.getInfoArrayPlayer(i);

			if (sa.length!=0 || sa!=null)
				for(int j=0; j<sa.length;j++){
					if(sa!=null)
						System.out.println(sa[i]);
				}
		}



	}

	public void start() throws Exception {

		gui.getUserButtonPressed("vælg", "start nyt spil", "fortsæt gamle spil");


		Connector c = new Connector();
		DAOdemo d = new DAOdemo(c);

		d.useMatadorDemo();

		System.out.println(d.activeGames());



		String s = gui.getUserButtonPressed("d", d.activePlayersInGame(1));
		List<String> playerlist = Arrays.asList(s.split(", "));

		for (int j = 0; j < playerlist.size(); j++) {
			System.out.println(playerlist.get(j));

		}

		for (String string : playerlist) {
			System.out.println(string);
		}




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

