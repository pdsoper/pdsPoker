package pokerEnums;

public enum eDrawCount {

	FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), 
	EIGHTH(8), NINTH(9), TENTH(10);
	
	private static eDrawCount[] eVals = eDrawCount.values();
	
	public boolean hasNext() {
		return this.ordinal() < eVals.length - 1;
	}
	
	public eDrawCount next() {
		if (this.hasNext()) {
			return eVals[(this.ordinal() + 1)];
		} else {
			return null;
		}
	}
	
	/* Cyclic form - always returns an enum
	public eDrawCount next() {
		return eVals[(this.ordinal() + 1) % eVals.length];
	}
	*/
	
	private eDrawCount(final int DrawNo){
		this.DrawNo = DrawNo;
	}

	private int DrawNo;
	
	public int getDrawNo(){
		return DrawNo;
	}
	
}
