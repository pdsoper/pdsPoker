package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import exceptions.HandException;

public class GamePlay implements Serializable   {

	private UUID GameID;
	//private UUID PlayerID_NextToAct = null;
	
	@XmlElement
	private HashMap<UUID, Player> hmGamePlayers = new HashMap<UUID, Player>();
	
	@XmlElement
	private ArrayList<GamePlayPlayerHand> GamePlayerHand = new ArrayList<GamePlayPlayerHand>();
	
	@XmlElement
	private ArrayList<GamePlayPlayerHand> GameCommonHand = new ArrayList<GamePlayPlayerHand>();
	
	private Rule rle;
	
	private Deck GameDeck = null;
	
	@XmlElement
	private UUID GameDealer = null;
	
	private int[] iActOrder = null;
	private Player PlayerNextToAct = null;
	
	
	public GamePlay(Rule rle, UUID GameDealerID)
	{
		this.GameID = UUID.randomUUID();
		this.GameDealer = GameDealerID;
		this.rle = rle;
	}
	
	public int dealerPosition() {
		return hmGamePlayers.get(GameDealer).getiPlayerPosition();
	}
	
	public void initializeHands() {
		this.GamePlayerHand.clear();
		for (Player p : this.hmGamePlayers.values()) {
			GamePlayPlayerHand gpph = new GamePlayPlayerHand(this, p);
			this.GamePlayerHand.add(gpph);
		}
		this.GameCommonHand.clear();
	}
	
	public UUID getGameID() {
		return GameID;
	}

	public void setGameID(UUID gameID) {
		GameID = gameID;
	}

	public Rule getRule()
	{
		return this.rle;
	}
	
	public HashMap<UUID, Player> getGamePlayers() {
		return hmGamePlayers;
	}

	public void setGamePlayers(HashMap<UUID, Player> gamePlayers) {
		this.hmGamePlayers = new HashMap<UUID, Player>(gamePlayers);
	}
	
	public void addPlayerToGame(Player p)
	{
		this.hmGamePlayers.put(p.getPlayerID(),p);
	}
	
	public Player getGamePlayer(UUID PlayerID)
	{
		return (Player) this.hmGamePlayers.get(PlayerID);
	}

	public Deck getGameDeck() {
		return GameDeck;
	}

	public void setGameDeck(Deck gameDeck) {
		GameDeck = gameDeck;
	}
	
	public UUID getGameDealer() {
		return GameDealer;
	}

	private void setGameDealer(UUID gameDealerID) {
		GameDealer = gameDealerID;
	}

	public void addGamePlayPlayerHand(GamePlayPlayerHand GPPH)
	{
		GamePlayerHand.add(GPPH);
	}
	
	public void addGamePlayCommonHand(GamePlayPlayerHand GPCH)
	{
		GameCommonHand.add(GPCH);
	}

	public int[] getiActOrder() {
		return iActOrder;
	}

	public void setiActOrder(int[] iActOrder) {
		this.iActOrder = iActOrder;
	}
	
	public Player getPlayerNextToAct() {
		return PlayerNextToAct;
	}

	public void setPlayerNextToAct(Player playerNextToAct) {
		PlayerNextToAct = playerNextToAct;
	}

	public static int[] GetOrder(int iStartPosition) {
		int[] iPos = null;
		switch (iStartPosition) {
		case 1:
			int[] iPositions1 = new int[] { 2, 3, 4, 1 };
			iPos = iPositions1;
			break;
		case 2:
			int[] iPositions2 = new int[] { 3, 4, 1, 2 };
			iPos = iPositions2;
			break;
		case 3:
			int[] iPositions3 = new int[] { 4, 1, 2, 3 };
			iPos = iPositions3;
			break;
		case 4:
			int[] iPositions4 = new int[] { 1, 2, 3, 4 };
			iPos = iPositions4;
			break;
		}
		return iPos;
	}

