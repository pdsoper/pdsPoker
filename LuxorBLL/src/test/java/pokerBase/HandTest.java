package pokerBase;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import pokerExceptions.DeckException;
import pokerEnums.HandValue;
import pokerEnums.Rank;
import pokerEnums.Suit;

/**
 * @author paulsoper
 *
 */

public class HandTest {

	private static Card aceOfSpades = new Card(Rank.ACE, Suit.SPADES);
	private static Card aceOfHearts = new Card(Rank.ACE, Suit.HEARTS);
	private static Card aceOfDiamonds = new Card(Rank.ACE, Suit.DIAMONDS);
	private static Card aceOfClubs = new Card(Rank.ACE, Suit.CLUBS);
	private static Card kingOfHearts = new Card(Rank.KING, Suit.HEARTS);
	private static Card kingOfSpades = new Card(Rank.KING, Suit.SPADES);
	private static Card queenOfSpades = new Card(Rank.QUEEN, Suit.SPADES);
	private static Card queenOfHearts = new Card(Rank.QUEEN, Suit.HEARTS);
	private static Card queenOfDiamonds = new Card(Rank.QUEEN, Suit.DIAMONDS);
	private static Card jackOfSpades = new Card(Rank.JACK, Suit.SPADES);
	private static Card jackOfHearts = new Card(Rank.JACK, Suit.HEARTS);
	private static Card tenOfSpades = new Card(Rank.TEN, Suit.SPADES);
	private static Card tenOfHearts = new Card(Rank.TEN, Suit.HEARTS);
	private static Card nineOfSpades = new Card(Rank.NINE, Suit.SPADES);
	private static Card nineOfClubs = new Card(Rank.NINE, Suit.CLUBS);
	private static Card nineOfDiamonds = new Card(Rank.NINE, Suit.DIAMONDS);
	private static Card nineOfHearts = new Card(Rank.NINE, Suit.HEARTS);
	private static Card eightOfSpades = new Card(Rank.EIGHT, Suit.SPADES);
	private static Card eightOfClubs = new Card(Rank.EIGHT, Suit.CLUBS);
	private static Card eightOfDiamonds = new Card(Rank.EIGHT, Suit.DIAMONDS);
	private static Card eightOfHearts = new Card(Rank.EIGHT, Suit.HEARTS);
	private static Card sevenOfDiamonds = new Card(Rank.SEVEN, Suit.DIAMONDS);
	private static Card sevenOfClubs = new Card(Rank.SEVEN, Suit.CLUBS);
	private static Card fiveOfClubs = new Card(Rank.FIVE, Suit.CLUBS);
	private static Card fourOfSpades = new Card(Rank.FOUR, Suit.SPADES);
	private static Card threeOfHearts = new Card(Rank.THREE, Suit.HEARTS);
	private static Card twoOfDiamonds = new Card(Rank.TWO, Suit.DIAMONDS);
	private static Card twoOfClubs = new Card(Rank.TWO, Suit.CLUBS);
	private static WildCard wildTwoOfDiamonds = new WildCard(Rank.TWO, Suit.DIAMONDS);
	private static WildCard wildTwoOfClubs = new WildCard(Rank.TWO, Suit.CLUBS);
	private static WildCard wildTwoOfHearts = new WildCard(Rank.TWO, Suit.HEARTS);
	private static WildCard wildTwoOfSpades = new WildCard(Rank.TWO, Suit.SPADES);
	private static Joker joker1 = new Joker();
	private static Joker joker2 = new Joker();
		
	// Hands with wild cards
	
	private static Hand fiveWild = new Hand(
			joker1, wildTwoOfClubs, wildTwoOfDiamonds, wildTwoOfHearts, wildTwoOfSpades);
	
	private static Hand fourWild1 = new Hand(
			twoOfDiamonds, wildTwoOfClubs, wildTwoOfDiamonds, wildTwoOfHearts, wildTwoOfSpades);
	
