package org.openboxprotocol.protocol;

import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.topology.ILocationSpecifier;

public interface IStatement {

	public ILocationSpecifier getLocation();
	
	public IProcessingGraph getProcessingGraph();
	
	public interface Builder {
		public Builder setLocation(ILocationSpecifier locspec);
		
		public Builder setProcessingGraph(IProcessingGraph graph);
		
		public IStatement build();
	}
}
