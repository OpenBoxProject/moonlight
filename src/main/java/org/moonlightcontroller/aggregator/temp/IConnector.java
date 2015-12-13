package org.moonlightcontroller.aggregator.temp;

public interface IConnector {
	public IProcessingBlock getSourceBlock();
	public IProcessingBlock getDestBlock();
	public int getSourceOutputPort();
	
	public interface Builder {
		public Builder setSourceBlock(IProcessingBlock source);
		public Builder setSourceOutputPort(int port);
		public Builder setDestBlock(IProcessingBlock dest);
		public IConnector build();
	}
}
