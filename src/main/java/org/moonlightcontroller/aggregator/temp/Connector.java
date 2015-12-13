package org.moonlightcontroller.aggregator.temp;

public class Connector implements IConnector {

	private IProcessingBlock src, dst;
	private int sourceOutputPort;
	
	public Connector(IProcessingBlock src, int srcOutputPort, IProcessingBlock dst) {
		this.src = src;
		this.sourceOutputPort = srcOutputPort;
		this.dst = dst;
	}
	
	@Override
	public IProcessingBlock getSourceBlock() {
		return src;
	}

	@Override
	public IProcessingBlock getDestBlock() {
		return dst;
	}

	@Override
	public int getSourceOutputPort() {
		return sourceOutputPort;
	}
	
	@Override
	public String toString() {
		return String.format("[Connector: %s[%d]->%s]", this.getSourceBlock(), this.getSourceOutputPort(), this.getDestBlock());
	}

	public static class Builder implements IConnector.Builder {
		private IProcessingBlock src, dst;
		private int sourceOutputPort;

		@Override
		public IConnector.Builder setSourceBlock(IProcessingBlock source) {
			this.src = source;
			return this;
		}

		@Override
		public IConnector.Builder setSourceOutputPort(int port) {
			this.sourceOutputPort = port;
			return this;
		}

		@Override
		public IConnector.Builder setDestBlock(IProcessingBlock dest) {
			this.dst = dest;
			return this;
		}

		@Override
		public IConnector build() {
			return new Connector(src, sourceOutputPort, dst);
		}
		
	}
}
