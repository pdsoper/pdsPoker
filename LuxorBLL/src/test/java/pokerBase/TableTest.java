package pokerBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pokerEnums.Rank;
import pokerEnums.Suit;
import pokerExceptions.DeckException;

public class TableTest {

	@Test
	public void toStringTest2() throws DeckException {
		int nPlayers = 4;
		Table tab = new Table(nPlayers);
		tab.addPlayer(new Player("Chris "), 0);
		tab.addPlayer(new Player("Tim   "), 1);
		tab.addPlayer(new Player("Andrew"), 2);
		tab.addPlayer(new Player("Thomas"), 3);
		tab.startNewGame(Game.FiveCardDraw(), new Deck(2, WildCard.OneEyedJacks()));
		while (tab.dealCards()) {
			System.out.println(tab);
		}
		tab.evaluateHands();
		System.out.println(tab);
		assertTrue(true);
	}

	@Test
	public void toStringTest1() throws DeckException {
		int nPlayers = 4;
		Table tab = new Table(nPlayers);
		tab.addPlayer(new Player("Chris "), 0);
		tab.addPlayer(new Player("Tim   "), 1);
		tab.addPlayer(new Player("Andrew"), 3);
		tab.startNewGame(Game.FiveCardStud(), new Deck(2, WildCard.Deuces()));
		while (tab.dealCards()) {
			System.out.println(tab);
		}
		tab.evaluateHands();
		System.out.println(tab);
		assertTrue(true);
	}

	@Test
	public void toStringTest3() throws DeckException {
		int nPlayers = 4;
		Table tab = new Table(nPlayers);
		tab.addPlayer(new Player("Chris "), 0);
		tab.addPlayer(new Player("Tim   "), 1);
		tab.addPlayer(new Player("Andrew"), 3);
		tab.startNewGame(Game.FiveCardStud(), new Deck(2, WildCard.Deuces()));
		while (tab.dealCards()) {
			if (tab.getCurrentDeal() == 4) {
				tab.getPlayer(1).fold();
			}
			System.out.println(tab);
		}
		tab.evaluateHands();
		System.out.println(tab);
		assertTrue(true);
	}

}
