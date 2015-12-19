package org.openboxprotocol.protocol;

public enum Priority implements Comparable<Priority> {
	DEFAULT,
	VERY_LOW,
	LOW,
	MEDIUM,
	HIGH,
	VERY_HIGH,
	CRITICAL;
	
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
}