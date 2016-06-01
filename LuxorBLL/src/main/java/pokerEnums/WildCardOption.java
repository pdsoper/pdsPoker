package pokerEnums;

public enum WildCardOption {
	
	DeucesWild  ("Deuces Wild"),
	AcesAndEights ("Aces and Eights");
	
	private String optionStr;
	
	private WildCardOption(String optionStr) {
		this.optionStr = optionStr;
	}
	
	@Override
	public String toString() {
		return this.optionStr;
	}
	
}
