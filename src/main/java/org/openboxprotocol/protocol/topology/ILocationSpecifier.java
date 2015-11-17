package org.openboxprotocol.protocol.topology;


public interface ILocationSpecifier {
	
	public interface Builder {
		public ILocationSpecifier build();
	}

	boolean IsSingleLocation();
}
