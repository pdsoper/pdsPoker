package pokerEnums;

import pokerBase.Game;

public enum GameOption {

	FiveCardDraw ("Five Card Draw"),
	FiveCardStud ("Five Card Stud");
	
	private String gameStr;
	
	private GameOption(String str) {
		this.gameStr = str;
	}
	
	public String valueString() {
		switch (this) {
		case FiveCardDraw:
			return "FiveCardDraw";
		case FiveCardStud:
			return "FiveCardStud";
		default:
			return "";
		}
	}
	
	public boolean isDefault() {
		return this == FiveCardStud;
	}
	
	public Game getGame() {
		switch (this) {
		case FiveCardDraw:
			return Game.FiveCardDraw();
		case FiveCardStud:
			return Game.FiveCardStud();
		default:
			return new Game("Empty Game");
		}
	}
	
	@Override
	public String toString() {
		return this.gameStr;
	}
	
}
