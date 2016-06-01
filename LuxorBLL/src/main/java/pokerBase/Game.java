package pokerBase;

import java.util.ArrayList;
import java.util.HashMap;

import pokerEnums.CardDestination;
import pokerEnums.CardVisibility;

public class Game {
	
	public static HashMap<String, Game> GameMap = new HashMap<String, Game>();

	private static CardDraw PlayerFaceDown = new CardDraw(CardDestination.Players, CardVisibility.PlayerOnly);
	private static CardDraw PlayerFaceUp = new CardDraw(CardDestination.Players, CardVisibility.Everyone);
	private static CardDraw CommunityFaceUp = new CardDraw(CardDestination.Community, CardVisibility.Everyone);	
	
	public static ArrayList<String> GameNames = new ArrayList<String>();
	
	static {
		FiveCardDraw();
		FiveCardStud();
	}
	
	/**
	 * Five Card Draw has one deal of five draws, all face down.
	 * @return a Game with the one deal
	 */
	public static Game FiveCardDraw() {
		String name = "Five Card Draw";
		GameNames.add(name);
		if (GameMap.containsKey(name)) {
			return GameMap.get(name);
		} else {
			Game aGame = new Game(name);
			ArrayList<CardDraw> deal = new ArrayList<CardDraw>();
			for (int i = 0; i < 5; i++) {
				deal.add(PlayerFaceDown);
			}
			aGame.addDeal(deal);
			GameMap.put(aGame.getName(), aGame);
			return aGame;
		}
	}
	
	/**
	 * Five Card Stud has four deals. The first is of two cards, one face down
	 * and one face up.  The other four are always one card, face up.
	 * @return a Game with the four deals 
	 */
	public static Game FiveCardStud() {
		String name = "Five Card Stud";
		GameNames.add(name);
		if (GameMap.containsKey(name)) {
			return GameMap.get(name);
		} else {
			Game aGame = new Game("Five Card Stud");
			ArrayList<CardDraw> deal1 = new ArrayList<CardDraw>();
			deal1.add(PlayerFaceDown);
			deal1.add(PlayerFaceUp);
			aGame.addDeal(deal1);
			ArrayList<CardDraw> deal2 = new ArrayList<CardDraw>();
			deal2.add(PlayerFaceUp);
			aGame.addDeal(deal2);
			aGame.addDeal(deal2);
			aGame.addDeal(deal2);
			GameMap.put(aGame.getName(), aGame);
			return aGame;
		}
	}
	
	private String name;
	private ArrayList<ArrayList<CardDraw>> deals = new ArrayList<ArrayList<CardDraw>>();
	
	public Game(String name) {
		this.name = name;
	}
	
	private void addDeal(ArrayList<CardDraw> deal) {
		this.deals.add(deal);
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<CardDraw> getDeal(int nDeal) {
		return this.deals.get(nDeal);
	}

	public int nDeals() {
		return this.deals.size();
	}
	
	public String toString() {
		String gameStr = this.name + ", " + this.nDeals() + " deal";
		gameStr += (this.nDeals() == 1) ? "\n" : "s\n";
		for (int i = 0; i < this.deals.size(); i++) {
			gameStr += "Deal " + i + "\n";
			for (int j = 0; j < this.getDeal(i).size(); j++) {
				gameStr += "    " + this.deals.get(i).get(j) + "\n";
			}
		}
		return gameStr;
	}
}
