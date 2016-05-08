package pokerEnums;

import javax.xml.bind.annotation.XmlElement;

public enum eRank {
	TWO(2), 
	THREE(3), 
	FOUR(4), 
	FIVE(5), 
	SIX(6), 
	SEVEN(7), 
	EIGHT(8), 
	NINE(9), 
	TEN(10), 
	JACK(11), 
	QUEEN(12), 
	KING(13), 
	ACE(14),
	JOKER(99);

	private int iRankNbr;
	
	private static String[] royaltyRankStr = { "J", "Q", "K", "A" };

	private eRank(int iRankNbr) {
		this.iRankNbr = iRankNbr;
	}

	@XmlElement
	public int getiRankNbr() {
		return iRankNbr;
	}
	
	public String toString() {
		if (this.iRankNbr < 11) {
			return String.valueOf(this.iRankNbr);
		} else if (this.iRankNbr <= 14) {
			return royaltyRankStr[this.iRankNbr - 11];
		} else {
			return "Joker";
		}
	}


	
}
