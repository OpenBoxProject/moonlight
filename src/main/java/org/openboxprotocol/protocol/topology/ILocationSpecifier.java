package org.openboxprotocol.protocol.topology;


public interface ILocationSpecifier {
	
	public interface Builder {
		public ILocationSpecifier build();
	}

	boolean isSingleLocation();
	
	long getId();
	boolean isMatch(long m);
	
	ILocationSpecifier findChild(long m);
}
