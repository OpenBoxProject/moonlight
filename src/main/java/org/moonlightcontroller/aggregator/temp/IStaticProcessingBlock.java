package org.moonlightcontroller.aggregator.temp;

import org.moonlightcontroller.exceptions.MergeException;

public interface IStaticProcessingBlock extends IProcessingBlock {

	public boolean canMergeWith(IStaticProcessingBlock other);
	
	public IStaticProcessingBlock mergeWith(IStaticProcessingBlock other) throws MergeException;
}
