package dk.dtu.compute.se.pisd.monopoly.mini;

import java.util.ArrayList;
import java.util.List;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Card;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Chance;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Game;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Space;
import dk.dtu.compute.se.pisd.monopoly.mini.model.Tax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardLegat;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMove;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMoveBack;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPayForAmountPropertiesHotel;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardReceiveMoneyFromBank;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardRecieveMoneyFromPlayers;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.OutOfJail;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.PayTax;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardMovePassStart;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardNearestShip;
import dk.dtu.compute.se.pisd.monopoly.mini.model.cards.CardPay;
import dk.dtu.compute.se.pisd.monopoly.mini.model.properties.Utility;

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
		game.addSpace(go);
		
		Property p = new Property();
		p.setName("Rødovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("betal inkomstskat eller 10% eller 4000kr");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("Rederiet Lindinger A/S");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);

		p = new Property();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Allégade");
		p.setCost(2400);
		p.setRent(150);
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("på besøg");
		game.addSpace(prison);
		
		p = new Property();
		p.setName("Frederiksberg Allé");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Coca-Cola");
		p.setCost(3000);
		p.setRent(300);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Bülowsvej");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Grenaa-Hundested");
		p.setCost(4000);
		p.setRent(500);
		game.addSpace(p);


		p = new Property();
		p.setName("Bernstorffsvej");
		p.setCost(3600);
		p.setRent(300);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		 
		p = new Property();
		p.setName("Hellerupvej");
		p.setCost(3600);
		p.setRent(300);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Strandvej");
		p.setCost(4000);
		p.setRent(400);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Strandvej");
		p.setCost(4000);
		p.setRent(350);
		game.addSpace(p);
		
		Space Parkering = new Space();
		Parkering.setName("Den Danske Bank");
		game.addSpace(Parkering);
		
		p = new Property();
		p.setName("Trianglen");
		p.setCost(4400);
		p.setRent(350);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Østerbrogade");
		p.setCost(4400);
		p.setRent(350);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Grønningen");
		p.setCost(4800);
		p.setRent(400);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Mols-Linien A/S");
		p.setCost(4000);
		p.setRent(350);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Bredegade");
		p.setCost(5200);
		p.setRent(450);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Kgs. Nytorv");
		p.setCost(5200);
		p.setRent(450);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Faxe Bryggeri");
		p.setCost(3000);
		p.setRent(150);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Østergade");
		p.setCost(5600);
		p.setRent(500);
		game.addSpace(p);
		
		Space prison2 = new Space();
		prison2.setName("De Fængsles");
		game.addSpace(prison2);
		
		p = new Property();
		p.setName("Amagertorv");
		p.setCost(6000);
		p.setRent(400);
		game.addSpace(p);
	
		p = new Property();
		p.setName("Vimmelskaftet");
		p.setCost(6000);
		p.setRent(550);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Nygade");
		p.setCost(6400);
		p.setRent(600);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Skandinavisk Linietrafik");
		p.setCost(4000);
		p.setRent(350);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Frederiksberggade");
		p.setCost(7000);
		p.setRent(700);
		game.addSpace(p);
		
		Tax t1 = new Tax();
		t.setName("Ekstraordinær statsskat betal kr. 2.000");
		game.addSpace(t1);
		
		p = new Property();
		p.setName("Rådhuspladsen");
		p.setCost(8000);
		p.setRent(1000);
		game.addSpace(p);
		

		
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
		mols.setGoToIndex(4);
		cards.add(mols);
		
		CardMove rHusPladsen = new CardMove();
		rHusPladsen.setText("Tag ind på Rådhuspladsen");
		rHusPladsen.setTarget(game.getSpaces().get(10)); //det skal være 39 men jeg venter på at de sidste felter bliver lavet
		cards.add(rHusPladsen);
		
		CardMovePassStart gronning = new CardMovePassStart();
		gronning.setText("Ryk frem til Grønningen. Hvis De passerer Start, indkassér da kr. 4.000.");
		gronning.setGoToIndex(13);
		cards.add(gronning);
		
		CardMovePassStart frederiksberg = new CardMovePassStart();
		frederiksberg.setText("Ryk frem til Frederiksberg Allé. Hvis De passerer “Start, indkassér kr. 4.000.");
		frederiksberg.setGoToIndex(37);
		cards.add(frederiksberg);
		
		game.setCardDeck(cards);
		

		return game;
	}

	/**
	 * The main method which creates a game, shuffles the chance
	 * cards, creates players, and then starts the game.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Game game = createGame();
		game.shuffleCardDeck();
		
		GameController controller = new GameController(game);
		
		controller.initializeGUI();
		controller.createPlayers();
		
		
		controller.play();
	}

}
