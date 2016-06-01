package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pokerEnums.HandValue;
import pokerEnums.Rank;
import pokerEnums.Suit;

/**
 * @author paulsoper
 *
 */
public class Hand implements Comparable<Hand>, Serializable {
	
	private ArrayList<Card> cards;
	private ArrayList<Card> tieBreakers;
	private HandValue value = null;
	private int nWilds = 0;
	
	static Hand WorstHand() {
		Hand h = new Hand();
		h.addCard(new Card (Rank.TWO, Suit.CLUBS));
		h.addCard(new Card (Rank.THREE, Suit.DIAMONDS));
		h.addCard(new Card (Rank.FOUR, Suit.HEARTS));
		h.addCard(new Card (Rank.FIVE, Suit.SPADES));
		h.addCard(new Card (Rank.SEVEN, Suit.CLUBS));
		return h;
	}
	
	public Hand() {
		this.cards = new ArrayList<Card>();
		this.tieBreakers = new ArrayList<Card>();
	}
	
	public Hand(Hand original) {
		this();
		this.setTo(original);
	}
	
	/**
	 * Create a hand consisting of the five specified cards
	 * @param c1 card 1
	 * @param c2 card 2
	 * @param c3 card 3
	 * @param c4 card 4
	 * @param c5 card 5
	 */
	public Hand(Card c1, Card c2, Card c3, Card c4, Card c5) {
		this();
		this.addCard(c1);
		this.addCard(c2);
		this.addCard(c3);
		this.addCard(c4);
		this.addCard(c5);
	}

	/**
	 * Set the attributes of this hand to those of the argument
	 * @param aHand
	 */
	public void setTo(Hand aHand) {
		this.resetHand();
		for (Card c : aHand.cards) {
			if (c instanceof Joker) {
				this.addCard(new Joker((Joker) c));
			} else if (c instanceof WildCard) {
				this.addCard(new WildCard((WildCard) c)); 
			} else {
				this.cards.add(new Card(c));
			}
		}
		for (Card c: aHand.tieBreakers) {
			 if (c instanceof Joker) {
				 this.tieBreakers.add(new Joker((Joker) c));
			} else if (c instanceof WildCard) {
				this.tieBreakers.add(new WildCard((WildCard) c)); 
			} else {
				this.tieBreakers.add(new Card(c));
			}
		}
		this.value = aHand.value;
	}
	
	/**
	 * Reset all variables of this hand to their initial default values
	 */
	public void resetHand() {
		this.cards.clear();
		this.tieBreakers.clear();
		this.nWilds = 0;
		this.value = null;
	}

	/**
	 * add specified card to this hand
	 * @param c The card to be added
	 */
	public void addCard(Card c){
		this.cards.add(c);
		if (c instanceof WildCard) {
			this.nWilds++;
		}
	}

	/**
	 * Number of cards in this hand
	 * @return the number of cards in this hand
	 */
	public int nCards() {
		return this.cards.size();
	}

	/**
	 * Return the value of this hand (no pair, one pair, etc.).  
	 * @return the value of this hand
	 */
	public HandValue getValue() {
		if (this.value == null) {
			this.evaluate();
		}
		return this.value;
	}

	/**
	 * Return the tie breakers for this hand
	 * @return the ArrayList of Cards used to break ties
	 */
	public ArrayList<Card> getTiebreakers() {
		return this.tieBreakers;
	}
	
	public boolean evaluateWild() {
		switch (this.nWilds) {
		case 0:
			return this.evaluateWithCurrentRanks();
		case 1:
			return this.evaluateOneWild();
		case 2:
			return this.evaluateTwoWild();
		case 3:
			return this.evaluateThreeWild();
		case 4:
			return this.makeFourWild();
		case 5:
			return this.makeFiveWild();
		default:
			return false;
		}
	}
	
	public boolean makeFiveWild() {
		Collections.sort(this.cards, new CardWildFirstComparator());
		for (Card c : this.cards) {
			WildCard wc = (WildCard) c;
			wc.setRank(Rank.ACE);
			wc.setSuit(Suit.SPADES);
		}
		this.value = HandValue.FIVE_OF_A_KIND;
		this.tieBreakers.clear();
		this.tieBreakers.add(this.cards.get(0));
		return true;
	}

