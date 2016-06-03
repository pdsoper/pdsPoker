package poker.app.model;

import java.io.IOException;
import netgame.common.Hub;
import pokerBase.Action;
import pokerBase.Deck;
import pokerBase.Game;
import pokerBase.Table;
import pokerExceptions.DeckException;

public class PokerHub extends Hub {

	// Set by the design of the view
	public static final int MaxPlayers = 4;
	
	private Table pokerTable = new Table(MaxPlayers);

	public PokerHub(int port) throws IOException {
		super(port);
		this.setAutoreset(true);
	}

	// Accept no further connections after the table is full.
	// nPlayerConnections() was added to Hub.java
	@Override
	protected void playerConnected(int playerID) {
		if (this.nPlayerConnections() == MaxPlayers) {
			shutdownServerSocket();
		}
	}

	// Accept new connections if a player leaves a full table
	protected void playerDisconnected(int playerID) {
		int nOld = this.nPlayerConnections();
		this.removePlayerConnection(playerID);
		if (this.nPlayerConnections() == MaxPlayers - 1) {
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
			switch (act.getActionOption()) {
			case TableState:
				resetOutput();
				sendToAll(pokerTable);
				break;
			case Sit:
				resetOutput();
				pokerTable.addPlayer(act.getPlayer(), act.getPosition());
				sendToAll(pokerTable);
				break;
			case Leave:
				resetOutput();
				pokerTable.removePlayer(act.getPosition());
				sendToAll(pokerTable);
				break;
			case StartGame:
				resetOutput();
				if (pokerTable.isGameInProgress()) {
					break;
				}

				// Determine which game is selected (from RootTableController)
				// 1 line of code

				Game aGame = Game.FiveCardStud();

				Deck aDeck = new Deck();
				
				// Deal out the first round
				pokerTable.startNewGame(aGame, aDeck);
				try {
					pokerTable.dealCards();
				} catch (DeckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Send the state of the game back to the players
				sendToAll(pokerTable);
				break;
			case Deal:
				try {
					pokerTable.dealCards();
				} catch (DeckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pokerTable.isGameOver()) {
					System.out.println(pokerTable);
				}
				sendToAll(pokerTable);
				break;
			default:
				sendToAll(pokerTable);
			}
		}

		// System.out.println("Message Received by Hub");
	}

}