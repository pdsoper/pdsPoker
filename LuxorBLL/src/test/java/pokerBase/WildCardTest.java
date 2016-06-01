package pokerBase;

import static org.junit.Assert.*;

import org.junit.Test;

import pokerEnums.Rank;
import pokerEnums.Suit;

public class WildCardTest {

	@Test
	public final void testWildCard() {
		WildCard wc = new WildCard();
		// System.out.println("testWildCard");
		// System.out.println(wc);
		assertEquals(wc.isWild(), true);
	}

	@Test
	public final void testWildCardWildCard() {
		WildCard wc1 = new WildCard(Rank.TWO, Suit.HEARTS);
		wc1.setRank(Rank.ACE);
		wc1.setSuit(Suit.DIAMONDS);
		WildCard wc2 = new WildCard(wc1);
		// System.out.println("testWildCardWildCard");
		// System.out.println(wc1 + " is a copy of " + wc2);
		assertNotEquals(wc1, wc2);
		assertEquals(wc1.getRank(), wc2.getRank());
		assertEquals(wc1.getFaceRank(), wc2.getFaceRank());
		assertEquals(wc1.getSuit(), wc2.getSuit());
		assertEquals(wc1.getFaceSuit(), wc2.getFaceSuit());
	}

	@Test
	public final void testWildCardRankSuit() {
		WildCard wc = new WildCard(Rank.TWO, Suit.HEARTS);
		// System.out.println("testWildCardRankSuit");
		// System.out.println(wc);
		assertEquals(wc.getRank(), Rank.TWO);
		assertEquals(wc.getFaceRank(), Rank.TWO);
		assertEquals(wc.getSuit(), Suit.HEARTS);
		assertEquals(wc.getFaceSuit(), Suit.HEARTS);
	}

	@Test
	public final void testSetRank() {
		WildCard wc = new WildCard(Rank.TWO, Suit.HEARTS);
		wc.setRank(Rank.ACE);
		assertEquals(wc.getRank(), Rank.ACE);
	}

	@Test
	public final void testSetSuit() {
		WildCard wc = new WildCard(Rank.TWO, Suit.HEARTS);
		wc.setSuit(Suit.DIAMONDS);
		assertEquals(wc.getSuit(), Suit.DIAMONDS);
	}

	@Test
	public final void toStringTest() {
		WildCard wc = new WildCard(Rank.TWO, Suit.HEARTS);
		wc.setSuit(Suit.DIAMONDS);
		wc.setRank(Rank.ACE);
		System.out.println("WildCard : " + wc);
		assertTrue(true);
	}

}
