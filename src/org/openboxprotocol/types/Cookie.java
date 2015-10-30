package org.openboxprotocol.types;

public class Cookie implements ValueType<Cookie> {

	private long value;

	public static final Cookie EMPTY_COOKIE = new Cookie(0);
	public static final Cookie EMPTY_MASK = EMPTY_COOKIE;

	private Cookie(long value) {
		this.value = value;
	}

	public long getValue() {
		return value;
	}
	
	public static Cookie of(long value) {
		if (value == 0)
			return EMPTY_COOKIE;
		else
			return new Cookie(value);
	}

	@Override
	public Cookie applyMask(Cookie mask) {
		return Cookie.of(this.value & mask.value);
	}
	
	@Override
	public int hashCode() {
		return (int)(this.value % Integer.MAX_VALUE);
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Cookie)
			return ((Cookie)other).value == this.value;
		return false;
	}

}
