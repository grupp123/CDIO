package dk.dtu.compute.se.pisd.monopoly.mini;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Chance;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.IncomeTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.StateTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardLegat;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMoveBack;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMovePassStart;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardNearestShip;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPay;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPayForAmountPropertiesHotel;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardRecieveMoneyFromPlayers;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.OutOfJail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.differentSpaces.Jail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.RealEstate;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Shipping;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;
import dtu.database.GameDAO;

/**
 * Main class for setting up and running a (Mini-)Monoploy game.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class MiniMonopoly {
	
	/**
	 * Creates the initial static situation of a Monopoly game. Note
	 * that the players are not created here, and the chance cards
	 * are not shuffled here.
	 * 
	 * @return the initial game board and (not shuffled) deck of chance cards 
	 */
	public static Game createGame() {

		// Create the initial Game set up (note that, in this simple
		// setup, we use only 11 spaces). Note also that this setup
		// could actually be loaded from a file or database instead
		// of creating it programmatically.
		Game game = new Game();
		
		Space go = new Space();
		go.setName("Start");
		go.setColor(Color.red);
		game.addSpace(go);
		
		RealEstate r = new RealEstate();
		r.setName("Rødovrevej");
		r.setCost(1200);
		r.setRent(50);
		r.setRentLevels(new int[] {50,250,750,2250,4000,6000});
		r.setHousePrice(1000);
		r.setColor(Color.darkGray);
		game.addSpace(r);
		
		Chance chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		
		r = new RealEstate();
		r.setName("Hvidovrevej");
		r.setCost(1200);
		r.setRent(50);
		r.setRentLevels(new int[] {50,250,750,2250,4000,6000});
		r.setHousePrice(1000);
		r.setColor(Color.darkGray);
		game.addSpace(r);
		
		IncomeTax t = new IncomeTax();
		t.setName("betal inkomstskat 10% eller 4000kr");
		t.setText("10% eller 4000");
		t.setTax(4000);
		game.addSpace(t);
		

		Shipping s = new Shipping();
		s.setName("Rederiet Lindinger A/S");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);

		r = new RealEstate();
		r.setName("Roskildevej");
		r.setCost(2000);
		r.setRent(100);
		r.setRentLevels(new int[] {100,600,1800,5400,8000,11000});
		r.setHousePrice(1000);
		r.setColor(Color.cyan);
		game.addSpace(r);
		
		chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		
		r = new RealEstate();
		r.setName("Valby Langgade");
		r.setCost(2000);
		r.setRent(100);
		r.setRentLevels(new int[] {100,600,1800,5400,8000,11000});
		r.setHousePrice(1000);
		r.setColor(Color.cyan);
		game.addSpace(r);
		
		r = new RealEstate();
		r.setName("Allégade");
		r.setCost(2400);
		r.setRent(150);
		r.setRentLevels(new int[] {150,800,2000,6000,9000,12000});
		r.setHousePrice(1000);
		r.setColor(Color.cyan);
		game.addSpace(r);
		
		Jail prison = new Jail();
		prison.setName("på besøg");
		game.addSpace(prison);
		
		r = new RealEstate();
		r.setName("Frederiksberg Allé");
		r.setCost(2800);
		r.setRent(200);
		r.setRentLevels(new int[] {200,1000,3000,9000,12500,15000});
		r.setHousePrice(2000);
		r.setColor(Color.pink);
		game.addSpace(r);
		
		Utility u = new Utility();
		u.setName("Tuborg");
		u.setCost(3000);
		game.addSpace(u);
		
		r = new RealEstate();
		r.setName("Bülowsvej");
		r.setCost(2800);
		r.setRent(200);
		r.setRentLevels(new int[] {200,1000,3000,9000,12500,15000});
		r.setHousePrice(2000);
		r.setColor(Color.pink);
		game.addSpace(r);
		
		r = new RealEstate();
		r.setName("Gl. Kongevej");
		r.setCost(3200);
		r.setRent(250);
		r.setRentLevels(new int[] {250,1250,3750,10000,14000,18000});
		r.setHousePrice(2000);
		r.setColor(Color.pink);
		game.addSpace(r);
		
		s = new Shipping();
		s.setName("Grenaa-Hundested");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);

		r = new RealEstate();
		r.setName("Bernstorffsvej");
		r.setCost(3600);
		r.setRent(300);
		r.setRentLevels(new int[] {300,1400,4000,11000,15000,19000});
		r.setHousePrice(2000);
		r.setColor(Color.ORANGE);
		game.addSpace(r);
		
		chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		 
		r = new RealEstate();
		r.setName("Hellerupvej");
		r.setCost(3600);
		r.setRent(300);
		r.setRentLevels(new int[] {300,1400,4000,11000,15000,19000});
		r.setHousePrice(2000);
		r.setColor(Color.ORANGE);
		game.addSpace(r);
		
		r = new RealEstate();
		r.setName("Strandvej");
		r.setCost(4000);
		r.setRent(400);
		r.setRentLevels(new int[] {350,1600,4400,12000,16000,20000});
		r.setHousePrice(2000);
		r.setColor(Color.ORANGE);
		game.addSpace(r);
		
		Space Parkering = new Space();
		Parkering.setName("Helle");
		game.addSpace(Parkering);
		
		r = new RealEstate();
		r.setName("Trianglen");
		r.setCost(4400);
		r.setRent(350);
		r.setRentLevels(new int[] {350,1800,5000,14000,17500,21000});
		r.setHousePrice(3000);
		r.setColor(Color.magenta);
		game.addSpace(r);	
		
		chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		
		r = new RealEstate();
		r.setName("Østerbrogade");
		r.setCost(4400);
		r.setRent(350);
		r.setRentLevels(new int[] {350,1800,5000,14000,17500,21000});
		r.setHousePrice(3000);
		r.setColor(Color.magenta);
		game.addSpace(r);
		
		r = new RealEstate();
		r.setName("Grønningen");
		r.setCost(4800);
		r.setRent(400);
		r.setRentLevels(new int[] {400,2000,6000,15000,18500,22000});
		r.setHousePrice(3000);
		r.setColor(Color.magenta);
		game.addSpace(r);
		
		s = new Shipping();
		s.setName("Mols-Linien A/S");
		s.setCost(4000);
		s.setRent(350);
		game.addSpace(s);
		
		r = new RealEstate();
		r.setName("Bredgade");
		r.setCost(5200);
		r.setRent(450);
		r.setRentLevels(new int[] {450,2200,6600,16000,19500,23000});
		r.setHousePrice(3000);
		r.setColor(new Color(255,250,205));
		game.addSpace(r);
		
		r = new RealEstate();
		r.setName("Kgs. Nytorv");
		r.setCost(5200);
		r.setRent(450);
		r.setRentLevels(new int[] {450,2200,6600,16000,19500,23000});
		r.setHousePrice(3000);
		r.setColor(new Color(255,250,205));
		game.addSpace(r);
		
		u = new Utility();
		u.setName("Carlsberg");
		u.setCost(3000);
		game.addSpace(u);
		
		r = new RealEstate();
		r.setName("Østergade");
		r.setCost(5600);
		r.setRent(500);
		r.setRentLevels(new int[] {500,2400,7200,17000,20500,24000});
		r.setHousePrice(3000);
		r.setColor(new Color(255,250,205));
		game.addSpace(r);
		
		Jail prison2 = new Jail();
		prison2.setName("De Fængsles");
		game.addSpace(prison2);
		
		r = new RealEstate();
		r.setName("Amagertorv");
		r.setCost(6000);
		r.setRent(400);
		r.setRentLevels(new int[] {550,2600,7800,18000,22000,25000});
		r.setHousePrice(4000);
		r.setColor(new Color(154,205,50));
		game.addSpace(r);
	
		r = new RealEstate();
		r.setName("Vimmelskaftet");
		r.setCost(6000);
		r.setRent(550);
		r.setRentLevels(new int[] {550,2600,7800,18000,22000,25000});
		r.setHousePrice(4000);
		r.setColor(new Color(154,205,50));
		game.addSpace(r);
		
		chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		
		r = new RealEstate();
		r.setName("Nygade");
		r.setCost(6400);
		r.setRent(600);
		r.setRentLevels(new int[] {600,3000,9000,20000,24000,28000});
		r.setHousePrice(4000);
		r.setColor(new Color(154,205,50));
		game.addSpace(r);
		
		s = new Shipping();
		s.setName("Skandinavisk Linietrafik");
		s.setCost(4000);
		s.setRent(350);
		game.addSpace(s);
		
		chance = new Chance();
		chance.setName("Chance");
		chance.setColor(Color.white);
		game.addSpace(chance);
		
		r = new RealEstate();
		r.setName("Frederiksberggade");
		r.setCost(7000);
		r.setRent(700);
		r.setRentLevels(new int[] {700,3500,10000,22000,26000,30000});
		r.setHousePrice(4000);
		r.setColor(new Color(160, 32, 240));
		game.addSpace(r);
		
		StateTax st = new StateTax();
		st.setName("Ekstraordinær statsskat betal kr. 2.000");
		st.setText("2000");
		st.setTax(2000);
		game.addSpace(st);
		
		
		r = new RealEstate();
		r.setName("Rådhuspladsen");
		r.setCost(8000);
		r.setRent(1000);
		r.setRentLevels(new int[] {1000,4000,12000,28000,34000,40000});
		r.setHousePrice(4000);
		r.setColor(new Color(160, 32, 240));
		game.addSpace(r);

		
		List<Card> cards = new ArrayList<Card>();
		
		PayTax tax = new PayTax();
		tax.setText("Pay 10% income tax!");
		cards.add(tax);
		
		CardRecieveMoneyFromPlayers birthday = new CardRecieveMoneyFromPlayers();
		birthday.setText("Det er deres fødselsdag. Modtag af hver medspiller kr. 200.");
		birthday.setAmount(200);
		cards.add(birthday);
		
		CardReceiveMoneyFromBank avl = new CardReceiveMoneyFromBank();
		avl.setText("Værdien af egen avl fra nyttehaven udgør kr.200, som De modtager af banken");
		avl.setAmount(200);
		cards.add(avl);
		
		CardReceiveMoneyFromBank lottery = new CardReceiveMoneyFromBank();
		lottery.setText("De har vundet i klasselotteriet. Modtag kr. 500.");
		lottery.setAmount(500);
		cards.add(lottery);
		
		CardReceiveMoneyFromBank elleverigtige = new CardReceiveMoneyFromBank();
		elleverigtige.setText("De havde en rekke med elleve rigtige i tipning. Modtag kr. 1.000.");
		elleverigtige.setAmount(1000);
		cards.add(elleverigtige);
		
		CardReceiveMoneyFromBank gage = new CardReceiveMoneyFromBank();
		gage.setText("Grundet dyrtiden har De fået gageforhøjelse. Modtag kr. 1.000.");
		gage.setAmount(1000);
		cards.add(gage);
		
		for(int i = 0 ; i<1 ; i++) {
		CardReceiveMoneyFromBank aktier = new CardReceiveMoneyFromBank();
		aktier.setText("Modtag udbytte af Deres aktier kr. 1.000.");
		aktier.setAmount(1000);
		cards.add(aktier);
		}
		
		CardReceiveMoneyFromBank obligationer = new CardReceiveMoneyFromBank();
		obligationer.setText("Deres præmieobligation er kommet ud. De modtager kr. 1.000 af banken");
		obligationer.setAmount(1000);
		cards.add(obligationer);
		
		CardReceiveMoneyFromBank skat = new CardReceiveMoneyFromBank();
		skat.setText("Komunen har eftergivet et kvartals skat. Hæv i banken kr. 3.000.");
		skat.setAmount(3000);
		cards.add(skat);
		
		
		
		CardPay parkerings = new CardPay();
		parkerings.setText("De har måtte vedtage en parkeringsbøde. Betal kr. 200 i bøde.");
		parkerings.setAmount(200);
		cards.add(parkerings);
		
		CardPay told = new CardPay();
		told.setText("De har været en tur i udlandet og haft for mange cigaretter med hjem. Betal told kr. 200.");
		told.setAmount(200);
		cards.add(told);
		
		CardPay bode = new CardPay();
		bode.setText("De har kørt frem for “Fuld Stop”. Betal kr. 1.000 i bøde.");
		bode.setAmount(1000);
		cards.add(bode);
		
		CardPay forsikring = new CardPay();
		forsikring.setText("Betal Dere bilforsikring kr. 1.000.");
		forsikring.setAmount(1000);
		cards.add(forsikring);
		
		CardPay tand = new CardPay();
		tand.setText("De har modtaget Deres tandlægeregning. Betal kr. 2.000.");
		tand.setAmount(2000);
		cards.add(tand);
		
		CardPay reparation = new CardPay();
		reparation.setText("Betal kr. 3.000 for reperation af Deres vogn.");
		reparation.setAmount(3000);
		cards.add(reparation);
		
		CardLegat trengende = new CardLegat();
		trengende.setText("De modtager “Matador-legatet for værdi trængende”, stort kr. 40.000. Ved værdig trængende forstås, at Deres formue, d.v.s. Deres kontante penge + skøder + bygninger ikke overstiger kr. 15.000.");
		trengende.setAmount(40000);
		cards.add(trengende);
		
		CardPayForAmountPropertiesHotel propTax = new CardPayForAmountPropertiesHotel();
		propTax.setText("Ejendomsskatterne er steget, ekstraudgifterne er: kr. 800 pr. hus, kr. 2.300 per hotel");
		propTax.setAmountPerHotel(2300);
		propTax.setAmountPerHouse(800);
		cards.add(propTax);
		
		CardPayForAmountPropertiesHotel propOil = new CardPayForAmountPropertiesHotel();
		propOil.setText("Oliepriserne er steget, og De skal betale: kr. 500 pr. hus, kr. 2.000 per. hotel");
		propOil.setAmountPerHotel(2000);
		propOil.setAmountPerHouse(500);
		cards.add(propOil);
		
		
		for (int i = 0; i < 2; i++) {
		CardMove prisonCard = new CardMove();
		prisonCard.setText("Gå i flngsel. Ryk direkte til fængslet. Selv om De passerer “Start”, indkasserer De ikke kr. 4.000.");
		prisonCard.setTarget(prison);
		cards.add(prisonCard);
		}
		
		for (int i = 0; i < 2; i++) {
		OutOfJail OutOfJail = new OutOfJail();
		cards.add(OutOfJail);
		}
		
		
		
		for (int i = 0; i < 2; i++) {
		CardNearestShip nearestShip = new CardNearestShip();
		nearestShip.setText("Ryk brikken frem til det nærmeste rederi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken");
		cards.add(nearestShip);
		}
		
		CardMoveBack threeFieldsBack = new CardMoveBack();
		threeFieldsBack.setText("Ryk tre felter tilbage.");
		cards.add(threeFieldsBack);
		
		CardMove toStart = new CardMove();
		toStart.setText("Ryk frem til 'Start'");
		toStart.setTarget(go);
		cards.add(toStart);
		
		CardMovePassStart mols = new CardMovePassStart();
		mols.setText("Tag med Mols-Linien - Flyt brikken frem, og hvis De passerer “Start”, inkassér da kr. 4.000.");
		mols.setGoToIndex(25);
		cards.add(mols);
		
		CardMove rHusPladsen = new CardMove();
		rHusPladsen.setText("Tag ind på Rådhuspladsen");
		rHusPladsen.setTarget(game.getSpaces().get(39));
		cards.add(rHusPladsen);
		
		CardMovePassStart gronning = new CardMovePassStart();
		gronning.setText("Ryk frem til Grønningen. Hvis De passerer Start, indkassér da kr. 4.000.");
		gronning.setGoToIndex(24);
		cards.add(gronning);
		
		CardMovePassStart frederiksberg = new CardMovePassStart();
		frederiksberg.setText("Ryk frem til Frederiksberg Allé. Hvis De passerer “Start, indkassér kr. 4.000.");
		frederiksberg.setGoToIndex(11);
		cards.add(frederiksberg);
		
		game.setCardDeck(cards);
		

		return game;
	}

	/**
	 * The main method which creates a game, shuffles the chance
	 * cards, creates players, and then starts the game.
	 * 
	 * @param args not used
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		Game game = createGame();
		game.shuffleCardDeck();
		
		
		GameController controller = new GameController(game);
		
		
		controller.initializeGUI();
		controller.chooseNewOrLoadGame();
		
		
		controller.play();
	}

}
