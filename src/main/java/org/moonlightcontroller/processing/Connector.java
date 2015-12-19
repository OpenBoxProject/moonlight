package org.moonlightcontroller.processing;

public class Connector implements IConnector {

	private IProcessingBlock sourceBlock;
	private int sourcePort;
	private IProcessingBlock destinationBlock;
	
	private Connector() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSourceBlockId() {
		return this.sourceBlock.getId();
	}

	@Override
	public int getSourceOutputPort() {
		return this.sourcePort;
	}

	@Override
	public String getDestinatinBlockId() {;
		return this.destinationBlock.getId();
	}

	@Override
	public IProcessingBlock getSourceBlock() {
		return this.sourceBlock;
	}

	@Override
	public IProcessingBlock getDestBlock() {
		return this.destinationBlock;
	}

	@Override
	public Builder createBuilder() {
		return new Builder(this);
	}
	
	@Override
	public Connector clone() {
		Connector other = new Connector();
		other.sourceBlock = this.sourceBlock;
		other.sourcePort = this.sourcePort;
		other.destinationBlock = this.destinationBlock;
		return other;
	}
	
	public static class Builder implements IConnector.Builder {

		private Connector conn;

		public Builder(Connector conn){
			this.conn = conn.clone();
		}
		
		public Builder(){
			this.conn = new Connector();
		}
		
		@Override
		public IConnector build() {
			return this.conn;
		}

		@Override
		public org.moonlightcontroller.processing.IConnector.Builder setSourceBlock(
				IProcessingBlock source) {
			this.conn.sourceBlock = source;
			return this;
		}

		@Override
		public org.moonlightcontroller.processing.IConnector.Builder setSourceOutputPort(
				int port) {
			this.conn.sourcePort = port;
			return this;
		}

		@Override
		public org.moonlightcontroller.processing.IConnector.Builder setDestBlock(
				IProcessingBlock dest) {
			this.conn.destinationBlock = dest;
			return this;
		}
	}
}