	private static Hand fourWild2 = new Hand(
			joker1, twoOfClubs, wildTwoOfDiamonds, wildTwoOfHearts, wildTwoOfSpades);
	
	private static Hand oneWildOnePair = new Hand(
			aceOfSpades, kingOfSpades, queenOfSpades, nineOfClubs, joker1);
	
	private static Hand oneWildBottomStraight = new Hand(
			twoOfClubs, threeOfHearts, fourOfSpades, fiveOfClubs, wildTwoOfClubs);
	
	private static Hand oneWildThreeOfaKind = new Hand(
			aceOfSpades, kingOfSpades, nineOfClubs, nineOfClubs, wildTwoOfHearts);
	
	private static Hand twoWildThreeOfaKind = new Hand(
			twoOfDiamonds, aceOfSpades, fourOfSpades, joker1, joker2); 
	
	private static Hand threeWildFiveOfaKind = new Hand(
			sevenOfClubs, sevenOfDiamonds, joker1, wildTwoOfClubs, wildTwoOfDiamonds);
	
	private static Hand threeWildRoyalFlush = new Hand(
			aceOfHearts, kingOfHearts, joker1, wildTwoOfClubs, wildTwoOfDiamonds);
	
	private static Hand threeWildStraightFlush = new Hand(
			nineOfHearts, eightOfHearts, joker1, wildTwoOfClubs, wildTwoOfDiamonds);
	
	private static Hand threeWildFourOfaKind = new Hand(
			aceOfClubs, sevenOfDiamonds, joker1, wildTwoOfClubs, wildTwoOfDiamonds);
	

	@Test
	public final void testOneWildOnePair() {
		oneWildOnePair.evaluate();
		assertTrue(oneWildOnePair.isOnePair());
	}
	
	@Test
	public final void testOneWildBottomStraight() {
		oneWildBottomStraight.evaluate();
		assertTrue(oneWildBottomStraight.isStraight());
	}

	@Test
	public final void testOneWildThreeOfaKind() {
		oneWildThreeOfaKind.evaluate();
		assertTrue(oneWildThreeOfaKind.isThreeOfaKind());
	}
	
	@Test
	public final void testTwoWildThreeOfaKind() {
		twoWildThreeOfaKind.evaluate();
		assertTrue(twoWildThreeOfaKind.isThreeOfaKind());
	}
	
	@Test 
	public final void testThreeWildRoyalFlush() {
		threeWildRoyalFlush.evaluate();
		assertTrue(threeWildRoyalFlush.isRoyalFlush());
	}
	
	@Test 
	public final void testThreeWildFiveOfaKind() {
		threeWildFiveOfaKind.evaluate();
		assertTrue(threeWildFiveOfaKind.isFiveOfaKind());
	}
	
	@Test 
	public final void testWildThreeStraightFlush() {
		threeWildStraightFlush.evaluate();
		assertTrue(threeWildStraightFlush.isStraightFlush());
	}
	
	@Test 
	public final void testWildThreeFourOfaKind() {
		threeWildFourOfaKind.evaluate();
		assertTrue(threeWildFourOfaKind.isFourOfaKind());
	}
	

	// Hands without wild cards
	
	private static Hand royalFlush = new Hand(
			aceOfSpades, kingOfSpades, queenOfSpades, jackOfSpades, tenOfSpades);
	
	private static Hand highStraightFlush = new Hand(
			kingOfSpades, queenOfSpades, jackOfSpades, tenOfSpades, nineOfSpades);
	
	private static Hand lowStraightFlush = new Hand(
			queenOfSpades, jackOfSpades, tenOfSpades, nineOfSpades, eightOfSpades);
	
	private static Hand highFourOfaKind = new Hand(
			nineOfSpades, nineOfClubs, nineOfDiamonds, nineOfHearts, twoOfDiamonds);
	
