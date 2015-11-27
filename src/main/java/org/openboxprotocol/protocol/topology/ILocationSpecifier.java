package org.openboxprotocol.protocol.topology;


public interface ILocationSpecifier {
	
	public interface Builder {
		public ILocationSpecifier build();
	}

	boolean isSingleLocation();
	
	boolean isMatch(String m);
	
	ILocationSpecifier findChild(String m);
	
	String getId();
}
