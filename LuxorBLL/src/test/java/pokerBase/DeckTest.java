/**
 * 
 */
package pokerBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import pokerEnums.HandValue;
import pokerEnums.Rank;
import pokerEnums.Suit;
import pokerExceptions.DeckException;

/**
 * @author paulsoper
 *
 */
public class DeckTest {
	
	@Test
	public final void testConstructor() {
		Deck dk = new Deck();
		assertEquals(dk.nCards(), 52);
	}
	
	@Test
	public final void testConstructorJokers() {
		int nJokers = 2;
		Deck dk = new Deck(nJokers);
		assertEquals(dk.nCards(), 54);
	}
	
	@Test
	public final void testConstructorWilds() {
		ArrayList<WildCard> wcal = new ArrayList<WildCard>();
		wcal.add(new WildCard(Rank.TWO, Suit.HEARTS));
		wcal.add(new WildCard(Rank.TWO, Suit.CLUBS));
		wcal.add(new WildCard(Rank.TWO, Suit.SPADES));
		wcal.add(new WildCard(Rank.TWO, Suit.DIAMONDS));
		Deck dk = new Deck(wcal);
		assertEquals(dk.nCards(), 52);
	}
		
	@Test
	public final void testConstructorJokerWilds() {
		int nJokers = 2;
		ArrayList<WildCard> wcal = new ArrayList<WildCard>();
		wcal.add(new WildCard(Rank.TWO, Suit.HEARTS));
		wcal.add(new WildCard(Rank.TWO, Suit.CLUBS));
		wcal.add(new WildCard(Rank.TWO, Suit.SPADES));
		wcal.add(new WildCard(Rank.TWO, Suit.DIAMONDS));
		Deck dk = new Deck(nJokers, wcal);
		assertEquals(dk.nCards(), 54);
	}
		
	/**
	 * Test method for {@link pokerBase.Deck#dealOne()}.
	 */
	@Test
	public final void testDraw() throws DeckException {
		Deck dk = new Deck();
		dk.draw();
		assertEquals(dk.nCards(), 51);
	}
	
	@Test
	public final void testDrawThree() throws DeckException {
		Deck dk = new Deck();
		dk.draw(3);
		assertEquals(dk.nCards(), 49);
	}
	

	@Test (expected = DeckException.class)
	public final void testDrawException() throws DeckException {
		Deck dk = new Deck();
		dk.draw(dk.nCards());
		dk.draw();
		assertEquals(dk.nCards(), 51);
	}
	
	/**
	 * Test method for {@link pokerBase.Deck#nCards()}.
	 */
	@Test
	public final void testNCards() {
		Deck dk = new Deck();
		assertEquals(dk.nCards(), 52);
		assertFalse(dk.nCards() == 51);
	}

	@Test
	public final void toStringTest() {
		int nJokers = 2;
		ArrayList<WildCard> wcal = new ArrayList<WildCard>();
		wcal.add(new WildCard(Rank.TWO, Suit.HEARTS));
		wcal.add(new WildCard(Rank.TWO, Suit.CLUBS));
		wcal.add(new WildCard(Rank.TWO, Suit.SPADES));
		wcal.add(new WildCard(Rank.TWO, Suit.DIAMONDS));
		Deck dk = new Deck(nJokers, wcal);
		System.out.println("Deck with " + nJokers + " jokers and deuces wild" );
		System.out.println(dk);
		assertTrue(true);
	
	}
}
