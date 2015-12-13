package org.openboxprotocol.protocol;


public interface IConnector {
	public IProcessingBlock getSourceBlock();
	public IProcessingBlock getDestBlock();
	public int getSourceOutputPort();

	// Legacy
	String getSourceBlockId();
	String getDestinatinBlockId();
	Builder createBuilder();
	
	public interface Builder {
		
		Builder setSourceBlockId(String src);
		
		Builder setSourceBlockPort(int port);
	
		public Builder setSourceBlock(IProcessingBlock source);
		public Builder setSourceOutputPort(int port);
		public Builder setDestBlock(IProcessingBlock dest);
		public IConnector build();
	}
}
