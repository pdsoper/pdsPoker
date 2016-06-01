package pokerEnums;

public enum JokerOption {
	
	None       ("None"), 
	One_Joker  ("One Joker"), 
	Two_Jokers ("Two Jokers");
			
	private String jokerStr;
	
	private JokerOption(String str) {
		this.jokerStr = str;
	}
			
	@Override
	public String toString() {
		return this.jokerStr;
	}
}
