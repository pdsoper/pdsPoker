package pokerEnums;

public enum Suit {

	HEARTS   ("\u2665"),
	SPADES   ("\u2660"),
	CLUBS    ("\u2663"), 
	DIAMONDS ("\u2666");

	private final String ucStr;

	Suit(String ucStr) {
		this.ucStr = ucStr;
	}

	public String toString() {
		return this.ucStr;
	}
	
}
