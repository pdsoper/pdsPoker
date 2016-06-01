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
