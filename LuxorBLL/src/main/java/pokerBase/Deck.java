package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import pokerEnums.Rank;
import pokerEnums.Suit;
import pokerExceptions.DeckException;

/**
 * 
 * @author Bert.Gibbons
 *
 */
public class Deck implements Serializable  {

	private ArrayList<Card> cards;

	/**
	 * Construct a basic 52-card deck
	 */
	public Deck() {
		this.cards = new ArrayList<Card>();
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				this.cards.add(new Card(r, s));
			}
		}
		Collections.shuffle(this.cards);	
	}
	
	/**
	 * Construct a deck with the specified number of jokers
	 * @param nJokers
	 */
	public Deck(int nJokers) {
		this();
		for (int i = 0; i < nJokers; i++) {
			this.cards.add(new Joker());
		}
		Collections.shuffle(this.cards);	
	}
	
	/**
	 * Construct a deck in which the specified cards are wild
	 * @param wildCards
	 */
	public Deck(ArrayList<WildCard> wildCards) {
		this();
		this.replaceWithWild(wildCards);
		Collections.shuffle(this.cards);	
	}
	
	/**
	 * Construct a deck with the specified number of jokers, and 
	 * in which the specified cards are wild (in addition to the jokers)
	 * @param nJokers
	 * @param wildCards
	 */
	public Deck(int nJokers, ArrayList<WildCard> wildCards) {
		this(nJokers);
		this.replaceWithWild(wildCards);
		Collections.shuffle(this.cards);	
	}
	
	/**
	 * Set to wild the cards in this deck that match those in wildCards  
	 * @param wildCards
	 */
	private void replaceWithWild(ArrayList<WildCard> wildCards) {
		for (WildCard wc : wildCards) {
			for (Card c : this.cards) {
				if (c.isWild()) {
					continue;
				}
				if (c.rankAndSuitEquals(wc)) {
					this.cards.remove(c);
					this.cards.add(wc);
					break;
				}
			}
		}
		Collections.shuffle(this.cards);
	}
	
	/**
	 * Return the top card, removing it from the deck.  Throw a DeckException
	 * if the deck runs out of cards.
	 * @return the top card of the deck
	 * @throws DeckException
	 */
	public Card draw() throws DeckException {
		if (this.cards.size() > 0) {
			return this.cards.remove(0);
		} else {
			throw new DeckException();
		} 
	}
	
	/**
	 * Return the top nCards cards from the deck.  Throw a DeckException
	 * if the deck runs out of cards.
	 * @param nCards
	 * @return
	 * @throws DeckException
	 */
	public ArrayList<Card> draw(int nCards) throws DeckException {
		ArrayList<Card> cal = new ArrayList<Card>(); 
		for (int i = 0; i < nCards; i ++) {
			cal.add(this.draw());
		}
		return cal;
	}

	/**
	 * 
	 * @return the number of cards in the deck
	 */
	public int nCards() {
		return this.cards.size();
	}
	
	@Override
	public String toString() {
		String deckStr = "";
		int i = 0;
		int nRanks = Rank.values().length;
		for (Card c : this.cards) {
			deckStr += String.format("%3s ", c);
			i++;
			if (i % nRanks == 0) {
				deckStr += "\n";
			}
		}
		return deckStr;
	}

}
