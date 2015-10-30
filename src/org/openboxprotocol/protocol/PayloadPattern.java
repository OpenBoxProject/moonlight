package org.openboxprotocol.protocol;

public interface PayloadPattern {

	public String getPattern();
	
	public int getFirstStartIndex();
	
	public int getLastStartIndex();
	
	public interface Builder {
		public PayloadPattern build();
	}
	
}
