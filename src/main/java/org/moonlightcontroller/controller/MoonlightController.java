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

public class MoonlightController {

	private IApplicationRegistry registry;
	private ISouthboundServer sserver;
	
	public MoonlightController(
			IApplicationRegistry registry, 
			ITopologyManager topology,
			int port) {
		this.registry = registry;
		this.sserver = new SouthboundServer(port);
	}
	
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