	private static Hand lowFourOfaKind = new Hand(
			tenOfSpades, eightOfSpades, eightOfClubs, eightOfDiamonds, eightOfHearts);
	
	private static Hand highFullHouse = new Hand(
			queenOfSpades, queenOfHearts, queenOfDiamonds, twoOfDiamonds, twoOfClubs);
	
	private static Hand lowFullHouse = new Hand(
			aceOfSpades, aceOfHearts, nineOfSpades, nineOfHearts, nineOfDiamonds);
	
	private static Hand highFlush = new Hand(
			aceOfSpades, kingOfSpades, queenOfSpades, jackOfSpades, nineOfSpades);
	
	private static Hand lowFlush = new Hand(
			aceOfHearts, kingOfHearts, queenOfHearts, jackOfHearts, eightOfHearts);
	
	private static Hand highStraight = new Hand(
			kingOfSpades, queenOfSpades, jackOfSpades, tenOfSpades, nineOfHearts);
	
	private static Hand lowStraight = new Hand(
			queenOfSpades, jackOfSpades, tenOfSpades, nineOfSpades, eightOfHearts);

	private static Hand bottomStraight = new Hand(
			aceOfHearts, fiveOfClubs, fourOfSpades, threeOfHearts, twoOfDiamonds);

	private static Hand bottomThreeOfaKind = new Hand(
			aceOfHearts, jackOfSpades, nineOfSpades, nineOfDiamonds, nineOfClubs);
	
	private static Hand middleThreeOfaKind = new Hand(
			tenOfHearts, nineOfSpades, nineOfDiamonds, nineOfHearts, sevenOfDiamonds);
	
	private static Hand topThreeOfaKind = new Hand(
			nineOfSpades, nineOfDiamonds, nineOfHearts, sevenOfDiamonds, twoOfClubs);

	private static Hand highTwoPair = new Hand(
			aceOfSpades, aceOfHearts, queenOfDiamonds, queenOfSpades, twoOfDiamonds);
	
	private static Hand middleTwoPair = new Hand(
			aceOfDiamonds, aceOfClubs, queenOfHearts, nineOfSpades, nineOfDiamonds);
	
	private static Hand lowTwoPair = new Hand(
			kingOfSpades, nineOfClubs, nineOfHearts, twoOfDiamonds, twoOfClubs);
	
	private static Hand onePairOne = new Hand(
			aceOfHearts, aceOfDiamonds, tenOfSpades, sevenOfDiamonds, twoOfClubs);
	
	private static Hand onePairTwo = new Hand(
			aceOfSpades, queenOfDiamonds, queenOfHearts, sevenOfDiamonds, twoOfClubs);
	
	private static Hand onePairThree = new Hand(
			aceOfSpades, tenOfSpades, eightOfHearts, eightOfDiamonds, twoOfClubs);
	
	private static Hand onePairFour = new Hand(
			queenOfDiamonds, jackOfHearts, nineOfHearts, eightOfClubs, eightOfSpades);
	
	private static Hand highNoPair = new Hand(
			aceOfSpades, tenOfHearts, eightOfDiamonds, sevenOfClubs, threeOfHearts);

	private static Hand lowNoPair = new Hand(
			aceOfHearts, tenOfSpades, eightOfClubs, sevenOfDiamonds, twoOfDiamonds);
		
/**
	 * Test method for {@link pokerBase.Hand#evaluate()}.
	 */
	@Test
	public final void testEvaluate() {
		assertTrue(royalFlush.evaluate());
		Hand h = new Hand();
		h.addCard(aceOfSpades);
		h.addCard(kingOfSpades);
		h.addCard(queenOfSpades);
		h.addCard(jackOfSpades);
		// Returns false if the hand does not contain 5 cards
		assertFalse(h.evaluate());
	}


	/**
	 * Test method for {@link pokerBase.Hand#isRoyalFlush()}.
	 */
	@Test
	public final void testIsRoyalFlush() {
		assertTrue(royalFlush.isRoyalFlush());
		assertFalse(highStraightFlush.isRoyalFlush());
	}

