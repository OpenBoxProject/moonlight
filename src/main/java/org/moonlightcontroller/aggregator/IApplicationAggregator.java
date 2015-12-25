package org.moonlightcontroller.aggregator;

import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.managers.models.messages.Alert;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IApplicationAggregator {
	void addApplications(List<BoxApplication> apps);
	void addApplication(BoxApplication apps);
	void performAggregation();
	IProcessingGraph getProcessingGraph(ILocationSpecifier loc);
	void handleAlert(Alert message);
}
