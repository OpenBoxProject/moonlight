package org.moonlightcontroller.blocks;

import java.util.HashMap;
import java.util.Map;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.HeaderMatch;

public class HeaderClassifier extends ProcessingBlock {

	private Map<HeaderMatch, Integer> headerToPort;
	
	public int getPort(HeaderMatch hf) {
		return this.headerToPort.get(hf);
	}
	
	protected static abstract class Init<T extends Init<T>> extends ProcessingBlock.Init<T> {
		protected Map<HeaderMatch, Integer> headerToPort;
		
		protected Init() {
			super();
			this.headerToPort = new HashMap<>();
		}
		public T addMatch(HeaderMatch hf) {
			super.addPort();
			this.headerToPort.put(hf, super.portCount);
			return self();
		}

		public HeaderClassifier build(){
			return new HeaderClassifier(this);
		}
	}
	
    public static class Builder extends Init<Builder> {
    	@Override
        protected Builder self() {
            return this;
        }
    }
    
	protected HeaderClassifier(Init<?> init) {
		super(init);
		this.headerToPort = init.headerToPort;
	}
}
