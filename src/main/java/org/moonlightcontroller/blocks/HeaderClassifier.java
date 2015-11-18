package org.moonlightcontroller.blocks;

import java.util.HashMap;
import java.util.Map;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.HeaderMatch;

public class HeaderClassifier extends ProcessingBlock {

	private Map<HeaderMatch, Integer> headerToPort;
	
	private HeaderClassifier(String id, Map<HeaderMatch, Integer> headerToPort) {
		super(id);
		this.headerToPort = headerToPort;
	}
	
	public int getPort(HeaderMatch hf) {
		return this.headerToPort.get(hf);
	}
	
	public static class Builder extends ProcessingBlock.Builder {
		private Map<HeaderMatch, Integer> headerToPort;
		
		public Builder() {
			super();
			this.headerToPort = new HashMap<>();
		}
		
		public Builder addMatch(HeaderMatch hf) {
			super.addPort();
			this.headerToPort.put(hf, super.portCount);
			return this;
		}

		public HeaderClassifier build(){
			return new HeaderClassifier(super.id, this.headerToPort);
		}
	}
}