	public boolean makeFourWild() {
		Collections.sort(this.cards, new CardWildFirstComparator());
		Rank r = this.cards.get(4).getRank();
		Suit s = this.cards.get(4).getSuit();
		for (int i = 0; i < 4; i++) {
			WildCard wc = (WildCard) this.cards.get(i);
			wc.setRank(r);
			wc.setSuit(s);
		}
		this.value = HandValue.FIVE_OF_A_KIND;
		this.tieBreakers.clear();
		this.tieBreakers.add(this.cards.get(4));
		return true; 
	}
	
	public boolean evaluateOneWild() {
		Collections.sort(this.cards, new CardWildFirstComparator());
		Hand bestSoFar = WorstHand();
		Suit s = this.cards.get(4).getSuit();
		WildCard wc1 = (WildCard) this.cards.get(0);
		wc1.setSuit(s);
		for (Rank r : Rank.values()) {
			wc1.setRank(r);
			this.evaluateWithCurrentRanks();
			if (this.compareTo(bestSoFar) > 0) {
				bestSoFar = new Hand(this);
			}
		}
		this.setTo(bestSoFar);
		return true;
	}
	
	public boolean evaluateTwoWild() {
		Collections.sort(this.cards, new CardWildFirstComparator());
		Hand bestSoFar = WorstHand();
		Suit s = this.cards.get(4).getSuit();
		WildCard wc1 = (WildCard) this.cards.get(0);
		WildCard wc2 = (WildCard) this.cards.get(0);
		wc1.setSuit(s);
		wc2.setSuit(s);
		for (Rank r1 : Rank.values()) {
			for (Rank r2 : Rank.values()) {
				wc1.setRank(r1);
				wc2.setRank(r2);
				this.evaluateWithCurrentRanks();
				if (this.compareTo(bestSoFar) > 0) {
					bestSoFar = new Hand(this);
				}
			}
		}
		this.setTo(bestSoFar);
		return true;
	}
	
	public boolean evaluateThreeWild() {
		Collections.sort(this.cards, new CardWildFirstComparator());
		Hand bestSoFar = WorstHand();
		Suit s = this.cards.get(4).getSuit();
		WildCard wc1 = (WildCard) this.cards.get(0);
		WildCard wc2 = (WildCard) this.cards.get(1);
		WildCard wc3 = (WildCard) this.cards.get(2);
		wc1.setSuit(s);
		wc2.setSuit(s);
		wc3.setSuit(s);
		for (Rank r1 : Rank.values()) {
			for (Rank r2 : Rank.values()) {
				for (Rank r3 : Rank.values()) {
					wc1.setRank(r1);
					wc2.setRank(r2);
					wc3.setRank(r3);
					this.evaluateWithCurrentRanks();
					if (this.compareTo(bestSoFar) > 0) {
						bestSoFar = new Hand(this);
					}
				}
			}
		}
		this.setTo(bestSoFar);
		return true;
	}	
	
	/**
	 * Evaluate this hand. Store the value and tie breakers
	 * @return true
	 */
	public boolean evaluate() {
		if (this.nCards() != 5) {
			return false;
		}  
		if (this.nWilds == 0) {
			return this.evaluateWithCurrentRanks();
		} else {
			return this.evaluateWild();
		}
	}

	/**
	 * Evaluate this hand. Store the value and tie breakers
	 * @return true
	 */
	public boolean evaluateWithCurrentRanks() {
		Collections.sort(this.cards);
		return     this.isFiveOfaKind()
				|| this.isRoyalFlush()
				|| this.isStraightFlush()
				|| this.isFourOfaKind()
				|| this.isFullHouse()
				|| this.isFlush()
				|| this.isStraight()
				|| this.isThreeOfaKind()
				|| this.isTwoPair()
				|| this.isOnePair()
				|| this.isNoPair();
	}
	
