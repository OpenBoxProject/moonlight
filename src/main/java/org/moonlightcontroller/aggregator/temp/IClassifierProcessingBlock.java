package org.moonlightcontroller.aggregator.temp;

import java.util.List;

import org.moonlightcontroller.aggregator.temp.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.openboxprotocol.protocol.Priority;

public interface IClassifierProcessingBlock extends IProcessingBlock {
	public Priority getPriority();
	
	public boolean canMergeWith(IClassifierProcessingBlock other);
	
	public IClassifierProcessingBlock mergeWith(IClassifierProcessingBlock other, IProcessingGraph containingGraph, List<Pair<Integer>> outPortSources) throws MergeException;
}
