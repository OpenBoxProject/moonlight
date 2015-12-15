package org.moonlightcontroller.aggregator.temp;
import java.util.List;

public interface IProcessingGraph {
	public List<IProcessingBlock> getBlocks();
	public List<IConnector> getConnectors();
	public List<IProcessingBlock> getSuccessors(IProcessingBlock block);
	public List<IConnector> getOutgoingConnectors(IProcessingBlock block);
	public List<IConnector> getIncomingConnectors(IProcessingBlock block);
	public IConnector getOutgoingConnector(IProcessingBlock block, int outPort);
	public int getOutDegree(IProcessingBlock block);
	public int getInDegree(IProcessingBlock block);
	public IProcessingBlock getRoot();
	
	public interface Builder {
		public Builder setBlocks(List<IProcessingBlock> blocks);
		public Builder setConnectors(List<IConnector> connectors);
		public Builder setRoot(IProcessingBlock root);
		public IProcessingGraph build();
	}
}
