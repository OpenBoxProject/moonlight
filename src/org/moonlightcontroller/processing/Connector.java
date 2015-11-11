package org.moonlightcontroller.processing;

public class Connector implements IConnector {

	private String sourceBlock;
	private int sourcePort;
	private String destinationBlock;
	
	private Connector() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSourceBlockId() {
		return this.sourceBlock;
	}

	@Override
	public int getSourceBlockPort() {
		return this.sourcePort;
	}

	@Override
	public String getDestinatinBlockId() {;
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
		public org.moonlightcontroller.processing.IConnector.Builder setSourceBlockId(
				String src) {
			this.conn.sourceBlock = src;
			return this;
		}

		@Override
		public org.moonlightcontroller.processing.IConnector.Builder setDestinationBlockId(
				String dst) {
			this.conn.destinationBlock = dst;
			return this;
		}

		@Override
		public org.moonlightcontroller.processing.IConnector.Builder setSourceBlockPort(
				int port) {
			this.conn.sourcePort = port;
			return this;
		}

		@Override
		public IConnector build() {
			return this.conn;
		}
	}
}

