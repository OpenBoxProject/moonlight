package org.openboxprotocol.types;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class AbstractValueType<T extends ValueType<T>> implements ValueType<T> {
	@Override
	public void serializeWithType(JsonGenerator arg0, SerializerProvider arg1,
			TypeSerializer arg2) throws IOException {
		arg2.writeTypePrefixForScalar(this, arg0, this.getClass());
		this.serialize(arg0, arg1);
		arg2.writeTypeSuffixForScalar(this, arg0);
	}
}
