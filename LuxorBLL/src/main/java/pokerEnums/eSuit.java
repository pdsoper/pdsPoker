package pokerEnums;

public enum eSuit {

	HEARTS(1), SPADES(2), CLUBS(3), DIAMONDS(4), JOKER(99);
	
	private static String[] suitStr = { "\u2665", "\u2660", "\u2663", "\u2666" };
	
	private int iSuitNbr;

	private eSuit(int iSuitNbr) {
		this.iSuitNbr = iSuitNbr;
	}

	public int getiSuitNbr() {
		return iSuitNbr;
	}
	
	public String toString() {
		if (this.iSuitNbr == 99) {
			return "";
		} else {
			return suitStr[this.iSuitNbr - 1];
		}
	}
	
	
}
