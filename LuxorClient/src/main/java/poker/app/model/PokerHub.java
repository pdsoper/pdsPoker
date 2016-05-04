package poker.app.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.DeckException;
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
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eGameState;

public class PokerHub extends Hub {

	private Table HubPokerTable = new Table();
	private GamePlay HubGamePlay;
	private int iDealNbr = 0;
	// private PokerGameState state;
	private eGameState eGameState;

	public PokerHub(int port) throws IOException {
		super(port);
	}

	protected void playerConnected(int playerID) {

		if (playerID > HubGamePlay.getRule().GetMaxNumberOfPlayers()) {
			shutdownServerSocket();
		}
	}

	protected void playerDisconnected(int playerID) {
		shutDownHub();
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

				// TODO - Lab #5 Do all the things you need to do to start a
				// game!!

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

				Player PlayerRandom = HubPokerTable.PickRandomPlayerAtTable();

				// Start a new instance of GamePlay, based on rule set and
				// Dealer (Player.PlayerID)
				// 1 line of code

				HubGamePlay = new GamePlay(rle, PlayerRandom.getPlayerID());

				// There are 1+ players seated at the table... add these players
				// to the game
				// < 5 lines of code
				HubGamePlay.setGamePlayers(HubPokerTable.getHashPlayers());

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

				int[] iPlayerOrder = GamePlay.GetOrder(PlayerRandom.getiPlayerPosition());

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
				DealCards(eDrawCount.FIRST);

				// Send the state of the game back to the players

				sendToAll(HubGamePlay);
				break;
			case Deal:

				/*
				 * int iCardstoDraw[] = HubGamePlay.getRule().getiCardsToDraw();
				 * int iDrawCount = iCardstoDraw[iDealNbr];
				 * 
				 * for (int i = 0; i<iDrawCount; i++) { try { Card c =
				 * HubGamePlay.getGameDeck().Draw(); } catch (DeckException e) {
				 * e.printStackTrace(); } }
				 */
				break;
			case Draw:
				break;
			}
		}

		// System.out.println("Message Received by Hub");
	}

	private void DealCards(eDrawCount eDrawCount) {
		CardDraw cd = HubGamePlay.getRule().GetCardDraw(eDrawCount);

		// How many cards to draw?
		for (int iDrawCnt = 0; iDrawCnt < cd.getCardCount().ordinal(); iDrawCnt++) {
			// What's the order of the draw?
			for (int iDrawOrder : HubGamePlay.getiActOrder()) {
				// Is there a player seated at that position? Is the hand
				// folded?
				if ((HubGamePlay.getPlayerByPosition(iDrawOrder) != null)
						&& (!HubGamePlay.getPlayerHand(HubGamePlay.getPlayerByPosition(iDrawOrder)).isFolded())) {
					try {
						HubGamePlay.getPlayerHand(HubGamePlay.getPlayerByPosition(iDrawOrder))
								.Draw(HubGamePlay.getGameDeck());
					} catch (DeckException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}