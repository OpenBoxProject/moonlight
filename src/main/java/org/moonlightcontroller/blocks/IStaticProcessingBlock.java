package org.moonlightcontroller.blocks;

import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.IProcessingBlock;

public interface IStaticProcessingBlock extends IProcessingBlock {

	public boolean canMergeWith(IStaticProcessingBlock other);
	
	public IStaticProcessingBlock mergeWith(IStaticProcessingBlock other) throws MergeException;
}
