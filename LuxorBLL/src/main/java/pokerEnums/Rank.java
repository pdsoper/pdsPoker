package pokerEnums;

import javax.xml.bind.annotation.XmlElement;

public enum Rank {

	TWO, 
	THREE, 
	FOUR, 
	FIVE, 
	SIX, 
	SEVEN, 
	EIGHT, 
	NINE,
	TEN, 
	JACK  ("J"), 
	QUEEN ("Q"), 
	KING  ("K"), 
	ACE   ("A");

	private String cardStr;

	Rank() {
		this.cardStr = Integer.toString(this.ordinal() + 2);
	}

	Rank(String str) {
		this.cardStr = str;
	}

	public String toString() {
		return this.cardStr;
	}

}
