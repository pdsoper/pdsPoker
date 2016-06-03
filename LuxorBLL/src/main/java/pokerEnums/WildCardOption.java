package pokerEnums;

import java.util.ArrayList;

import pokerBase.WildCard;

public enum WildCardOption {
	
	Deuces        ("Deuces"),
	OneEyedJacks  ("One-eyed Jacks"),
	AcesAndEights ("Aces and Eights");
	
	private String optionStr;
	
	private WildCardOption(String optionStr) {
		this.optionStr = optionStr;
	}
	
	public String valueString() {
		switch (this) {
		case Deuces:
			return "Deuces";
		case OneEyedJacks:
			return "OneEyedJacks";
		case AcesAndEights:
			return "AcesAndEights";
		default:
			return "";
		}
	}
	
	public ArrayList<WildCard> getWildCards() {
		switch (this) {
		case Deuces:
			return WildCard.Deuces();
		case OneEyedJacks:
			return WildCard.OneEyedJacks();
		case AcesAndEights:
			return WildCard.AcesAndEights();
		default:
			return new ArrayList<WildCard>();
		}
	}
	
	public boolean isDefault() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.optionStr;
	}
	
}
