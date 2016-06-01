package pokerBase;

import java.io.Serializable;

public class Joker extends WildCard implements Serializable {

private static int JokerCount = 0;
	
	public Joker() {
		// Alternate images of black and red jokers
		this.imageNum = 53 + (JokerCount % 2);
		JokerCount++;
	}
	
	public Joker(Joker original) {
		super(original);
	}

	@Override
	public String toString() {
		String jokerStr = "";
		jokerStr += (this.getRank() == null) ? "_" : this.getRank();
		jokerStr += (this.getSuit() == null) ? "_" : this.getSuit();
		return jokerStr + " (Joker)";
	}
	
}
