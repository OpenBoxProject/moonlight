package org.moonlightcontroller.aggregator;

import java.util.List;

import org.moonlightcontroller.aggregator.temp.IProcessingGraph;
import org.moonlightcontroller.bal.BoxApplication;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IApplicationAggregator {
	void addApplications(List<BoxApplication> apps);
	void addApplication(BoxApplication apps);
	void performAggregation();
	IProcessingGraph getProcessingGraph(ILocationSpecifier loc);
}
