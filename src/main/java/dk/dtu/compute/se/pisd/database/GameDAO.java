package dk.dtu.compute.se.pisd.database;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Player;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardBail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;

public class GameDAO implements IGameDAO {

	private Connector connector;

	public GameDAO(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void create(Game game) throws SQLException {
		java.sql.Connection con = connector.getConnection();
		int i = 0;

		// her opretter jeg spillet inde i databasen, og sørger for at få gameID
		// tilbage så den kan puttes ind i game
		con.setAutoCommit(false);
		connector.doUpdate("insert into game(gameName) values('" + game.getGameName() + "');");
		ResultSet rs = connector.doQuery("select max(gameID) from game;");
		while (rs.next()) {
			i = rs.getInt("max(gameID)");

		}
		game.setGameID(i);

		// spillerne oprettes i databasen med deres ID, gameID og navn
		for (int j = 0; j < game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int id = p.getId();
			int gid = game.getGameID();
			String name = p.getName();
			connector.doUpdate("insert into player(playerID, gameID, playerName) values(" + id + ", " + gid + ", '"
					+ name + "');");
		}

		// nu oprettes de tilhørende biler til hver spiller
		for (int j = 0; j < game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int playerid = p.getId();
			int color = p.getColor().getRGB();
			int gid = game.getGameID();
			String name = p.getName();
			connector.doUpdate("insert into car(carColor, playerID, gameID) values(" + color + ", " + playerid + ", "
					+ gid + ");");
		}
		// nu oprettes alle de ejelige felter
		// List<Space> spaces = game.getSpaces();
		for (int f = 0; f < game.getSpaces().size(); f++) {
			Space k = game.getSpaces().get(f);
			if (k instanceof Property) {

				int spaceNumber = k.getIndex();

				int gid = game.getGameID();
				// Følgende sql sætning virker i workbench
				// insert into properties(spaceNumber,gameID) values (0,1);
				connector.doUpdate(
						"insert into properties(spaceNumber,gameID) values(" + spaceNumber + ", " + gid + ");");
			}
		}

		con.commit();
		con.setAutoCommit(true);
	}

	@Override
	public void update(Game game) throws SQLException {
		java.sql.Connection con = connector.getConnection();

		// her opdateres spillet inde i databasen altså sørger for at ændre
		// currentPlayer
		con.setAutoCommit(false);
		int cp = game.getCurrentPlayer().getId();
		int gidd = game.getGameID();
		connector.doUpdate("update game set currentPlayer = " + cp + " where gameID = " + gidd + ";");

		// her opdateres spillerne inde i databasen, altså om de har tabt, balance, jail
		// jailcard og jailtime
		for (int j = 0; j < game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int id = p.getId();
			int gid = game.getGameID();
			boolean hasLost = p.isBroke();
			int balance = p.getBalance();
			boolean inJail = p.isInPrison();
			// List<Card> jailc = p.getOwnedCards();
			int jailCard = p.getOwnedCards().size();
			int jailTime = p.getPrisonTime();
			connector.doUpdate("call update_player(" + hasLost + "," + balance + "," + inJail + "," + jailCard + ","
					+ jailTime + "," + gid + "," + id + ");");
		}
		// nu opdateres car-tabellen og det eneste der skal
		// opdateres løbende er position
		for (int j = 0; j < game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			int playerid = p.getId();
			int gameid = game.getGameID();
			int position = p.getCurrentPosition().getIndex();
			connector.doUpdate("update car set position = " + position + " where gameID = " + gameid
					+ " and playerID = " + playerid + ";");
		}

		// nu opdateres de ejerne på alle felterne samt antal huse og om det er pantsat

		// List<Space> spaces = game.getSpaces();
		// Loop through all spaces
		for (int o = 0; o < game.getSpaces().size(); o++) {
			Space current = game.getSpaces().get(o);
			// Properties - Updating owner and if the space is mortgaged
			if (current instanceof Property) {
				Property prop = (Property) game.getSpaces().get(o);
				if (prop.getOwner() != null) {
					int pid = prop.getOwner().getId();
					int spaceNumber = prop.getIndex();
					int gid = game.getGameID();
					boolean mortgaged = prop.isMortgaged();
					int houses = 0;

					// Følgende sql sætning virker i workbench
					// update properties set ownerP=0, spaceNumber=0, Mortagaged=true where
					// gameid=1;
					connector.doUpdate(
							"call update_properties(" + pid + "," + mortgaged + "," + gid + "," + spaceNumber + ");");
				}
			}
			if (current instanceof RealEstate) {
				RealEstate real = (RealEstate) game.getSpaces().get(o);
				int numberhouses = real.getHouses();
				int gid = game.getGameID();
				int spaceNumber = real.getIndex();
				connector.doUpdate("update properties set houses = " + numberhouses + " where gameID = " + gid
						+ " And spaceNumber = " + spaceNumber + ";");
			}
		}
		con.commit();
		con.setAutoCommit(true);
	}

	@Override
	public void load(Game game, int gameID) throws SQLException {
		java.sql.Connection con = connector.getConnection();
		int i = 0;
		int numberOfPlayers = 0;

		// AutoCommit is set to false until all the data in this method is ready to
		// commit.
		con.setAutoCommit(false);

		// Start med at hente antallet af spillere i spillet med dette gameID, hermed
		// kender vi deres ID
		// test værdi:
		ResultSet rs = connector.doQuery("Select count(playerID) from player where gameID = " + gameID + ";");
		while (rs.next()) {
			numberOfPlayers = rs.getInt(1);
		}

		// Going through the player ID's
		Player[] players = new Player[numberOfPlayers];
		for (int u = 0; u < numberOfPlayers; u++) {
			players[u] = new Player();
			int playerID = u;
			// getting and setting playerName
			rs = connector.doQuery(
					"select playerName from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				String name = rs.getString("playerName");
				players[u].setName(name);
			}

			// getting and setting balance
			rs = connector.doQuery(
					"select balance from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				int balance = rs.getInt("balance");
				players[u].setBalance(balance);
			}

			// getting and setting has lost;
			rs = connector.doQuery(
					"select hasLost from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				boolean hasLost = rs.getBoolean("hasLost");
				players[u].setBroke(hasLost);
			}

			// getting and setting in jail;
			rs = connector.doQuery(
					"select inJail from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				boolean inJail = rs.getBoolean("inJail");
				players[u].setInPrison(inJail);
			}

			// getting and setting jailTime
			rs = connector.doQuery(
					"select jailTime from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				int jailTime = rs.getInt(1);
				players[u].setPrisonTime(jailTime);
			}

			// Getting and setting jailCard
			rs = connector.doQuery(
					"select jailCard from player where playerID = " + playerID + " and gameID = " + gameID + ";");
			int jailCard = 0;
			while (rs.next()) {
				jailCard = rs.getInt(1);
			}
			for (int j = 0; j < jailCard; j++) {
				players[u].addOwnedCard(new CardBail());
				// Very bad, we have no certainty that the actual jailcard was removed or
				// another card.
				// TODO: could be reimplemented.
				game.removeJailCard();
			}

			// getting and setting car color
			rs = connector
					.doQuery("select carColor from car where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				int rgb = rs.getInt("carColor");
				Color carColor = new Color(rgb);
				players[u].setColor(carColor);
				;
			}

			// getting and setting current position
			rs = connector
					.doQuery("select position from car where playerID = " + playerID + " and gameID = " + gameID + ";");
			while (rs.next()) {
				int position = rs.getInt("position");
				// List<Space> spaces = game.getSpaces();
				Space cuPos = game.getSpaces().get(position);
				players[u].setCurrentPosition(cuPos);
			}

			players[u].setId(u);
			game.addPlayer(players[u]);
		}

		// getting currrent player from db
		rs = connector.doQuery("select currentPlayer from game;");
		// setting current player
		while (rs.next()) {
			i = rs.getInt("currentPlayer");
		}
		int currentPlayerID = i;
		// Looping through players, checking if id is equal to currentPlayerID from db
		for (int j = 0; j < game.getPlayers().size(); j++) {
			Player p = game.getPlayers().get(j);
			if (p.getId() == currentPlayerID) {
				game.setCurrentPlayer(p);
			}
		}

		// Going through the properties
		// List<Space> spaces = new ArrayList<Space>();
		Integer owner = 0;
		// spaces = game.getSpaces();
		for (int u = 0; u <= 39; u++) {
			Space space = game.getSpaces().get(u);
			if (space instanceof Property) {
				// tjek for ejer
				rs = connector.doQuery("Select ownerP from properties where spaceNumber = " + space.getIndex()
						+ " and gameID = " + gameID + ";");
				while (rs.next()) {
					owner = (Integer) rs.getObject(1);
				}
				try {

				if (owner == 0) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}

				if (owner == 1) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}

				if (owner == 2) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}

				if (owner == 3) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}

				if (owner == 4) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}

