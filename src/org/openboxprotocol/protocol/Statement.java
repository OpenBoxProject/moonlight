package org.openboxprotocol.protocol;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public class Statement implements IStatement{

	private List<ProcessingBlock> blocks;
	private List<IConnector> connectors;
	private ILocationSpecifier location;
	
	@Override
	public ILocationSpecifier getLocation() {
		return location;
	}

	@Override
	public List<ProcessingBlock> getBlocks() {
		return blocks;
	}

	@Override
	public List<IConnector> getConnectors() {
		return connectors;
	}

	private Statement(Builder builder) {
		this.blocks = builder.blocks;
		this.connectors = builder.connectors;
		this.location = builder.location;
	}
	
	public static class Builder implements IStatement.Builder {

		private ArrayList<ProcessingBlock> blocks;
		private ArrayList<IConnector> connectors;
		private ILocationSpecifier location;
		
		public Builder() {
			this.blocks = new ArrayList<>();
			this.connectors = new ArrayList<>();
		}
		
		@Override
		public org.openboxprotocol.protocol.IStatement.Builder setLocation(
				ILocationSpecifier locspec) {
			this.location = locspec;
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.IStatement.Builder addBlock(
				ProcessingBlock block) {
			this.blocks.add(block);
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.IStatement.Builder setBlocks(
				List<ProcessingBlock> blocks) {
			this.blocks = new ArrayList<>(blocks);
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.IStatement.Builder addConnector(
				IConnector connector) {
			this.connectors.add(connector);
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.IStatement.Builder setConnectors(
				List<IConnector> connectors) {
			this.connectors = new ArrayList<>(connectors);
			return this;
		}

		@Override
		public IStatement build() {
			return new Statement(this);
		}
	}
}
