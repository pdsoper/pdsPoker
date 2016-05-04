package pokerBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import pokerEnums.eCardCount;
import pokerEnums.eCardDestination;
import pokerEnums.eCardVisibility;
import pokerEnums.eDrawCount;
import pokerEnums.eGame;
import pokerEnums.eRank;
import pokerEnums.eSuit;

public class Rule implements Serializable {

		private int MaxNumberOfPlayers;
		private int PlayerNumberOfCards;
		private int NumberOfJokers;
		private int PlayerCardsMin;
		private int PlayerCardsMax;
		private int CommunityCardsMin;
		private int CommunityCardsMax;
		private int PossibleHandCombinations;
		private ArrayList<Card> WildCards = new ArrayList<Card>();
		private HashMap hmCardDraw = new HashMap<Integer, CardDraw>();
		
		private LinkedList<CardDraw> CardDraw = new LinkedList<CardDraw>();
		private eGame Game;

		public Rule(eGame gme) {
			this.Game = gme;
			switch (gme) {
			case FiveStud: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 5;
				this.NumberOfJokers = 0;
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;	
				this.PossibleHandCombinations = 1;
				this.hmCardDraw.put(eDrawCount.FIRST, new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND, new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD, new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH, new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				
				break;
			}
			case FiveStudOneJoker: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 5;
				this.NumberOfJokers = 21;
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;			
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;
				this.PossibleHandCombinations = 1;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
		
				break;
			}
			case FiveStudTwoJoker: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 5;
				this.NumberOfJokers = 2;
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;			
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;
				this.PossibleHandCombinations = 1;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
			
				break;
			}
			case TexasHoldEm: {
				this.MaxNumberOfPlayers = 8;
				this.PlayerNumberOfCards = 2;
				this.NumberOfJokers = 0;
				this.PlayerCardsMin = 0;
				this.PlayerCardsMax = 2;			
				this.CommunityCardsMin = 3;
				this.CommunityCardsMax = 5;
				this.PossibleHandCombinations = 21;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.Three,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				break;
			}
			case Omaha: {
				this.MaxNumberOfPlayers = 6;
				this.PlayerNumberOfCards = 4;
				this.NumberOfJokers = 0;
				this.PlayerCardsMin = 2;
				this.PlayerCardsMax = 2;			
				this.CommunityCardsMin = 3;
				this.CommunityCardsMax = 5;
				this.PossibleHandCombinations = 60;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.Three,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.FIFTH,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));				
				break;
			}
			case SuperOmaha: {
				this.MaxNumberOfPlayers = 6;
				this.PlayerNumberOfCards = 4;
				this.NumberOfJokers = 0;
				this.PlayerCardsMin = 0;
				this.PlayerCardsMax = 2;			
				this.CommunityCardsMin = 3;
				this.CommunityCardsMax = 5;
				this.PossibleHandCombinations = 81;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.Three,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.FIFTH,new CardDraw(eCardCount.One,eCardDestination.Community,eCardVisibility.VisibleEveryone));
			
				break;
			}		
			case SevenDraw: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 7;
				this.NumberOfJokers = 0;
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;			
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;
				this.PossibleHandCombinations = 21;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FIFTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.SIXTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));				
				
				
				break;
			}		
			case DeucesWild: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 5;
				this.NumberOfJokers = 0;
				this.WildCards.add(new Card(eSuit.DIAMONDS, eRank.TWO, 40));
				this.WildCards.add(new Card(eSuit.HEARTS, eRank.TWO, 1));
				this.WildCards.add(new Card(eSuit.SPADES, eRank.TWO, 14));
				this.WildCards.add(new Card(eSuit.CLUBS, eRank.TWO, 27));
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;			
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;
				this.PossibleHandCombinations = 1;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
			
				break;
			}
			case AcesAndEights: {
				this.MaxNumberOfPlayers = 4;
				this.PlayerNumberOfCards = 2;
				this.NumberOfJokers = 0;
				this.WildCards.add(new Card(eSuit.DIAMONDS, eRank.ACE, 52));
				this.WildCards.add(new Card(eSuit.HEARTS, eRank.ACE, 13));
				this.WildCards.add(new Card(eSuit.SPADES, eRank.ACE, 26));
				this.WildCards.add(new Card(eSuit.CLUBS, eRank.ACE, 39));
				this.WildCards.add(new Card(eSuit.DIAMONDS, eRank.EIGHT, 46));
				this.WildCards.add(new Card(eSuit.HEARTS, eRank.EIGHT, 7));
				this.WildCards.add(new Card(eSuit.SPADES, eRank.EIGHT, 20));
				this.WildCards.add(new Card(eSuit.CLUBS, eRank.EIGHT, 33));
				this.PlayerCardsMin = 5;
				this.PlayerCardsMax = 5;			
				this.CommunityCardsMin = 0;
				this.CommunityCardsMax = 0;
				this.PossibleHandCombinations = 1;
				this.hmCardDraw.put(eDrawCount.FIRST,new CardDraw(eCardCount.Two,eCardDestination.Player,eCardVisibility.VisibleEveryone));
				this.hmCardDraw.put(eDrawCount.SECOND,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.THIRD,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
				this.hmCardDraw.put(eDrawCount.FOURTH,new CardDraw(eCardCount.One,eCardDestination.Player,eCardVisibility.VisibleMe));
						
				break;
			}
			}
		}

		public LinkedList<CardDraw> getCardDraw() {
			return CardDraw;
		}

		// Added
		public CardDraw getCardDraw(int drawNum) {
			return this.CardDraw.get(drawNum);
		}

		public int GetMaxNumberOfPlayers() {
			return this.MaxNumberOfPlayers;
		}

		public int GetPlayerNumberOfCards() {
			return this.PlayerNumberOfCards;
		}

		public int GetNumberOfJokers() {
			return this.NumberOfJokers;
		}
		
		public int GetCommunityCardsCount()
		{
			return this.CommunityCardsMax;
		}
		
		public ArrayList<Card> GetWildCards()
		{
			return this.WildCards;
		}
		public eGame GetGame()
		{
			return this.Game;
		}

		public int getCommunityCardsMin() {
			return CommunityCardsMin;
		}

		public void setCommunityCardsMin(int communityCardsMin) {
			CommunityCardsMin = communityCardsMin;
		}

		public int getCommunityCardsMax() {
			return CommunityCardsMax;
		}

		public void setCommunityCardsMax(int communityCardsMax) {
			CommunityCardsMax = communityCardsMax;
		}

		public int getPlayerCardsMin() {
			return PlayerCardsMin;
		}

		public void setPlayerCardsMin(int playerCardsMin) {
			PlayerCardsMin = playerCardsMin;
		}

		public int getPlayerCardsMax() {
			return PlayerCardsMax;
		}
		
		public int getTotalCardsToDraw()
		{
			return this.GetPlayerNumberOfCards() + this.getCommunityCardsMax();
		}

		public void setPlayerCardsMax(int playerCardsMax) {
			PlayerCardsMax = playerCardsMax;
		}

		public int getPossibleHandCombinations() {
			return PossibleHandCombinations;
		}

		public void setPossibleHandCombinations(int possibleHandCombinations) {
			PossibleHandCombinations = possibleHandCombinations;
		}		
}
