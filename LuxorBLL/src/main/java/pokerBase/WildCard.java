package pokerBase;

import java.io.Serializable;

import pokerEnums.Rank;
import pokerEnums.Suit;

public class WildCard extends Card implements Serializable {
	
	public WildCard() {
	}
	
	public WildCard(WildCard original) {
		super(original);
	}
	
	public WildCard(Rank rank, Suit suit) {
		super(rank, suit);
	}
	
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	@Override
	public String toString() {
		String str = "" + this.getRank() + this.getSuit();
		str += " (wild " + this.getFaceRank() + this.getFaceSuit() + ")";
		return str;
	}
	
}
