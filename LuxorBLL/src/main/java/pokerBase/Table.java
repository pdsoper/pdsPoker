package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import pokerEnums.CardVisibility;
import pokerExceptions.DeckException;

/**
 * @author paulsoper
 *
 */
public class Table implements Serializable {
	
	private int maxPlayers;
	private Player[] players;

	private Game currentGame;
	private Deck currentDeck;
	private ArrayList<Card> communityCards;
	
	private HashMap<Card, CardVisibility> visibility = new HashMap<Card, CardVisibility>();
	private HashMap<Card, Player> owner = new HashMap<Card, Player>();
	
	private int dealerPosition = -1;
	private int currentPlayerPosition;
	private int currentDeal;
	
	private boolean gameInProgress = false;
	private boolean gameOver = false;

	public Table(int maxPlayers) {
		this.maxPlayers = maxPlayers;
		this.players = new Player[maxPlayers];
		this.communityCards = new ArrayList<Card>();
	}
	
	public int getDealerPosition() {
		return dealerPosition;
	}

	public void setDealerPosition(int dealerPosition) {
		this.dealerPosition = dealerPosition;
	}

	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public Player getPlayerAtPosition(int position) {
		return this.players[position];
	}
	
	public void addPlayerToTable(Player aPlayer, int position) {
		aPlayer.setActive(!this.gameInProgress);
		this.players[position] = aPlayer;
	}
	
	/**
	 * Find the next player who has not folded, and update
	 * currentPlayerPosition accordingly.
	 * @return the next active player
	 */
	public Player nextActivePlayer() {
		int position = 0;
		Player aPlayer = null;
		for (int i = 1; i <= this.maxPlayers; i++) {
			position = (this.currentPlayerPosition + i) % this.maxPlayers;
			aPlayer = this.getPlayerAtPosition(position);
			if (aPlayer != null && aPlayer.isActive()) {
				break;
			}
		}
		this.currentPlayerPosition = position;
		return aPlayer;
	}
	
	public boolean isCardVisibleToPlayer(Card aCard, Player aPlayer) {
		return visibility.get(aCard) == CardVisibility.Everyone
				|| owner.get(aCard).equals(aPlayer);
	}
	
	public int winningPosition() {
		int winIdx = -1;
		if (this.gameOver) {
			Hand bestSoFar = Hand.WorstHand();
			for (int i = 0; i < this.maxPlayers; i++) {
				Player aPlayer = this.players[i];
				if (aPlayer != null && aPlayer.isActive() 
						&& aPlayer.getHand().compareTo(bestSoFar) > 0) {
					bestSoFar = aPlayer.getHand();
					winIdx = i;
				}
			}
		}
		return winIdx;
	}
	
	public void startNewGame(Game aGame, Deck aDeck) {
		this.currentGame = aGame;
		this.currentDeck = aDeck;
		if (this.dealerPosition < 0) {
			this.dealerPosition = (int) Math.floor((double) this.maxPlayers * Math.random());
		} else {
			this.dealerPosition++;
		}
		this.currentPlayerPosition = (this.dealerPosition + 1) % this.maxPlayers;
		for (Player aPlayer : this.players) {
			aPlayer.reset();
		}
		this.currentDeal = 0;
		this.gameInProgress = true;
		this.gameOver = false;
	}
	
	public boolean dealCards() throws DeckException {
		if (this.currentDeal >= this.currentGame.nDeals()) {
			this.gameInProgress = false;
			this.gameOver = true;
			this.evaluateHands();
			return false;
		}
		ArrayList<CardDraw> cDraws = this.currentGame.getDeal(this.currentDeal);
		Card aCard;
		Player aPlayer;
		for (CardDraw cDraw : cDraws) {
			switch (cDraw.getDestination()) {
			case Community:
				aCard = this.currentDeck.draw();
				this.communityCards.add(aCard);
				this.visibility.put(aCard, cDraw.getVisibility());
				break;
			case Players:
				for (int i = 1; i <= this.maxPlayers; i++) {
					aPlayer = this.players[(this.dealerPosition + i) % this.maxPlayers];
					if (aPlayer != null && aPlayer.isActive()) {
						aCard = this.currentDeck.draw();
						aPlayer.getHand().addCard(aCard);
						this.visibility.put(aCard, cDraw.getVisibility());
						this.owner.put(aCard, aPlayer);
					}
				}
				break;
			}
		}
		this.currentDeal++;
		return true;
	}
	
	public boolean evaluateHands() {
		if (!this.gameOver) {
			return false;
		}
		for (int i = 0; i < this.maxPlayers; i++) {
			Player aPlayer = this.players[i];
			if (aPlayer != null && aPlayer.isActive()) { 
				aPlayer.getHand().evaluate();
			}
		}
		return true;
	}
	
	public String toString() {
		int winIdx = this.winningPosition();
		String tableStr = "Table\n";
		tableStr += "Dealer = Player[" + this.dealerPosition + "] " + this.players[this.dealerPosition].getName()  + "\n";
		tableStr += this.currentGame.getName() + ", deal " + this.currentDeal + "\n";
		if (this.communityCards.size() > 0) {
			tableStr += "Community = ";
			for (Card c : this.communityCards) {
				tableStr += c + " ";
			}
			tableStr += "\n";
		}
		for (int i = 0; i < this.maxPlayers; i++) {
			tableStr += (i == winIdx) ? "* " : "  ";
			tableStr += "Player[" + i + "] = " + this.players[i] + "\n";
		}
		if (winIdx >= 0) {
			tableStr += "* = winner\n";
		}
		return tableStr;
	}
	
}
