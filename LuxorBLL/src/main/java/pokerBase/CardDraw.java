package pokerBase;

import java.io.Serializable;

import pokerEnums.CardDestination;
import pokerEnums.CardVisibility;

public class CardDraw implements Serializable {
	
	private CardDestination destination;
	private CardVisibility visibility;
	
	public CardDraw(CardDestination destination, CardVisibility visibility) {
		super();
		this.destination = destination;
		this.visibility = visibility;
	}
	
	public CardDestination getDestination() {
		return this.destination;
	}
	
	public CardVisibility getVisibility() {
		return this.visibility;
	}
	
	@Override
	public String toString() {
		return "To " + this.destination + ", " + this.visibility;
	}
	
}