				if (owner == 5) {
					Player powner = game.getPlayers().get(owner);
					Property owned = (Property) space;
					((Property) space).setOwner(powner);
					powner.addOwnedProperty(owned);
				}
				}catch (NullPointerException e) {
					e.getStackTrace();
					
				}

				// hent antal huse
				int houses = 0;
				if (space instanceof RealEstate) {
					rs = connector.doQuery("Select houses from properties where spaceNumber = " + space.getIndex()
							+ " and gameID = " + gameID + ";");
					while (rs.next()) {
						houses = rs.getInt(1);
					}
					((RealEstate) space).setHouses(houses);

				}

				// hent mortagaged
				boolean mort = false;
				rs = connector.doQuery("Select mortagaged from properties where spaceNumber = " + space.getIndex()
						+ " and gameID = " + gameID + ";");
				while (rs.next()) {
					mort = rs.getBoolean(1);
				}
				((Property) space).setMortgaged(mort);

			}
		}
		con.commit();
		con.setAutoCommit(true);

	}

	@Override
	public ArrayList<String> activeGames() throws SQLException {

		String q = "";
		ArrayList<String> l = new ArrayList<>();

		ResultSet rs = connector.doQuery("SELECT GameName FROM game");
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int j = 1; j <= columnsNumber; j++) {
				if (j > 1) {
					q += " - ";
				}
				l.add(rs.getString(j));
				String columnValue = rs.getString(j);
				// System.out.print(columnValue + " " + rsmd.getColumnName(j));
				q += columnValue;// + " " + rsmd.getColumnName(j);
			}

			q += ", ";
		}
		// q += "\n";
		return l;

	}

	@Override
	public int getGameIdFromName(String str) throws SQLException {
		ResultSet rs = connector.doQuery("SELECT gameid FROM game WHERE gamename = '" + str + "';");
		if (!rs.first())
			throw new SQLException("id " + str + " findes ikke");
		return (rs.getInt(1));

	}

}
