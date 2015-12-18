package org.moonlightcontroller.processing;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class MutableProcessingGraph extends ProcessingGraph {

	public MutableProcessingGraph(IProcessingGraph graph) {
		super(graph);
	}
	
	@Override
	public List<IProcessingBlock> getBlocks() {
		return this.blocks;
	}

	@Override
	public List<IConnector> getConnectors() {
		return this.connectors;
	}

	public MutableProcessingGraph addBlock(IProcessingBlock block) {
		this.blocks.add(block);
		return this;		
	}
	
	public MutableProcessingGraph addConnector(IConnector connector) {
		this.connectors.add(connector);
		return this;
	}
	
	public MutableProcessingGraph addBlocks(Collection<IProcessingBlock> blocks) {
		this.blocks.addAll(blocks);
		return this;
	}
	
	public MutableProcessingGraph addConnectors(Collection<IConnector> connectors) {
		this.connectors.addAll(connectors);
		return this;
	}
	
	public MutableProcessingGraph replaceConnector(IConnector oldConnector, IConnector newConnector) {
		this.connectors.remove(oldConnector);
		this.connectors.add(newConnector);
		return this;
	}
	
	public MutableProcessingGraph removeConnector(IConnector connector) {
		this.connectors.remove(connector);
		return this;
	}

	public MutableProcessingGraph removeBlock(IProcessingBlock block) {
		this.blocks.remove(block);
		this.connectors.removeIf(c -> c.getSourceBlock().equals(block) || c.getDestBlock().equals(block));
		return this;
	}
	
	public MutableProcessingGraph replaceBlock(IProcessingBlock oldBlock, IProcessingBlock newBlock, List<IConnector> newOutgoingConnectors) {
		this.blocks.remove(oldBlock);
		this.blocks.add(newBlock);
		this.connectors.removeIf(c -> c.getSourceBlock().equals(oldBlock));
		List<IConnector> incoming = this.getIncomingConnectors(oldBlock);
		incoming.forEach(c -> this.connectors.add(new Connector.Builder()
														.setSourceBlock(c.getSourceBlock())
														.setSourceOutputPort(c.getSourceOutputPort())
														.setDestBlock(newBlock)
														.build()));
		
		this.connectors.removeIf(c -> c.getDestBlock().equals(oldBlock));
		this.connectors.addAll(newOutgoingConnectors);
		return this;
	}
	
	private void markConnected(IProcessingBlock current, Set<IProcessingBlock> markedBlocks, Set<IConnector> markedConnectors) {
		if (!this.blocks.contains(current))
			return;
		
		markedBlocks.add(current);
		List<IConnector> outs = this.getOutgoingConnectors(current);
		
		for (IConnector c : outs) {
			if (this.blocks.contains(c.getDestBlock()))
				markedConnectors.add(c);
		}
		
		outs.forEach(c -> markConnected(c.getDestBlock(), markedBlocks, markedConnectors));
	}
	
	public MutableProcessingGraph clean() {
		Set<IProcessingBlock> markedBlocks = new HashSet<>();
		Set<IConnector> markedConnectors = new HashSet<>();
		
		markConnected(getRoot(), markedBlocks, markedConnectors);
		
		this.blocks.removeIf(b -> !(markedBlocks.contains(b)));
		this.connectors.removeIf(c -> !(markedConnectors.contains(c)));
		
		return this;
	}
}
