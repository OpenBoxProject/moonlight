package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.Priority;

import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.IProcessingGraph;

public class HeaderPayloadClassifier extends ProcessingBlock implements IClassifierProcessingBlock{
	private compound_matches match;
	private Priority priority;

	public HeaderPayloadClassifier(String id, compound_matches match, Priority priority) {
		super(id);
		this.match = match;
	}

	public compound_matches getMatch() {
		return match;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
		config.put("match", this.match.toString());
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new HeaderPayloadClassifier(id, match, priority);
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
class compound_matches {}