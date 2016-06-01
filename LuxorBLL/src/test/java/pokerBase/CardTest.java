package pokerBase;

import static org.junit.Assert.*;

import org.junit.Test;

import pokerEnums.Rank;
import pokerEnums.Suit;

/**
* @author paulsoper
*
*/

public class CardTest {

	private static Card aceOfSpades = new Card(Rank.ACE, Suit.SPADES);
	private static Card aceOfHearts = new Card(Rank.ACE, Suit.HEARTS);
	private static Card kingOfSpades = new Card(Rank.KING, Suit.SPADES);
	private static Card fourOfDiamonds = new Card(Rank.FOUR, Suit.DIAMONDS);
	private static Card queenOfDiamonds = new Card(Rank.QUEEN, Suit.DIAMONDS);
	private static Card twoOfClubs = new Card(Rank.TWO, Suit.CLUBS);
	private static Card nineOfClubs = new Card(Rank.NINE, Suit.CLUBS);
	private static Card twoOfHearts = new Card(Rank.TWO, Suit.HEARTS);
	private static Card fourOfHearts = new Card(Rank.FOUR, Suit.HEARTS);
	
	/**
	 * Test method for {@link pokerBase.Card#rankEquals(pokerBase.Card)}.
	 */
	@Test
	public final void testEqualsCard() {
		assertTrue(twoOfClubs.rankEquals(twoOfHearts));
		assertTrue(aceOfHearts.rankEquals(aceOfSpades));
		assertFalse(aceOfHearts.rankEquals(twoOfClubs));
		assertFalse(aceOfHearts.rankEquals(nineOfClubs));
	}

	/**
	 * Test method for {@link pokerBase.Card#equalsSuit(pokerBase.Card)}.
	 */
	@Test
	public final void testEqualsSuit() {
		assertTrue(twoOfClubs.suitEquals(nineOfClubs));
		assertTrue(fourOfHearts.suitEquals(aceOfHearts));
		assertFalse(fourOfDiamonds.suitEquals(nineOfClubs));
		assertFalse(queenOfDiamonds.suitEquals(fourOfHearts));
	}

	/**
	 * Test method for {@link pokerBase.Card#compareTo(java.lang.Object)}.
	 */
	@Test
	public final void testCompareTo1() {
		assertEquals(fourOfDiamonds.compareTo(queenOfDiamonds),8);
		assertEquals(twoOfClubs.compareTo(aceOfSpades),12);
		assertEquals(twoOfClubs.compareTo(twoOfHearts),0);
	}

	/**
	 * Test method for {@link pokerBase.Card#getRank}.
	 */
	@Test
	public final void getRank(){
		assertEquals(twoOfClubs.getRank(), Rank.TWO); 
		assertEquals(fourOfDiamonds.getRank(), Rank.FOUR);
	}
	
	/**
	 * Test method for {@link pokerBase.Card#getSuit}.
	 */
	@Test
	public final void getSuit(){
		assertEquals(twoOfClubs.getSuit(), Suit.CLUBS);
		assertEquals(fourOfDiamonds.getSuit(), Suit.DIAMONDS);
	}

}
