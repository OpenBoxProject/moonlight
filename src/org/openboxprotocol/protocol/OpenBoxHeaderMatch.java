package org.openboxprotocol.protocol;

import java.util.HashMap;
import java.util.Map;

import org.openboxprotocol.types.Masked;
import org.openboxprotocol.types.ValueType;

public class OpenBoxHeaderMatch implements HeaderMatch {

	private Map<HeaderField<?>, ValueType<?>> fields;
	
	private OpenBoxHeaderMatch() {
		this.fields = new HashMap<>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <F extends ValueType<F>> F get(HeaderField<F> field)
			throws UnsupportedOperationException {
		if (!field.arePrerequisitesOK(this))
			return null;
		return (F)fields.get(field);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <F extends ValueType<F>> Masked<F> getMasked(HeaderField<F> field)
			throws UnsupportedOperationException {
		if (!field.arePrerequisitesOK(this))
			return null;
		
		F value = (F)fields.get(field);
		if (value instanceof Masked<?>){
			return (Masked<F>)value;	
		}

		return Masked.of(value, field.noMask);
	}

	@Override
	public Iterable<HeaderField<?>> getMatchFields() {
		return fields.keySet();
	}

	@Override
	public Builder createBuilder() {
		return new Builder(this);
	}
	
	@Override
	public OpenBoxHeaderMatch clone() {
		OpenBoxHeaderMatch other = new OpenBoxHeaderMatch();
		other.fields.putAll(this.fields);
		return other;
	}
	
	public static class Builder implements HeaderMatch.Builder {

		private OpenBoxHeaderMatch match;
		
		public Builder() {
			match = new OpenBoxHeaderMatch();
		}
		
		public Builder(OpenBoxHeaderMatch match) {
			this.match = match.clone();
		}
		
		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder setExact(
				HeaderField<F> field, F value)
				throws UnsupportedOperationException {
			match.fields.put(field, value);
			return this;
		}

		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder setMasked(
				HeaderField<F> field, F value, F mask)
				throws UnsupportedOperationException {
			match.fields.put(field, Masked.of(value, mask));
			return this;
		}

		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder setMasked(
				HeaderField<F> field, Masked<F> valueWithMask)
				throws UnsupportedOperationException {
			match.fields.put(field, valueWithMask);
			return this;
		}

		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder wildcard(
				HeaderField<F> field) throws UnsupportedOperationException {
			match.fields.remove(field);
			return this;
		}

		@Override
		public HeaderMatch build() {
			return match;
		}
		
	}

}
