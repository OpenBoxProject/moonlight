package org.moonlightcontroller.aggregator.temp;

import java.util.List;

import org.moonlightcontroller.aggregator.Tupple.Pair;
import org.moonlightcontroller.exceptions.MergeException;
import org.openboxprotocol.protocol.Priority;

public interface IClassifierProcessingBlock extends IProcessingBlock {
	public Priority getPriority();
	
	public boolean canMergeWith(IClassifierProcessingBlock other);
	
	/**
	 * Merges this instance of classifier with another classifier block.
	 * 
	 * @param other The other classifier block to merge with
	 * @param containingGraph Graph containing the two blocks to be merged
	 * @param outPortSources An empty list to be filled by this method. Eventually, the i'th element of this list will be a pair specifying the original source output ports of this instance (index 0 in the pair) and other (index 1 in the pair)
	 * @return Merged classifier block
	 * @throws MergeException If merge fails
	 */
	public IClassifierProcessingBlock mergeWith(IClassifierProcessingBlock other, IProcessingGraph containingGraph, List<Pair<Integer>> outPortSources) throws MergeException;
}
