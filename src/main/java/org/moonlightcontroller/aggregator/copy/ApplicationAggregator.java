package org.moonlightcontroller.aggregator.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.processing.ProcessingBlock;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.Statement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.ITopologyManager;

public class ApplicationAggregator implements IApplicationAggregator {

	private List<BoxApplication> apps;
	private ITopologyManager topology;
	private Map<ILocationSpecifier, ArrayList<IStatement>> aggregatedBlocks;
	
	public ApplicationAggregator(ITopologyManager topology) {
		this.topology = topology;
		this.aggregatedBlocks = new HashMap<>();	
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
				ArrayList<IStatement> stmts = this.aggregatedBlocks.get(loc);
				if (stmts == null) {
					stmts = new ArrayList<>();
					this.aggregatedBlocks.put(loc, stmts);
				}
				stmts.add(st);
			}
		}
	}

	@Override
	public List<IStatement> getBlocks(ILocationSpecifier loc) {
		if (!loc.isSingleLocation()) {
			// Throw exception?
			return null;
		}
		return this.aggregatedBlocks.get(loc);
	}
}
