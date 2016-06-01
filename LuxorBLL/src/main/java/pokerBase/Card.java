package pokerBase;

import java.io.Serializable;
import java.util.Comparator;

import javax.xml.bind.annotation.XmlElement;

import pokerEnums.CardVisibility;
import pokerEnums.Rank;
import pokerEnums.Suit;

public class Card implements Comparable<Card>,  Serializable {

	protected Rank rank;
	protected Suit suit;
	protected Rank faceRank;
	protected Suit faceSuit;
	protected int imageNum;

	public Card () {
	}
	
	public Card(Card original) {
		this();
		this.rank = original.getRank();
		this.suit = original.getSuit();
		this.faceRank = original.getFaceRank();
		this.faceSuit = original.getFaceSuit();
		this.imageNum = original.imageNum;
	}
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.faceRank = rank;
		this.faceSuit = suit;
		this.imageNum = (suit.ordinal() * 13) + rank.ordinal() + 1;
	}
	
	public Rank getRank() {
		return this.rank;
	}
	
	public Suit getSuit() {
		return this.suit;
	}
	
	public Rank getFaceRank() {
		return faceRank;
	}
	
	public Suit getFaceSuit() {
		return faceSuit;
	}

	public int getImageNum() {
		return imageNum;
	}

	public boolean isWild() {
		return this instanceof WildCard;
	}

	/**
	 * Test two cards for equality of rank
	 * @param otherCard the comparison card
	 * @return true if the rank of this card is equal to the rank of otherCard, otherwise false
	 */
	public boolean rankEquals(Card otherCard) {
		return this.getRank() == otherCard.getRank();
	}
	
	public boolean isOneRankHigherThan(Card otherCard) {
		return this.getRank().ordinal() == otherCard.getRank().ordinal() + 1;
	}

	/**
	 * Test two cards for equality of suit
	 * @param otherCard the comparison card
	 * @return true if the suit of this card is the same as the suit of otherCard, otherwise false
	 */
	public boolean suitEquals(Card otherCard) {
		return this.getSuit() == otherCard.getSuit();
	}

	/**
	 * Test two cards for equality of rank and suit
	 * @param otherCard the comparison card
	 * @return true if the rank and suit of this card are the same as the rank and suit of otherCard, otherwise false
	 */
	public boolean rankAndSuitEquals(Card otherCard) {
		return this.rankEquals(otherCard) && this.suitEquals(otherCard);
	}
	
	/**
	 * Compare the ranks of two cards for sorting in descending order
	 * @param o the comparison card
	 * @return the difference between the rank of the other card
	 * and the rank of this card
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Card c) {
		return -1 * this.rank.compareTo(c.rank);
	}
	
	@Override
	public String toString() {
		return this.getRank().toString() + this.getSuit().toString();
	}
	
	
}
