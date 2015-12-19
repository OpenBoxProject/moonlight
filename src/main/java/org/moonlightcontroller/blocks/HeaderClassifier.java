package org.moonlightcontroller.blocks;

import java.util.HashMap;
import java.util.Map;

import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.HeaderMatch;

public class HeaderClassifier extends ProcessingBlock {

	private Map<HeaderMatch, Integer> headerToPort;
	
	public HeaderClassifier(String id) {
		super(id);
		this.headerToPort = new HashMap<>();
	}
	
	public int getPort(HeaderMatch hf) {
		return this.headerToPort.get(hf);
	}
	
	public void addMatch(HeaderMatch hf) {
		this.headerToPort.put(hf, super.addPort());
	}
	
	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	public String getBlockType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessingBlock clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		// TODO Auto-generated method stub
		// TODO: [Yotam] this should be completed to make the demo work
		// It should serialize the configuration of matches as JSON
	}
}
