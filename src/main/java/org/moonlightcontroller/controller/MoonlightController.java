package org.moonlightcontroller.controller;

import java.util.List;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.aggregator.IApplicationAggregator;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.events.EventManager;
import org.moonlightcontroller.events.IEventManager;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.southbound.server.ISouthboundServer;
import org.moonlightcontroller.southbound.server.SouthboundServer;
import org.moonlightcontroller.topology.ITopologyManager;

/**
 * The class representing the Moonlight Controller 
 *
 */
public class MoonlightController {

	private IApplicationRegistry registry;
	private ISouthboundServer sserver;
	
	/**
	 * Initializes a new controller with the given parameters
	 * @param registry An already loaded application registry
	 * @param topology The network topology
	 * @param port The port on which the controller should be listening
	 */
	public MoonlightController(
			IApplicationRegistry registry, 
			ITopologyManager topology,
			int port) {
		this.registry = registry;
		this.sserver = new SouthboundServer(port);
	}
	
	/**
	 * Starts the controller
	 * Iterates over all loaded OpenBox applications and sends them for aggregation
	 * Also, registers them for event handling
	 */
	public void start(){
		List<BoxApplication> apps = this.registry.getApplications();
		
		IApplicationAggregator aggregator = ApplicationAggregator.getInstance();
		aggregator.addApplications(apps);
		aggregator.performAggregation();
		
		IEventManager eManager = EventManager.getInstance();
		eManager.addApplications(apps);		
		
		for (BoxApplication app : apps){
			eManager.HandleAppStart(app.getName());
		}
		
		try {
			this.sserver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}