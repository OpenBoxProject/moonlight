package org.moonlightcontroller.aggregator;

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
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class ApplicationAggregator implements IApplicationAggregator {

	private List<BoxApplication> apps;
	private ITopologyManager topology;
	private Map<InstanceLocationSpecifier, ArrayList<IStatement>> aggregatedStatement;
	
	public ApplicationAggregator(ITopologyManager topology) {
		this.topology = topology;
		this.aggregatedStatement = new HashMap<>();	
		this.apps = new ArrayList<>();
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

	@Override
	public List<IStatement> getStatements(ILocationSpecifier loc) {
		if (!loc.isSingleLocation()) {
			// Throw exception?
			return null;
		}
		return this.aggregatedStatement.get(loc);
	}
}