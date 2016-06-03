package pokerEnums;

public enum JokerOption {
	
	None      ("None"), 
	OneJoker  ("One Joker"), 
	TwoJokers ("Two Jokers");
			
	private String jokerStr;
	
	private JokerOption(String str) {
		this.jokerStr = str;
	}
	
	public String valueString() {
		switch(this) {
		case None:
			return "None";
		case OneJoker:
			return "OneJoker";
		case TwoJokers:
			return "TwoJokers";
		default:
			return "";
		}
	}
	
	public boolean isDefault() {
		return this == None;
	}
			
	@Override
	public String toString() {
		return this.jokerStr;
	}
}