	/**
	 * Determine whether this hand is five of a kind
	 * @return true if this hand is five of a kind
	 */
	public boolean isFiveOfaKind() {
		if (this.cards.get(0).rankEquals(this.cards.get(4))) {
			this.value = HandValue.FIVE_OF_A_KIND;	
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			return true; 
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is a royal flush,
	 * assuming it does not have a higher value
	 * @return true if this hand is royal flush
	 */
	public boolean isRoyalFlush() {
		if (   this.isStraightFlush() 
			&& this.cards.get(0).getRank() == Rank.ACE) {
			this.value = HandValue.ROYAL_FLUSH;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is a straight flush,
	 * assuming it does not have a higher value
	 * @return true if this hand is a straight flush
	 */
	public boolean isStraightFlush() {
		if (this.isFlush() && this.isStraight()) {
			this.value = HandValue.STRAIGHT_FLUSH;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is four of a kind,
	 * assuming it does not have a higher value
	 * @return true if this hand is four of a kind
	 */
	public boolean isFourOfaKind() {
		if (   (this.cards.get(0).rankEquals(this.cards.get(3)))
		    || (this.cards.get(1).rankEquals(this.cards.get(4)))) {
			this.value = HandValue.FOUR_OF_A_KIND;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(1));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is a full house
	 * assuming it does not have a higher value
	 * @return true if this hand is full house
	 */
	public boolean isFullHouse() {
		if (   (this.cards.get(0).rankEquals(this.cards.get(2)))	
	    	&& (this.cards.get(3).rankEquals(this.cards.get(4)))) {
			this.value = HandValue.FULL_HOUSE;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else if ( (this.cards.get(0).rankEquals(this.cards.get(1)))	
				 && (this.cards.get(2).rankEquals(this.cards.get(4)))) {
			this.value = HandValue.FULL_HOUSE;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(4));
			this.tieBreakers.add(this.cards.get(0));
			return true;
		} else
			return false;
	}

	/**
	 * Determine whether this hand is a flush,
	 * assuming it does not have a higher value
	 * @return true if this hand is flush
	 */
	public boolean isFlush() {
		if (   this.cards.get(1).suitEquals(this.cards.get(0))
			&& this.cards.get(2).suitEquals(this.cards.get(0))
			&& this.cards.get(3).suitEquals(this.cards.get(0))
			&& this.cards.get(4).suitEquals(this.cards.get(0))) {
			this.value = HandValue.FLUSH;
			this.tieBreakers.clear();
			for (Card c : this.cards) {
				this.tieBreakers.add(c);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is a straight,
	 * assuming it does not have a higher value
	 * @return true if this hand is a straight
	 */
	public boolean isStraight() {
		if (   this.cards.get(1).isOneRankHigherThan(this.cards.get(2))
			&& this.cards.get(2).isOneRankHigherThan(this.cards.get(3))
			&& this.cards.get(3).isOneRankHigherThan(this.cards.get(4))) {
			if (this.cards.get(0).isOneRankHigherThan(this.cards.get(1))) {
				this.value = HandValue.STRAIGHT;
				this.tieBreakers.clear();
				this.tieBreakers.add(this.cards.get(0));
				return true;
	 		} else if (this.cards.get(0).getRank() == Rank.ACE && this.cards.get(4).getRank() == Rank.TWO) {
				this.value = HandValue.STRAIGHT;
				this.tieBreakers.clear();
				this.tieBreakers.add(this.cards.get(1));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is three of a kind,
	 * assuming it does not have a higher value
	 * @return true if this hand is three of a kind
	 */
	public boolean isThreeOfaKind() {
		return     this.isTopThreeOfaKind()
				|| this.isMiddleThreeOfaKind()
				|| this.isBottomThreeOfaKind();
	}
	
	/**
	 * Test for three of a kind in the highest three cards
	 * @return true if the highest three cards in the hand form three of a kind
	 */
	public boolean isTopThreeOfaKind() {
		if (this.cards.get(0).rankEquals(this.cards.get(2))) {
			this.value = HandValue.THREE_OF_A_KIND;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test for three of a kind in the middle three cards
	 * @return true if the middle three cards in the hand form three of a kind
	 */
	public boolean isMiddleThreeOfaKind() {
		if (this.cards.get(1).rankEquals(this.cards.get(3))) {
			this.value = HandValue.THREE_OF_A_KIND;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(1));
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Test for three of a kind in the lowest three cards
	 * @return true if the lowest three cards in the hand form three of a kind
	 */
	public boolean isBottomThreeOfaKind() {
		if (this.cards.get(2).rankEquals(this.cards.get(4))) {
			this.value = HandValue.THREE_OF_A_KIND;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(2));
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(1));
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Determine whether this hand is two pair
	 * assuming it does not have a higher value
	 * @return true if this hand is two pair
	 */
	public boolean isTwoPair() {
		return     this.isHighTwoPair() 
				|| this.isMiddleTwoPair() 
				|| this.isLowTwoPair();
	}

	/**
	 * Test for two pair in the top four cards
	 * @return true if the highest four cards in the hand form two pair
	 */
	public boolean isHighTwoPair() {
		if (   this.cards.get(0).rankEquals(this.cards.get(1)) 
		    && this.cards.get(2).rankEquals(this.cards.get(3))) {
			this.value = HandValue.TWO_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(2));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test for two pair in the top two and bottom two cards
	 * @return true if the top two and bottom two cards in the hand form two pair
	 */
	public boolean isMiddleTwoPair() {
		if (   this.cards.get(0).rankEquals(this.cards.get(1)) 
		    && this.cards.get(3).rankEquals(this.cards.get(4))) {
			this.value = HandValue.TWO_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(2));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test for two pair in the bottom four cards
	 * @return true if the bottom four cards in the hand form two pair
	 */

	public boolean isLowTwoPair() {
		if (   this.cards.get(1).rankEquals(this.cards.get(2)) 
		    && this.cards.get(3).rankEquals(this.cards.get(4))) {
			this.value = HandValue.TWO_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(1));
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(0));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determine whether this hand is a one pair,
	 * assuming it does not have a higher value
	 * @return true if this hand is one pair
	 */
	public boolean isOnePair() {
		return     this.isOnePairOne()
				|| this.isOnePairTwo()
				|| this.isOnePairThree()
				|| this.isOnePairFour();
	}
	
	/**
	 * Test whether the top two cards form a pair
	 * @return true if the top two cards form a pair
	 */
	public boolean isOnePairOne() {
		if (this.cards.get(0).rankEquals(this.cards.get(1))) {
			this.value = HandValue.ONE_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(2));
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test whether the second and third highest cards form a pair
	 * @return true if the second and third highest cards form a pair
	 */
	public boolean isOnePairTwo() {
		if (this.cards.get(1).rankEquals(this.cards.get(2))) {
			this.value = HandValue.ONE_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(1));
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test whether the third and fourth highest cards form a pair
	 * @return true if the third and fourth highest cards form a pair
	 */
	public boolean isOnePairThree() {
		if (this.cards.get(2).rankEquals(this.cards.get(3))) {
			this.value = HandValue.ONE_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(2));
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(1));
			this.tieBreakers.add(this.cards.get(4));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Test whether the lowest two cards form a pair
	 * @return true if the lowest two cards form a pair
	 */
		public boolean isOnePairFour() {
		if (this.cards.get(3).rankEquals(this.cards.get(4))) {
			this.value = HandValue.ONE_PAIR;
			this.tieBreakers.clear();
			this.tieBreakers.add(this.cards.get(3));
			this.tieBreakers.add(this.cards.get(0));
			this.tieBreakers.add(this.cards.get(1));
			this.tieBreakers.add(this.cards.get(2));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * If the hand has no higher rank, assign in a value of No Pair
	 * @return true
	 */
	public boolean isNoPair() {
		this.value = HandValue.NO_PAIR;
		this.tieBreakers.clear();
		for (Card c : this.cards) {
			this.tieBreakers.add(c);
		}
		return true;
	}

	/** 
	 * Return the difference between the rank of this
	 * hand and the rank of o, the other hand
	 * @param other the other hand
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Hand other) {
		if (this.value == null) {
			this.evaluateWithCurrentRanks();
		}
		if (other.value == null) {
			other.evaluateWithCurrentRanks();
		}
		int comp = this.value.compareTo(other.value);
		if (comp != 0) {
			return comp * 100;
		} else {
			for (int i = 0; i < this.tieBreakers.size(); i++) {
				comp = other.tieBreakers.get(i).compareTo(this.tieBreakers.get(i));
				if (comp != 0) {
					return comp;
				}
			}
			return 0;
		}
	}

	public String toString() {
		String handStr = "";
		for (Card c : this.tieBreakers) {
			handStr += c + " ";
		}
		if (this.value != null) {
			handStr += " " + this.getValue() + " with tiebreakers ";
		}
		return handStr;
	}
	
}

