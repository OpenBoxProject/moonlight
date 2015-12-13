package org.moonlightcontroller.aggregator.temp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;


public class ProcessingGraph implements IProcessingGraph {

	protected List<IProcessingBlock> blocks;
	protected List<IConnector> connectors;
	private IProcessingBlock root;
	
	protected ProcessingGraph(List<IProcessingBlock> blocks, 
			List<IConnector> connectors,
			IProcessingBlock root) {
		this.blocks = blocks;
		this.connectors = connectors;
		this.root = root;
	}
	
	protected ProcessingGraph(IProcessingGraph graph) {
		this.blocks = new ArrayList<>(graph.getBlocks());
		this.connectors = new ArrayList<>(graph.getConnectors());
		this.root = graph.getRoot();
	}
	
	@Override
	public List<IProcessingBlock> getBlocks() {
		return ImmutableList.copyOf(this.blocks);
	}

	@Override
	public List<IConnector> getConnectors() {
		return ImmutableList.copyOf(this.connectors);
	}

	@Override
	public List<IProcessingBlock> getSuccessors(IProcessingBlock block) {
		return this.connectors.stream()
				.filter(c -> c.getSourceBlock().equals(block))
				.map(c -> c.getDestBlock())
				.collect(Collectors.toList());
	}

	@Override
	public List<IConnector> getOutgoingConnectors(IProcessingBlock block) {
		return this.connectors.stream()
				.filter(c -> c.getSourceBlock().equals(block))
				.collect(Collectors.toList());
	}

	@Override
	public List<IConnector> getIncomingConnectors(IProcessingBlock block) {
		return this.connectors.stream()
				.filter(c -> c.getDestBlock().equals(block))
				.collect(Collectors.toList());
	}

	@Override
	public int getOutDegree(IProcessingBlock block) {
		return (int)
				this.connectors.stream()
				.filter(c -> c.getSourceBlock().equals(block))
				.count();
	}

	@Override
	public int getInDegree(IProcessingBlock block) {
		return (int)
				this.connectors.stream()
				.filter(c -> c.getDestBlock().equals(block))
				.count();
	}

	@Override
	public IProcessingBlock getRoot() {
		return this.root;
	}
	
	private void toStringImpl(IProcessingBlock current, int depth, StringBuilder sb) {
		sb.append(current.toShortString()).append('\n');
		
		List<IConnector> outs = this.connectors.stream().filter(c -> c.getSourceBlock().equals(current)).collect(Collectors.toList());
		for (IConnector c : outs) {
			for (int i = 0; i < depth; i++)
				sb.append("        ");
			sb.append("+-");
			sb.append('[').append(c.getSourceOutputPort()).append(']').append("-->");
			toStringImpl(c.getDestBlock(), depth + 1, sb);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toStringImpl(getRoot(), 0, sb);
		return sb.toString();
	}

	public static class Builder implements IProcessingGraph.Builder {

		private List<IProcessingBlock> blocks = null;
		private List<IConnector> connectors = null;
		private IProcessingBlock root = null;
		
		@Override
		public IProcessingGraph.Builder setBlocks(List<IProcessingBlock> blocks) {
			this.blocks = blocks;
			return this;
		}

		@Override
		public IProcessingGraph.Builder setConnectors(
				List<IConnector> connectors) {
			this.connectors = connectors;
			return this;
		}
		
		@Override
		public IProcessingGraph.Builder setRoot(IProcessingBlock root) {
			this.root = root;
			return this;
		}
		
		@Override
		public IProcessingGraph build() {
			return new ProcessingGraph(blocks, connectors, root);
		}
		
	}

}
