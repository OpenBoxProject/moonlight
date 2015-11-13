package org.openboxprotocol.types;

public class Masked<F extends ValueType<F>> implements ValueType<Masked<F>> {

	private F value;
	private F mask;
	
	private Masked(F value, F mask) {
		this.value = value.applyMask(mask);
		this.mask = mask;
	}
	
	public F getValue() {
		return this.value;
	}
	
	public F getMask() {
		return this.mask;
	}
	
	@Override
	public int hashCode() {
		return (this.value.hashCode() * 3 + this.mask.hashCode() * 71);
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof Masked) && ((Masked<?>)other).value.equals(this.value) && ((Masked<?>)other).mask.equals(this.mask);
	}
	
	public static <F extends ValueType<F>> Masked<F> of(F value, F mask) {
		return new Masked<F>(value, mask);
	}

	@Override
	public Masked<F> applyMask(Masked<F> mask) {
		return this;
	}
	
}
