package org.openboxprotocol.types;

public interface ValueType<T extends ValueType<T>> {
	public T applyMask(T mask);
}