	/**
	 * Test method for {@link pokerBase.Hand#isStraightFlush()}.
	 */
	@Test
	public final void testIsStraightFlush() {
		assertTrue(highStraightFlush.isStraightFlush());
		assertTrue(lowStraightFlush.isStraightFlush());	
		assertFalse(highStraight.isStraightFlush());
	}

	/**
	 * Test method for {@link pokerBase.Hand#isFourOfaKind()}.
	 */
	@Test
	public final void testIsFourOfaKind() {
		assertTrue(highFourOfaKind.isFourOfaKind());
		assertTrue(lowFourOfaKind.isFourOfaKind());
		assertFalse(topThreeOfaKind.isFourOfaKind());
	}

	/**
	 * Test method for {@link pokerBase.Hand#isFullHouse()}.
	 */
	@Test
	public final void testIsFullHouse() {
		assertTrue(highFullHouse.isFullHouse());
		assertTrue(lowFourOfaKind.isFourOfaKind());
		assertFalse(topThreeOfaKind.isFullHouse());
	}
	

	/**
	 * Test method for {@link pokerBase.Hand#isFlush()}.
	 */
	@Test
	public final void testIsFlush() {
		assertTrue(highFlush.isFlush());
		assertTrue(lowFlush.isFlush());
		assertFalse(highStraight.isFlush());
	}

	/**
	 * Test method for {@link pokerBase.Hand#isStraight()}.
	 */
	@Test
	public final void testIsStraight() {
		assertTrue(highStraight.isStraight());
		assertTrue(lowStraight.isStraight());
		assertTrue(bottomStraight.isStraight());
		assertFalse(topThreeOfaKind.isStraight());
	}	
	

	/**
	 * Test method for {@link pokerBase.Hand#isThreeOfaKind()}.
	 */
	@Test
	public final void testIsThreeOfaKind() {
		assertTrue(topThreeOfaKind.isThreeOfaKind());
		assertTrue(middleThreeOfaKind.isThreeOfaKind());
		assertTrue(bottomThreeOfaKind.isThreeOfaKind());
		assertFalse(highTwoPair.isThreeOfaKind());
	}	

	/**
	 * Test method for {@link pokerBase.Hand#isBottomThreeOfaKind()}.
	 */
	@Test
	public final void testIsBottomThreeOfaKind() {
		assertTrue(bottomThreeOfaKind.isBottomThreeOfaKind());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isMiddleThreeOfaKind()}.
	 */
	@Test
	public final void testIsMiddleThreeOfaKind() {
		assertTrue(middleThreeOfaKind.isMiddleThreeOfaKind());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isTopThreeOfaKind()}.
	 */
	@Test
	public final void testIsTopThreeOfaKind() {
		assertTrue(topThreeOfaKind.isTopThreeOfaKind());
	}	
			
	/**
	 * Test method for {@link pokerBase.Hand#isTwoPair()}.
	 */
	@Test
	public final void testIsTwoPair() {
		assertTrue(highTwoPair.isTwoPair());
		assertTrue(middleTwoPair.isTwoPair());
		assertTrue(lowTwoPair.isTwoPair());
		assertFalse(onePairOne.isTwoPair());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isHighTwoPair()}.
	 */
	@Test
	public final void testIsHighTwoPair() {
		assertTrue(highTwoPair.isHighTwoPair());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isMiddleTwoPair()}.
	 */
	@Test
	public final void testIsMiddleTwoPair() {
		assertTrue(middleTwoPair.isMiddleTwoPair());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isLowTwoPair()}.
	 */
	@Test
	public final void testIsLowTwoPair() {
		assertTrue(lowTwoPair.isLowTwoPair());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isOnePair()}.
	 */
	@Test
	public final void testIsOnePair() {
		assertTrue(onePairOne.isOnePair());
		assertTrue(onePairTwo.isOnePair());
		assertTrue(onePairThree.isOnePair());
		assertTrue(onePairFour.isOnePair());
		assertFalse(highNoPair.isOnePair());
	}	
	

