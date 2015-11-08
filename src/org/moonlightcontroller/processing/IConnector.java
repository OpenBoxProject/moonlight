package org.moonlightcontroller.processing;

public interface IConnector {
	
	String getSourceBlockId();
	
	int getSourceBlockPort();
	
	String getDestinatinBlockId();
	
	Builder createBuilder();
	
	public interface Builder {
		
		Builder setSourceBlockId(String src);
		
		Builder setDestinationBlockId(String dst);
		
		Builder setSourceBlockPort(int port);
		
		IConnector build();
	}
}
