package org.openboxprotocol.protocol;

import java.util.HashMap;
import java.util.Map;

import org.openboxprotocol.types.Masked;
import org.openboxprotocol.types.ValueType;

public class OpenBoxHeaderMatch implements HeaderMatch {

	private Map<HeaderField<?>, ValueType<?>> fields;
	private Map<HeaderField<?>, ValueType<?>> masks;
	
	private OpenBoxHeaderMatch() {
		this.fields = new HashMap<>();
		this.masks = new HashMap<>();
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
		F mask = (F)masks.get(field);
		
		if (mask == null)
			return Masked.of(value, field.noMask);
		return Masked.of(value, mask);
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
		other.masks.putAll(this.masks);
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
			match.fields.put(field, value);
			match.masks.put(field, value);
			return this;
		}

		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder setMasked(
				HeaderField<F> field, Masked<F> valueWithMask)
				throws UnsupportedOperationException {
			match.fields.put(field, valueWithMask.getValue());
			match.masks.put(field, valueWithMask.getMask());
			return this;
		}

		@Override
		public <F extends ValueType<F>> org.openboxprotocol.protocol.HeaderMatch.Builder wildcard(
				HeaderField<F> field) throws UnsupportedOperationException {
			match.fields.remove(field);
			match.masks.remove(field);
			return this;
		}

		@Override
		public HeaderMatch build() {
			return match;
		}
		
	}

}
