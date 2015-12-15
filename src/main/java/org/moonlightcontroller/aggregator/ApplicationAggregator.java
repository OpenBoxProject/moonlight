package org.moonlightcontroller.aggregator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.moonlightcontroller.aggregator.temp.IProcessingGraph;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.IProcessingBlock;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.ITopologyManager;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class ApplicationAggregator implements IApplicationAggregator {

	private List<BoxApplication> apps;
	private ITopologyManager topology;
	private Map<InstanceLocationSpecifier, ArrayList<IStatement>> aggregatedStatement;
	
	private static ApplicationAggregator instance;
	
	private ApplicationAggregator() {
		this.topology = TopologyManager.getInstance();
		this.aggregatedStatement = new HashMap<>();	
		this.apps = new ArrayList<>();
	}
	
	public static ApplicationAggregator getInstance() {
		if (instance == null) {
			instance = new ApplicationAggregator();
		}
		return instance;
	}
			
	@Override
	public void addApplication(BoxApplication app) {
		this.apps.add(app);
	}

	@Override
	public void addApplications(List<BoxApplication> apps) {
		this.apps.addAll(apps);
	}

	@Override
	public void performAggregation() {
		List<BoxApplication> sorted = 
				this.apps
				.stream()
				.sorted((a1 ,a2) -> a1.getPriority().compareTo(a2.getPriority()))
				.collect(Collectors.toList());
		
		
		for (BoxApplication ba : sorted){
			for (IStatement st : ba.getStatemens()){
				ILocationSpecifier loc = st.getLocation();
				for (InstanceLocationSpecifier ep : this.topology.getSubInstances(loc)) {
				ArrayList<IStatement> stmts = this.aggregatedStatement.get(ep);
					if (stmts == null) {
						stmts = new ArrayList<>();
						this.aggregatedStatement.put(ep, stmts);
					}
					stmts.add(st);
				}
			}
		}
	}

	/*
	@Override
	public IProcessingGraph getProcessingGraph(ILocationSpecifier loc) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
	@Override
	public IProcessingGraph getProcessingGraph(ILocationSpecifier loc) {
		// THIS IS A TEMPORARY CODE THAT WILL BE REPLACED ONCE THE NEW AGGREGATOR IS USED
		
		if (!loc.isSingleLocation()) {
			// Throw exception?
			return null;
		}
		List<IProcessingBlock> blocks = new ArrayList<>();
		List<IConnector> connectors = new ArrayList<>();
		
		List<IStatement> statements = this.aggregatedStatement.get(loc);
		
		for (IStatement s : statements) {
			blocks.addAll(s.getBlocks());
			connectors.addAll(s.getConnectors());
		}
		
		return new IProcessingGraph() {
			
			@Override
			public List<IProcessingBlock> getSuccessors(IProcessingBlock block) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public IProcessingBlock getRoot() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public List<IConnector> getOutgoingConnectors(IProcessingBlock block) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int getOutDegree(IProcessingBlock block) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public List<IConnector> getIncomingConnectors(IProcessingBlock block) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int getInDegree(IProcessingBlock block) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public List<IConnector> getConnectors() {
				return connectors;
			}
			
			@Override
			public List<IProcessingBlock> getBlocks() {
				return blocks;
			}
		};
	}
}