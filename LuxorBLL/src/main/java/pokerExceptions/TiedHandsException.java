package pokerExceptions;

import pokerBase.Hand;

public class TiedHandsException extends Exception {

	private int position1;
	private int position2;
	private Hand hand1;
	private Hand hand2;
	
	public TiedHandsException(int p1, int p2, Hand h1, Hand h2) {
		this.position1 = p1;
		this.position2 = p2;
		this.hand1 = h1;
		this.hand2 = h2;
	}
	
	public int getPosition1() {
		return position1;
	}

	public int getPosition2() {
		return position2;
	}

	public Hand getHand1() {
		return hand1;
	}

	public Hand getHand2() {
		return hand2;
	}

	public String toString() {
		return "The two best hands are tied\n" + this.hand1 + "(position " + this.position1 + ")\n" 
				+ this.hand2 + "(position " + this.position2 + ")" ;
	}
	
}
