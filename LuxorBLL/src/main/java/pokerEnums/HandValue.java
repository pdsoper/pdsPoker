package pokerEnums;


public enum HandValue {

	NO_PAIR         ("No Pair"), 
	ONE_PAIR        ("One Pair"), 
	TWO_PAIR        ("Two Pair"),
	THREE_OF_A_KIND ("Three of a Kind"), 
	STRAIGHT        ("Straight"),
	FLUSH           ("Flush"), 
	FULL_HOUSE      ("Full House"), 
	FOUR_OF_A_KIND  ("Four of a Kind"), 
	STRAIGHT_FLUSH  ("Straight Flush"), 
	ROYAL_FLUSH     ("Royal Flush"), 
	FIVE_OF_A_KIND  ("Five of a Kind");
	
	private String valueString;
	
	private HandValue(String str) {
		this.valueString = str;
	}
	
	@Override
	public String toString() {
		return this.valueString;
	}
}
