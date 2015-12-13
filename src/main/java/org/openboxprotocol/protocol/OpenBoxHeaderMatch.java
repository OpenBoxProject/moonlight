package org.openboxprotocol.protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.moonlightcontroller.exceptions.MergeException;
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
	
	@Override
	public HeaderMatch mergeWith(HeaderMatch other) throws MergeException {
		// TODO: Extend this
		// Currently, this only supports merge of rules that either don't share fields or they
		// have exactly the same values for these fields
		
		Set<HeaderField<?>> m1fields = new HashSet<>();
		this.getMatchFields().forEach(f -> m1fields.add(f));
		
		for (HeaderField<?> f : other.getMatchFields()) {
			if (m1fields.contains(f)) {
				// Field has a value in both matches
				ValueType<?> v1 = this.get(f);
				ValueType<?> v2 = other.get(f);
				if (!v1.equals(v2)) {
					throw new MergeException(String.format("Rules conflict on field %s (value1=%s, value2=%s)", f.toString(), v1.toString(), v2.toString()));
				}
			}
		}
		
		OpenBoxHeaderMatch.Builder matchBuilder = new OpenBoxHeaderMatch.Builder();
		
		HeaderMatch[] todo = { this, other };
		
		for (HeaderMatch current : todo) {
			for (HeaderField<?> f : current.getMatchFields()) {
				ValueType<?> v = current.get(f);
				if (v instanceof Masked<?>) {
					matchBuilder.setMaskedUnsafe(f, (Masked<?>)v);
				} else {
					matchBuilder.setExactUnsafe(f, v);
				}
			}
		}

		return matchBuilder.build();
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
		
        Builder setExactUnsafe(HeaderField<?> field, ValueType<?> value) 
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
		
        Builder setMaskedUnsafe(HeaderField<?> field, Masked<?> valueWithMask) 
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
