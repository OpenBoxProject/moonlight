package org.openboxprotocol.protocol;

import java.util.HashSet;
import java.util.Set;

import org.openboxprotocol.types.ValueType;

public class Prerequisite<T extends ValueType<T>> {
    private final HeaderField<T> field;
    private final Set<ValueType<T>> values;
    private boolean any;

    @SafeVarargs
	public Prerequisite(HeaderField<T> field, ValueType<T>... values) {
        this.values = new HashSet<ValueType<T>>();
        this.field = field;
        if (values == null || values.length == 0) {
            this.any = true;
        } else {
            this.any = false;
            for (ValueType<T> value : values) {
                this.values.add(value);
            }
        }
    }

    /**
     * Returns true if this prerequisite is satisfied by the given match object.
     *
     * @param match Match object
     * @return true iff prerequisite is satisfied.
     */
    public boolean isSatisfied(HeaderMatch match) {
        ValueType<T> res = match.get(this.field);
        if (res == null)
            return false;
        if (this.any)
            return true;
        if (this.values.contains(res)) {
            return true;
        }
        return false;
    }
}
