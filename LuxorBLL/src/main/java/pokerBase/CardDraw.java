package pokerBase;

import java.io.Serializable;

import pokerEnums.eCardCount;
import pokerEnums.eCardDestination;
import pokerEnums.eCardVisibility;


public class CardDraw implements Serializable {

	private static final long serialVersionUID = 1L;
	private eCardCount CardCount;
	private eCardDestination CardDestination;
	private eCardVisibility CardVisibility;
	
	public CardDraw(eCardCount cardCount, eCardDestination cardDestination, eCardVisibility cardVisibility) {
		super();
		CardCount = cardCount;
		CardDestination = cardDestination;
		CardVisibility = cardVisibility;
	}
	
	public eCardCount getCardCount() {
		return CardCount;
	}
	
	public eCardDestination getCardDestination() {
		return CardDestination;
	}
	
	public eCardVisibility getCardVisibility() {
		return CardVisibility;
	}
	
	
}
