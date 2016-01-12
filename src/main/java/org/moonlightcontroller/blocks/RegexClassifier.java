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
	private List<String> pattern;
	private boolean payload_only;
	private Priority priority;

	public RegexClassifier(String id, List<String> pattern, Priority priority) {
		super(id);
		this.pattern = pattern;
		this.priority = priority;
	}
	
	public RegexClassifier(String id, List<String> pattern, boolean payload_only, Priority priority) {
		super(id);
		this.pattern = pattern;
		this.payload_only = payload_only;
	}

	public List<String> getPattern() {
		return pattern;
	}

	public boolean getPayload_only() {
		return payload_only;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
		config.put("pattern", this.pattern.toArray(new String[0]));
		config.put("payload_only", this.payload_only);
	}
	
	@Override
	protected ProcessingBlock spawn(String id) {
		return new RegexClassifier(id, pattern, payload_only, priority);
	}

	@Override
	public Priority getPriority() {
		return priority;
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
