package pokerBase;

import java.io.Serializable;
import java.util.UUID;
import javax.xml.bind.annotation.XmlElement;

public class Player implements Serializable {

	private String name;
	private boolean active = false;
	private int position;
	private Hand currentHand;
	
	public Player(String name) {
		this.name = name;
		this.currentHand = new Hand();
	}

	public String getName() {
		return name;
	}
		
	public Hand getHand() {
		return currentHand;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void fold() {
		this.active = false;
		this.currentHand.resetHand();
	}
	
	public void reset() {
		this.active = true;
		this.currentHand.resetHand();
	}
		
	@Override
	public String toString() {
		String playerStr = "Player[" + this.position + "] " + this.name + " ";
		if (this.active) {
			playerStr += this.getHand();
		} else {
			playerStr += "Folded";
		}
		return playerStr;
	}
	
}
