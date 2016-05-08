package pokerBase;

import java.util.ArrayList;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

public class HandScore implements Serializable {


	private int HandStrength;
	private int HiHand;
	private int LoHand = 0;
	private ArrayList<Card> Kickers = new ArrayList<Card>();
	private boolean Natural = true;
	
	public HandScore() {}
	
	public HandScore(int handStrength, int hiHand, int loHand, ArrayList<Card> kickers) {
		super();
		HandStrength = handStrength;
		HiHand = hiHand;
		LoHand = loHand;
		Kickers = kickers;
	}

	public int getHandStrength() {
		return HandStrength;
	}

	public void setHandStrength(int handStrength) {
		HandStrength = handStrength;
	}

	public int getHiHand() {
		return HiHand;
	}

	public void setHiHand(int hiHand) {
		HiHand = hiHand;
	}

	public int getLoHand() {
		return LoHand;
	}

	public void setLoHand(int loHand) {
		LoHand = loHand;
	}

	public ArrayList<Card> getKickers() {
		return Kickers;
	}

	public void setKickers(ArrayList<Card> kickers) {
		Kickers = kickers;
	}

	boolean isNatural() {
		return Natural;
	}

	void setNatural(boolean natural) {
		Natural = natural;
	}
	
	
}
