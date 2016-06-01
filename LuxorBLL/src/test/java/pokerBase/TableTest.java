package pokerBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pokerEnums.Rank;
import pokerEnums.Suit;
import pokerExceptions.DeckException;

public class TableTest {

	@Test
	public void toStringTest() throws DeckException {
		int nPlayers = 4;
		Table tab = new Table(nPlayers);
		tab.addPlayerToTable(new Player("Chris "), 0);
		tab.addPlayerToTable(new Player("Tim   "), 1);
		tab.addPlayerToTable(new Player("Andrew"), 2);
		tab.addPlayerToTable(new Player("Thomas"), 3);
		ArrayList<WildCard> wcal = new ArrayList<WildCard>(4);
		wcal.add(new WildCard(Rank.TWO, Suit.CLUBS));
		wcal.add(new WildCard(Rank.TWO, Suit.DIAMONDS));
		wcal.add(new WildCard(Rank.TWO, Suit.HEARTS));
		wcal.add(new WildCard(Rank.TWO, Suit.SPADES));
		tab.startNewGame(Game.FiveCardStud(), new Deck(2, wcal));
		while (tab.dealCards()) {
			System.out.println(tab);
		}
		tab.winningPosition();
		System.out.println(tab);
		assertTrue(true);
	}

}
