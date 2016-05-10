package pokerBase;

import java.io.Serializable;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;

import pokerEnums.eCardVisibility;

public class GamePlayPlayerHand implements Serializable {

	@XmlElement
	private GamePlay game = null;
	
	@XmlElement
	private Player player = null;
	
	@XmlElement
	private Hand hand = null;
	
	private Hand BestHand = null;

	@XmlElement
	private boolean folded = false;
	
	@XmlElement
	private HashMap<Card, eCardVisibility> cardVisibility = new HashMap<Card, eCardVisibility>();
	
	@XmlElement
	private Player WinningPlayer = null;
	
	public GamePlayPlayerHand(GamePlay gme, Player p) {
		this.game = gme;
		this.player = p;
		this.hand = new Hand();
		this.folded = false;
	}

	public boolean isFolded() {
		return this.folded;
	}
	
	public void setFolded() {
		this.folded = true;
	}
	
	public GamePlay getGame() {
		return game;
	}

	public void setGame(GamePlay game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public void addCardToHand(Card c, eCardVisibility visibility )
	{
		this.hand.AddCardToHand(c);
		this.cardVisibility.put(c, visibility);
	}
	
	public boolean playerCardVisible(Player p, Card c) {
		return this.player.equals(p)
			|| this.WinningPlayer != null
			|| this.cardVisibility.get(c) == eCardVisibility.VisibleEveryone;
	}
	
	public Player getWinningPlayer() {
		return WinningPlayer;
	}

	public void setWinningPlayer(Player winningPlayer) {
		WinningPlayer = winningPlayer;
	}

	public Hand getBestHand() {
		return BestHand;
	}

	public void setBestHand(Hand bestHand) {
		BestHand = bestHand;
	}

	public String toString() {
		return this.player.toString() + "\n" + this.hand.toString();
	}
}
