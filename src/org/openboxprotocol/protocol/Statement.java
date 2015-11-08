package org.openboxprotocol.protocol;

import java.util.List;

import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface Statement {

	public ILocationSpecifier getLocation();
	
	public List<ProcessingBlock> getBlocks();
	
	public List<IConnector> getConnectors();
	
	public interface Builder {
		public Builder setLocation(ILocationSpecifier locspec);
		
		public Builder addBlock(ProcessingBlock block);
		
		public Builder addConnector(IConnector connector);
		
		public Statement build();
	}
}
