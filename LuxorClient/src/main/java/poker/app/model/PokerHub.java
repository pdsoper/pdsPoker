package poker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.DeckException;
import exceptions.HandException;
import netgame.common.Hub;
import pokerBase.Action;
import pokerBase.Card;
import pokerBase.CardDraw;
import pokerBase.Deck;
import pokerBase.GamePlay;
import pokerBase.GamePlayPlayerHand;
import pokerBase.Hand;
import pokerBase.Player;
import pokerBase.Rule;
import pokerBase.Table;
import pokerEnums.eAction;
import pokerEnums.eCardVisibility;
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eGameState;

public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;
	// private PokerGameState state;
	private eGameState eGameState;
	private eDrawCount currentDraw;

	public PokerHub(int port) throws IOException {
		super(port);
		this.setAutoreset(true);
	}

	// Modified for extra credit
	// nPlayerConnections() was added to Hub.java
	protected void playerConnected(int playerID) {
		if (HubGamePlay != null && this.nPlayerConnections() == HubGamePlay.getRule().GetMaxNumberOfPlayers()) {
			shutdownServerSocket();
		}
	}

	// Modified for extra credit
	protected void playerDisconnected(int playerID) {
		int nOld = this.nPlayerConnections();
		int nMax = HubGamePlay.getRule().GetMaxNumberOfPlayers();
		this.removePlayerConnection(playerID);
		if (this.nPlayerConnections() == nMax - 1) {
			try {
				this.restartServer();
			} catch (IOException e) {
			}
		} else if (this.nPlayerConnections() == 0) {
			this.shutDownHub();
		}
	}

	protected void messageReceived(int ClientID, Object message) {

		if (message instanceof Action) {
			Action act = (Action) message;
			switch (act.getAction()) {
			case GameState:
				sendToAll(HubPokerTable);
				break;
			case TableState:
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case Sit:
				resetOutput();
				HubPokerTable.AddPlayerToTable(act.getPlayer());
				sendToAll(HubPokerTable);
				break;
			case Leave:
				resetOutput();
				HubPokerTable.RemovePlayerFromTable(act.getPlayer());
				sendToAll(HubPokerTable);
				break;
			case StartGame:
				// System.out.println("Starting Game!");
				resetOutput();

				// Determine which game is selected (from RootTableController)
				// 1 line of code

				// Get the Rule based on the game selected
				// 1 line of code

				Rule rle = new Rule(act.geteGame());

				// The table should eventually allow multiple instances of
				// 'GamePlay'...
				// Each game played is an instance of 'GamePlay'...
				// For the first instance of GamePlay, pick a random player to
				// be the
				// 'Dealer'...
				// < 5 lines of code to pick random player

				Player PlayerDealer = null;
				if (HubGamePlay == null) {					
					PlayerDealer = HubPokerTable.PickRandomPlayerAtTable();
				} else {
					PlayerDealer = HubGamePlay.nextDealer();
				}

				// Start a new instance of GamePlay, based on rule set and
				// Dealer (Player.PlayerID)
				// 1 line of code

				HubGamePlay = new GamePlay(rle, PlayerDealer.getPlayerID());

				// There are 1+ players seated at the table... add these players
				// to the game
				// < 5 lines of code
				HubGamePlay.setGamePlayers(HubPokerTable.getHashPlayers());
				HubGamePlay.initializeHands();

				// GamePlay has a deck... create the deck based on the game's
				// rules (the rule
				// will have number of jokers... wild cards...
				// 1 line of code
				HubGamePlay.setGameDeck(new Deck(rle.GetNumberOfJokers(), rle.GetWildCards()));

				// Determine the order of players and add each player in turn to
				// GamePlay.lnkPlayerOrder
				// Example... four players playing... seated in Position 1, 2,
				// 3, 4
				// Dealer = Position 2
				// Order should be 3, 4, 1, 2
				// Example... three players playing... seated in Position 1, 2,
				// 4
				// Dealer = Position 4
				// Order should be 1, 2, 4
				// < 10 lines of code

				int[] iPlayerOrder = GamePlay.GetOrder(PlayerDealer.getiPlayerPosition());

				HubGamePlay.setiActOrder(iPlayerOrder);

				// Set PlayerID_NextToAct in GamePlay (next player after Dealer)
				// 1 line of code

				for (int iPlayer : iPlayerOrder) {
					if (HubGamePlay.getPlayerByPosition(iPlayer) != null) {
						HubGamePlay.setPlayerNextToAct(HubGamePlay.getPlayerByPosition(iPlayer));
						break;
					}
				}

				// Deal out the first round
				this.currentDraw = eDrawCount.FIRST;
				System.out.println(this.currentDraw + " draw");
				DealCards();

				// Send the state of the game back to the players

				sendToAll(HubGamePlay);
				break;
			case Deal:
				eDrawCount nextDraw = this.currentDraw.next();
				if (nextDraw != null && HubGamePlay.getRule().hasDrawCount(nextDraw)) {
					this.currentDraw = nextDraw;
					System.out.println(this.currentDraw + " draw");
					DealCards();
				} else {
					this.ScoreHands();
				}
				sendToAll(HubGamePlay);
				break;
			}
		}

		if (HubGamePlay != null) {
			System.out.println(HubGamePlay);
		}
		
		// System.out.println("Message Received by Hub");
	}

	private void ScoreHands() {
		try {
			HubGamePlay.findWinner();
			System.out.println(HubGamePlay.scoreReport());
		} catch (HandException e) {
			e.printStackTrace();
		}
	}
	
	private void DealCards() {
		CardDraw cd = HubGamePlay.getRule().getCardDraw(this.currentDraw);
		Deck dk = HubGamePlay.getGameDeck();
		
		System.out.println(cd);

		// How many cards to draw?
		int nCards = cd.getCardCount().ordinal();
		for (int iDrawCnt = 0; iDrawCnt <= nCards ; iDrawCnt++) {
			// What's the order of the draw?
			for (int iDrawOrder : HubGamePlay.getiActOrder()) {
				// Is there a player seated at that position?
				Player aPlayer = HubGamePlay.getPlayerByPosition(iDrawOrder);
				if (aPlayer == null) {
					continue;
				// Is the hand folded?
				} else if (HubGamePlay.playerGPPH(aPlayer).isFolded()) {
					continue;
				} else {
					try {
						eCardVisibility visibility = cd.getCardVisibility();
						HubGamePlay.playerGPPH(aPlayer).addCardToHand(dk.Draw(), visibility);
					} catch (DeckException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}