	public static int NextPosition(int iCurrentPosition, int[] iOrder) {
		int iNextPosition = -1;
		try {
			for (int i : iOrder) {
				if (iCurrentPosition == i) {
					iNextPosition = iOrder[i + 1];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Whoops! Asking for something beyond the size of the array
			iNextPosition = iOrder[0];
		}

		return iNextPosition;
	}
	
	public Player getPlayerByPosition(int iPlayerPosition)
	{
		Player pl = null;
		
		Iterator it = getGamePlayers().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player)pair.getValue();
			if (p.getiPlayerPosition() == iPlayerPosition)
				pl = p;
		}
		
		return pl;
	}
	/*
	public GamePlayPlayerHand FindCommonHand(GamePlay gme)
	{
		GamePlayPlayerHand GPCH = null;
		for (GamePlayPlayerHand GPPH: GameCommonHand)
		{
			if (GPPH.getGame().getGameID() == gme.getGameID())
			{
				GPCH = GPPH;
			}
		}		
		return GPCH;
	}
	*/
	
	public Player findWinner() throws HandException {
		boolean firstTime = true;
		GamePlayPlayerHand winner = null;
		for (GamePlayPlayerHand gpph : this.GamePlayerHand) {
			gpph.setHand(Hand.Evaluate(gpph.getHand()));
			if (firstTime) {
				winner = gpph;
				firstTime = false;
			} else if (Hand.HandRank.compare(winner.getHand(), gpph.getHand()) > 0) {
				winner = gpph;
			}
		}
		if (winner != null) {
			for (GamePlayPlayerHand gpph : this.GamePlayerHand) {
				gpph.setWinningPlayer(winner.getPlayer());
			}
		}
		return winner.getPlayer();
	}
	
	public GamePlayPlayerHand playerGPPH(Player aPlayer) {
		GamePlayPlayerHand GPPHReturn = null;
		for (GamePlayPlayerHand GPPH : GamePlayerHand) {
			if (aPlayer.equals(GPPH.getPlayer())) {
				GPPHReturn = GPPH;
				break;
			}
		}
		return GPPHReturn;
	}
	
	public String toString() {
		String ans = "\n";
		for (GamePlayPlayerHand gpph : GamePlayerHand) {
			ans += gpph.toString() + "\n";
		}
		return ans;
	}
	
	public Player winner() {
		if (this.GamePlayerHand.size() > 0) {
			return this.GamePlayerHand.get(0).getWinningPlayer();
		} else {
			return null;
		}
	}
	
	public int nPlayers() {
		return this.hmGamePlayers.size();
	}
	
	public Player nextDealer() {
		if (this.GameDealer == null || this.rle == null) return null;
		Player currentDealer = this.hmGamePlayers.get(this.GameDealer);
		int currentDealerPos = currentDealer.getiPlayerPosition();
		int maxPlayers = this.rle.GetMaxNumberOfPlayers();
		Player nextDealer = null;
		for (int i = 1 ; i < maxPlayers; i++) {
			int nextDealerPos = currentDealerPos + i;
			if (nextDealerPos > maxPlayers) {
				nextDealerPos = nextDealerPos - maxPlayers;
			}
			nextDealer = this.getPlayerByPosition(nextDealerPos);
			if (nextDealer != null) {
				break;
			}
		}
		return nextDealer;
	}
	
	public String scoreReport() {
		Player winner = this.winner();
		if (winner == null) {
			return "";
		}
		String ans = "";
		GamePlayPlayerHand gpphw = null;
		ans += winner.getPlayerName() + " wins!\n";
		gpphw = this.playerGPPH(winner);
		ans += gpphw.getHand().getHandScore().toString();
		for (GamePlayPlayerHand gpph : this.GamePlayerHand) {
			Player aPlayer = gpph.getPlayer();
			if (aPlayer.equals(winner)) {
				continue;
			}
			ans += "\n" + aPlayer.getPlayerName() + " : ";
			if (gpph.isFolded()) {
				ans+= "folded";
			} else {
				ans += gpph.getHand().getHandScore();
			}
		}
		return ans;
	}
	
}