	/**
	 * Test method for {@link pokerBase.Hand#isOnePairOne()}.
	 */
	@Test
	public final void testIsOnePairOne() {
		assertTrue(onePairOne.isOnePairOne());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isOnePairTwo()}.
	 */
	@Test
	public final void testIsOnePairTwo() {
		assertTrue(onePairTwo.isOnePairTwo());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isOnePairThree()}.
	 */
	@Test
	public final void testIsOnePairThree() {
		assertTrue(onePairThree.isOnePairThree());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isOnePairFour()}.
	 */
	@Test
	public final void testIsOnePairFour() {
		assertTrue(onePairFour.isOnePairFour());
	}	
	
	/**
	 * Test method for {@link pokerBase.Hand#isNoPair()}.
	 */
	@Test
	public final void testIsNoPair() {
		assertTrue(highNoPair.isNoPair());
		assertTrue(lowNoPair.isNoPair());
	}	
	
	@Test
	public final void testFiveWild() {
		fiveWild.evaluate();
		assertTrue(fiveWild.isFiveOfaKind());
	}
	
	@Test
	public final void testFourWild1() {
		fourWild1.evaluate();
		assertTrue(fourWild1.isFiveOfaKind());
	}
	
	@Test
	public final void testFourWild2() {
		fourWild2.evaluate();
		assertTrue(fourWild2.isFiveOfaKind());
	}
	
	
	/**
	 * Test method for {@link pokerBase.Hand#compareTo(Hand)}.
	 */
	@Test
	public final void testCompareTo() {
		assertTrue(royalFlush.compareTo(highStraightFlush) > 0);
		assertTrue(royalFlush.compareTo(royalFlush) == 0);
		assertTrue(highStraightFlush.compareTo(royalFlush) < 0);

		ArrayList<Hand> handsInOrder = new ArrayList<Hand>(23);
		handsInOrder.add(royalFlush);
		handsInOrder.add(highStraightFlush);
		handsInOrder.add(lowStraightFlush);
		handsInOrder.add(highFourOfaKind);
		handsInOrder.add(lowFourOfaKind);
		handsInOrder.add(highFullHouse);
		handsInOrder.add(lowFullHouse);
		handsInOrder.add(highFlush);
		handsInOrder.add(lowFlush);
		handsInOrder.add(highStraight);
		handsInOrder.add(lowStraight);
		handsInOrder.add(bottomThreeOfaKind);
		handsInOrder.add(middleThreeOfaKind);
		handsInOrder.add(topThreeOfaKind);
		handsInOrder.add(highTwoPair);
		handsInOrder.add(middleTwoPair);
		handsInOrder.add(lowTwoPair);
		handsInOrder.add(onePairOne);
		handsInOrder.add(onePairTwo);
		handsInOrder.add(onePairThree);
		handsInOrder.add(onePairFour);
		handsInOrder.add(highNoPair);
		handsInOrder.add(lowNoPair);
		
		for (int i = 0; i < handsInOrder.size() - 1; i++) {
			/*
 			System.out.println();
			System.out.println(handsInOrder.get(i).evaluationString());
			System.out.println("vs");
			System.out.println(handsInOrder.get(i+1).evaluationString());
			System.out.println("= " + handsInOrder.get(i).compareTo(handsInOrder.get(i+1)));
			*/
			assertTrue(handsInOrder.get(i).compareTo(handsInOrder.get(i+1)) > 0);
		}
	}	
	
	@Test
	public final void toStringTest() {
		System.out.println(bottomStraight);
		System.out.println(threeWildFourOfaKind);
		System.out.println(highFullHouse);
		System.out.println(highNoPair);
		System.out.println(lowNoPair);
		assertTrue(true);
	}

}
