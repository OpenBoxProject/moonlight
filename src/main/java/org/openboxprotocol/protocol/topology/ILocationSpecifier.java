package org.openboxprotocol.protocol.topology;


public interface ILocationSpecifier {
	
	public interface Builder {
		public ILocationSpecifier build();
	}

	boolean isSingleLocation();
	
	String getId();
	boolean isMatch(String m);
	
	ILocationSpecifier findChild(String m);
	
}
