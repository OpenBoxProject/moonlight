package org.moonlightcontroller.aggregator;

import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.managers.models.messages.Alert;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.topology.ILocationSpecifier;

/**
 * Interface for the application aggregator
 *
 */
public interface IApplicationAggregator {

	/**
	 * Performs aggregation for all added applications
	 */
	void performAggregation(List<BoxApplication> apps);

	/**
	 * after performAggregation is called this method returns a processing graph for each given location
	 * @param loc
	 * @return
	 */
	IProcessingGraph getProcessingGraph(ILocationSpecifier loc);
	
	/**
	 * Handles alerts coming from the control plane.
	 * It will know to which application the alert should be routed
	 * @param message
	 */
	void handleAlert(Alert message);
}
