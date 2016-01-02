package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.Priority;

import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.IProcessingGraph;

public class RegexMatcher extends ProcessingBlock implements IClassifierProcessingBlock{
	private String[] pattern;
	private boolean payload_only;
	private boolean match_all;
	private Priority priority;

	public RegexMatcher(String id, String[] pattern) {
		super(id);
		this.pattern = pattern;
	}
	
	public RegexMatcher(String id, String[] pattern, boolean payload_only, boolean match_all, Priority priority) {
		super(id);
		this.pattern = pattern;
		this.payload_only = payload_only;
		this.match_all = match_all;
		this.priority = priority;
	}

	public String[] getPattern() {
		return pattern;
	}

	public boolean getPayload_only() {
		return payload_only;
	}

	public boolean getMatch_all() {
		return match_all;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, Object> config) {
		config.put("pattern", this.pattern.toString());
		config.put("payload_only", this.payload_only);
		config.put("match_all", this.match_all);
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new RegexMatcher(id, pattern, payload_only, match_all, priority);
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
