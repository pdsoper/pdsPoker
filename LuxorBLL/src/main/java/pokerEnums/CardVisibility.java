package pokerEnums;

public enum CardVisibility {
	
	PlayerOnly ("player only"), 
	Everyone   ("everyone");
	
	private String visibilityStr;
	
	private CardVisibility(String str) {
		this.visibilityStr = str;
	}
	
	@Override
	public String toString() {
		return "visible to " + this.visibilityStr;
	}
	
}
