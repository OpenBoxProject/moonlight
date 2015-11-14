package org.openboxprotocol.protocol;

import java.util.List;

import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IStatement {

	public ILocationSpecifier getLocation();
	
	public List<ProcessingBlock> getBlocks();
	
	public List<IConnector> getConnectors();
	
	public interface Builder {
		public Builder setLocation(ILocationSpecifier locspec);
		
		public Builder addBlock(ProcessingBlock block);
		
		public Builder setBlocks(List<ProcessingBlock> blocks);
		
		public Builder addConnector(IConnector connector);
		
		public Builder setConnectors(List<IConnector> connectors);
		
		public IStatement build();
	}
}
