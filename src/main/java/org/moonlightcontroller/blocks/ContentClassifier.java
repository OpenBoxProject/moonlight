package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.Priority;

import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.IProcessingGraph;

public class ContentClassifier extends ProcessingBlock implements IClassifierProcessingBlock{
	private String[] pattern;
	private Priority priority;
	
	public ContentClassifier(String id, String[] pattern, Priority priority) {
		super(id);
		this.pattern = pattern;
		this.priority = priority;
	}

	public String[] getPattern() {
		return pattern;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("pattern", this.pattern.toString());
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new ContentClassifier(id, this.pattern, priority);
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
