package org.moonlightcontroller.blocks;

import java.util.HashMap;
import java.util.Map;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.HeaderMatch;

public abstract class HeaderClassifier extends ProcessingBlock {

	private Map<HeaderMatch, Integer> headerToPort;
	
	public HeaderClassifier(String id) {
		super(id);
		this.headerToPort = new HashMap<>();
	}
	
	public int addMatch(HeaderMatch hf) {
		int p = super.addPort();
		this.headerToPort.put(hf, p);
		return p;
	}
	
	public int getPort(HeaderMatch hf) {
		return this.headerToPort.get(hf);
	}
}
