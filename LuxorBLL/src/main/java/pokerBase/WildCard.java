package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import pokerEnums.Rank;
import pokerEnums.Suit;

public class WildCard extends Card implements Serializable {
	
	public static final ArrayList<WildCard> Deuces() {
		ArrayList<WildCard> wilds = new ArrayList<WildCard>(4);
		wilds.add(new WildCard(Rank.TWO, Suit.CLUBS));
		wilds.add(new WildCard(Rank.TWO, Suit.DIAMONDS));
		wilds.add(new WildCard(Rank.TWO, Suit.HEARTS));
		wilds.add(new WildCard(Rank.TWO, Suit.SPADES));
		return wilds;
	}
	
	public static final ArrayList<WildCard> AcesAndEights() {
		ArrayList<WildCard> wilds = new ArrayList<WildCard>(8);
		wilds.add(new WildCard(Rank.ACE, Suit.CLUBS));
		wilds.add(new WildCard(Rank.ACE, Suit.DIAMONDS));
		wilds.add(new WildCard(Rank.ACE, Suit.HEARTS));
		wilds.add(new WildCard(Rank.ACE, Suit.SPADES));
		wilds.add(new WildCard(Rank.EIGHT, Suit.CLUBS));
		wilds.add(new WildCard(Rank.EIGHT, Suit.DIAMONDS));
		wilds.add(new WildCard(Rank.EIGHT, Suit.HEARTS));
		wilds.add(new WildCard(Rank.EIGHT, Suit.SPADES));
		return wilds;
	}
	
	public static final ArrayList<WildCard> OneEyedJacks() {
		ArrayList<WildCard> wilds = new ArrayList<WildCard>(2);
		wilds.add(new WildCard(Rank.JACK, Suit.HEARTS));
		wilds.add(new WildCard(Rank.JACK, Suit.SPADES));
		return wilds;
	}
	
	public static final ArrayList<WildCard> CombineWilds(ArrayList<ArrayList<WildCard>> wildLists) {
		ArrayList<WildCard> wilds = new ArrayList<WildCard>();
		for (ArrayList<WildCard> wcl : wildLists) {
			wilds.addAll(wcl);
		}
		return wilds;
	}
	
	public WildCard() {
	}
	
	public WildCard(WildCard original) {
		super(original);
	}
	
	public WildCard(Rank rank, Suit suit) {
		super();
		this.faceRank = rank;
		this.faceSuit = suit;
		this.imageNum = (suit.ordinal() * 13) + rank.ordinal() + 1;
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += (this.getRank() == null) ? "_" : this.getRank();
		str += (this.getSuit() == null) ? "_" : this.getSuit();
		str += " (wild " + this.getFaceRank() + this.getFaceSuit() + ")";
		return str;
	}
	
}
