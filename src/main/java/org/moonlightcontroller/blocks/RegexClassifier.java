package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.Priority;

import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.IProcessingGraph;

public class RegexClassifier extends ProcessingBlock implements IClassifierProcessingBlock{
	private String[] pattern;
	private boolean payload_only;

	public RegexClassifier(String id, String[] pattern) {
		super(id);
		this.pattern = pattern;
	}
	
	public RegexClassifier(String id, String[] pattern, boolean payload_only) {
		super(id);
		this.pattern = pattern;
		this.payload_only = payload_only;
	}

	public String[] getPattern() {
		return pattern;
	}

	public boolean getPayload_only() {
		return payload_only;
	}

	@Override
	public String getBlockType() {
		return this.getClass().getName();
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("pattern", this.pattern.toString());
		config.put("payload_only", this.payload_only? "true" : "false");
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new RegexClassifier(id, pattern, payload_only);
	}

	@Override
	public Priority getPriority() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canMergeWith(IClassifierProcessingBlock other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IClassifierProcessingBlock mergeWith(IClassifierProcessingBlock other, IProcessingGraph containingGraph,
			List<Pair<Integer>> outPortSources) throws MergeException {
		// TODO Auto-generated method stub
		return null;
	}
}
