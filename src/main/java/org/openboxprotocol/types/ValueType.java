package org.openboxprotocol.types;

import com.fasterxml.jackson.databind.JsonSerializable;

public interface ValueType<T extends ValueType<T>> extends JsonSerializable {
	public T applyMask(T mask);
}
