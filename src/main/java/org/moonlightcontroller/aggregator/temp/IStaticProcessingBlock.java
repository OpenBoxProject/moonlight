package org.moonlightcontroller.aggregator.temp;

public interface IStaticProcessingBlock extends IProcessingBlock {

	public boolean canMergeWith(IStaticProcessingBlock other);
}
