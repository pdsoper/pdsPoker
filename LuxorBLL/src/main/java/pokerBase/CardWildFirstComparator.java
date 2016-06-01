package pokerBase;

import java.util.Comparator;

public class CardWildFirstComparator implements Comparator<Card> {
	
	public int compare(Card c1, Card c2) {
		if (c1 instanceof WildCard) {
			return -1;
		} else if (c2 instanceof WildCard) {
			return 1;
		} else {
			return c1.compareTo(c2);
		}
	}
	
}
