package pokerEnums;

public enum BettingOption {

	PotLimit("Pot Limit"), NoLimit("No Limit");
	
	private String betStr;
	
	private BettingOption(String str) {
		this.betStr = str;
	}
	
	public String valueString() {
		switch (this) {
		case PotLimit:
			return "PotLimit";
		case NoLimit:
			return "NoLimit";
		default:
			return "";
		}
	}
	
	public boolean isDefault() {
		return this == NoLimit;
	}
	
	@Override
	public String toString() {
		return this.betStr;
	}
}
