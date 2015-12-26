package org.openboxprotocol.protocol;

public enum Priority implements Comparable<Priority> {
	DEFAULT ("DEAFAULT"),
	VERY_LOW ("VERY_LOW"),
	LOW ("LOW"),
	MEDIUM ("MEDIUM"),
	HIGH ("HIGH"),
	VERY_HIGH ("VERY_HIGH"),
	CRITICAL ("CRITICAL");
	
	public static final int MAX_ORDINAL = 6;
	
	public static Priority of(int ordinal) {
		switch (ordinal) {
		case 0:
			return DEFAULT;
		case 1:
			return VERY_LOW;
		case 2:
			return LOW;
		case 3:
			return MEDIUM;
		case 4:
			return HIGH;
		case 5:
			return VERY_HIGH;
		case 6:
			return CRITICAL;
		default:
			throw new IndexOutOfBoundsException("Invalid priority ordinal value: " + ordinal);
		}
	}
	
	private final String name;       

    private Priority(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
       return this.name;
    }
}