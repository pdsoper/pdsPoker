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
	
	private int dealerPosition;
	private int currentPlayerPosition;
	private int currentDeal;
	
	private boolean gameInProgress = false;

	public Table(int maxPlayers) {
		this.maxPlayers = maxPlayers;
		this.players = new Player[maxPlayers];
	}
	
	public Player[] getTablePlayers() {
		return players;
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
		Hand bestSoFar = Hand.WorstHand();
		for (int i = 0; i < this.maxPlayers; i++) {
			Player aPlayer = this.players[i];
			if (aPlayer != null && aPlayer.isActive() 
					&& aPlayer.getHand().compareTo(bestSoFar) > 0) {
				bestSoFar = aPlayer.getHand();
				winIdx = i;
			}
		}
		return winIdx;
	}
	
	public void startNewGame(Game aGame, Deck aDeck, int dealerPosition) {
		this.currentGame = aGame;
		this.currentDeck = aDeck;
		this.dealerPosition = dealerPosition;
		this.currentPlayerPosition = (this.dealerPosition + 1) % this.maxPlayers;
		for (Player aPlayer : this.players) {
			aPlayer.reset();
		}
		this.currentDeal = 0;
		this.gameInProgress = true;
	}
	
	public boolean dealCards() throws DeckException {
		if (this.currentDeal >= this.currentGame.nDeals()) {
			this.gameInProgress = false;
			return false;
		}
		ArrayList<CardDraw> draws = this.currentGame.getDeal(this.currentDeal);
		Card aCard;
		Player aPlayer;
		for (CardDraw draw : draws) {
			switch (draw.getDestination()) {
			case Community:
				aCard = this.currentDeck.draw();
				this.communityCards.add(aCard);
				this.visibility.put(aCard, draw.getVisibility());
				break;
			case Players:
				for (int i = 1; i <= this.maxPlayers; i++) {
					aPlayer = this.players[(this.dealerPosition + i) % this.maxPlayers];
					if (aPlayer != null && aPlayer.isActive()) {
						aCard = this.currentDeck.draw();
						aPlayer.getHand().addCard(aCard);
						this.visibility.put(aCard, draw.getVisibility());
						this.owner.put(aCard, aPlayer);
					}
				}
				break;
			}
		}
		this.currentDeal++;
		return true;
	}
	
	public String toString() {
		int winIdx = -1;
		if (!this.gameInProgress) {
			winIdx = this.winningPosition();
		}
		String tableStr = "Table\n";
		if (this.communityCards.size() > 0) {
			tableStr += "Community = ";
			for (Card c : this.communityCards) {
				tableStr += c + " ";
			}
			tableStr += "\n";
		}
		for (int i = 0; i < this.maxPlayers; i++) {
			tableStr += (i == winIdx) ? "* " : " ";
			tableStr += "Player[" + i + "] = " + this.players[i];
		}
		if (winIdx >= 0) {
			tableStr += "* = winner\n";
		}
		return tableStr;
	}
	
}
