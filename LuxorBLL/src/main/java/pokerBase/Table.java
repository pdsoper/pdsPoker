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
import pokerExceptions.TiedHandsException;

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
		if (this.dealerPosition < 0) {
			ArrayList<Player> actualPlayers = new ArrayList<Player>();
			for (int i = 0; i < this.maxPlayers; i++) {
				if (this.players[i] != null) {
					actualPlayers.add(this.players[i]);
				}
			}
			int randInt = (int) Math.floor((double) actualPlayers.size() * Math.random());
			this.dealerPosition = actualPlayers.get(randInt).getPosition();
		}
		return this.dealerPosition;
	}

	public void setNextDealerPosition() {
		this.dealerPosition = this.nextActivePlayerPositionAfter(this.getDealerPosition());
		this.currentPlayerPosition = this.nextActivePlayerPositionAfter(this.dealerPosition);
	}

	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public Player getPlayer(int position) {
		return this.players[position];
	}
	
	public int getCurrentDeal() {
		return currentDeal;
	}

	public void addPlayer(Player aPlayer, int position) {
		aPlayer.setActive(!this.gameInProgress);
		aPlayer.setPosition(position);
		this.players[position] = aPlayer;
	}
	
	public void removePlayer(int position) {
		this.players[position] = null;
	}
	
	/**
	 * Find the position of the next active player who has not folded.
	 * @param startPos
	 * @return the position of the next player
	 */
	public int nextActivePlayerPositionAfter(int startPos) {
		int position = 0;
		Player aPlayer = null;
		for (int i = 1; i <= this.maxPlayers; i++) {
			position = (startPos + i) % this.maxPlayers;
			aPlayer = this.getPlayer(position);
			if (aPlayer != null && aPlayer.isActive()) {
				break;
			}
		}
		return position;
	}
	
	public boolean isCardVisibleToPlayer(Card aCard, Player aPlayer) {
		return visibility.get(aCard) == CardVisibility.Everyone
				|| owner.get(aCard).equals(aPlayer);
	}
	
	public int winningPosition() throws TiedHandsException {
		int winIdx = -1;
		int secondIdx = -1;
		if (this.gameOver) {
			Hand best = Hand.WorstHand();
			Hand secondBest = Hand.WorstHand();
			for (int i = 0; i < this.maxPlayers; i++) {
				Player aPlayer = this.players[i];
				if (aPlayer != null && aPlayer.isActive()) {
					if (aPlayer.getHand().compareTo(best) > 0) {
						secondBest = best;
						secondIdx = winIdx;
						best = aPlayer.getHand();
						winIdx = i;
					} else if (aPlayer.getHand().compareTo(secondBest) > 0) {
						secondBest = aPlayer.getHand();
						secondIdx = i;
					}
				}
			} 
			if (best.compareTo(secondBest) == 0) {
				throw new TiedHandsException(winIdx, secondIdx, best, secondBest);
			}
		}
		return winIdx;
	}
	
	public void reset() {
		this.visibility.clear();
		this.owner.clear();
		for (Player aPlayer : this.players) {
			if (aPlayer != null) {
				aPlayer.reset();
			}
		}
	}
	
	public void startNewGame(Game aGame, Deck aDeck) {
		this.reset();
		this.currentGame = aGame;
		this.currentDeck = aDeck;
		this.setNextDealerPosition();
		this.currentDeal = 0;
		this.gameInProgress = true;
		this.gameOver = false;
	}
	
	public void endGame() {
		this.gameInProgress = false;
		this.gameOver = true;
		this.evaluateHands();
		for (Card c : this.visibility.keySet()) {
			if (this.owner.get(c).isActive()) {
				this.visibility.put(c, CardVisibility.Everyone);
			}
		}
	}
	
	public boolean dealCards() throws DeckException {
		if (this.currentDeal >= this.currentGame.nDeals()) {
			this.endGame();
			return false;
		}
		ArrayList<CardDraw> cDraws = this.currentGame.getDeal(this.currentDeal);
		for (CardDraw cDraw : cDraws) {
			this.executeDraw(cDraw);
		}
		this.currentDeal++;
		return true;
	}
	
	public void executeDraw(CardDraw cDraw) throws DeckException {
		Card aCard;
		switch (cDraw.getDestination()) {
		case Community:
			aCard = this.currentDeck.draw();
			this.communityCards.add(aCard);
			this.visibility.put(aCard, cDraw.getVisibility());
			break;
		case Players:
			for (int i = 1; i <= this.maxPlayers; i++) {
				Player aPlayer = this.players[(this.dealerPosition + i) % this.maxPlayers];
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
		int winIdx = -1;
		int tidx1 = -1;
		int tidx2 = -1;
		try {
			winIdx = this.winningPosition();
		} catch (TiedHandsException e) {
			tidx1 = e.getPosition1();
			tidx2 = e.getPosition2();
		}
		String tableStr = "Table\n";
		tableStr += "Dealer = " + this.players[this.dealerPosition].getName() + "\n";
		tableStr += this.currentGame.getName() + ", deal " + this.currentDeal + "\n";
		if (winIdx > -1) {
			tableStr += this.getPlayer(winIdx).getName() + " wins!\n";
		} else if (tidx1 > -1 || tidx2 > 0) {
			tableStr += this.getPlayer(tidx1).getName() + " and " +
					this.getPlayer(tidx2).getName() + " are tied. (Unusual!)\n";
		}
		if (this.communityCards.size() > 0) {
			tableStr += "Community = ";
			for (Card c : this.communityCards) {
				tableStr += c + " ";
			}
			tableStr += "\n";
		}
		for (int i = 0; i < this.maxPlayers; i++) {
			if (this.players[i] != null) {
				if (i == winIdx) {
					tableStr += "*";
				} else if (i == tidx1 || i == tidx2) {
					tableStr += "t";
				} else {
					tableStr += " ";
				}
				tableStr += " " + this.players[i] + "\n";
			}
		}
		if (winIdx >= 0) {
			tableStr += "* = winner\n";
		} else if (tidx1 >= 0) {
			tableStr += "t = tied\n";
		}
		return tableStr;
	}
	
}
