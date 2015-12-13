package org.moonlightcontroller.aggregator.temp;

import java.util.Arrays;

public class Tupple<T> {
	private T[] data;
	
	@SafeVarargs
	public Tupple(T... values) {
		this.data = values;
	}
	
	public T get(int index) throws IndexOutOfBoundsException {
		try {
			return data[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(e.getMessage());
		}
	}
	
	public T[] toArray() {
		return Arrays.copyOf(this.data, this.data.length);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < this.data.length; i++) {
			sb.append(this.data[i]);
			if (i < this.data.length - 1)
				sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}
	
	public static class Pair<T> extends Tupple<T> {
		public Pair(T a, T b) {
			super(a, b);
		}
	}
}
