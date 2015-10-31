package org.openboxprotocol.protocol;

import org.openboxprotocol.types.Masked;
import org.openboxprotocol.types.ValueType;

public interface HeaderMatch {
	
    public <F extends ValueType<F>> F get(HeaderField<F> field) throws UnsupportedOperationException;

    public <F extends ValueType<F>> Masked<F> getMasked(HeaderField<F> field) throws UnsupportedOperationException;

    public Iterable<HeaderField<?>> getMatchFields();

    public Builder createBuilder();

    interface Builder {
        public <F extends ValueType<F>> Builder setExact(HeaderField<F> field, F value) throws UnsupportedOperationException;

        public <F extends ValueType<F>> Builder setMasked(HeaderField<F> field, F value, F mask) throws UnsupportedOperationException;

        public <F extends ValueType<F>> Builder setMasked(HeaderField<F> field, Masked<F> valueWithMask) throws UnsupportedOperationException;

        public <F extends ValueType<F>> Builder wildcard(HeaderField<F> field) throws UnsupportedOperationException;

        public HeaderMatch build();
    }
}