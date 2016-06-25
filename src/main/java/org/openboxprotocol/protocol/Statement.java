package org.openboxprotocol.protocol;

import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.topology.ILocationSpecifier;

public class Statement implements IStatement{

	private IProcessingGraph graph;
	private ILocationSpecifier location;
	
	@Override
	public ILocationSpecifier getLocation() {
		return location;
	}

	@Override
	public IProcessingGraph getProcessingGraph() {
		return graph;
	}

	private Statement(Builder builder) {
		this.graph = builder.graph;
		this.location = builder.location;
	}
	
	public static class Builder implements IStatement.Builder {

		private IProcessingGraph graph;
		private ILocationSpecifier location;
		
		@Override
		public Builder setLocation(
				ILocationSpecifier locspec) {
			this.location = locspec;
			return this;
		}

		@Override
		public Builder setProcessingGraph(IProcessingGraph graph) {
			this.graph = graph;
			return this;
		}

		@Override
		public IStatement build() {
			return new Statement(this);
		}
	}
}
