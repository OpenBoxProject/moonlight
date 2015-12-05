package org.moonlightcontroller.controller;

import java.util.List;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.events.EventManager;
import org.moonlightcontroller.events.IEventManager;
import org.moonlightcontroller.managers.ISouthboundClient;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.southbound.server.ISouthboundServer;
import org.moonlightcontroller.southbound.server.SouthboundServer;
import org.openboxprotocol.protocol.topology.ITopologyManager;

public class MoonlightController {

	private IApplicationRegistry registry;
	private ITopologyManager topology;
	private ISouthboundServer sserver;
	private IEventManager eManager;
	
	public MoonlightController(
			IApplicationRegistry registry, 
			ITopologyManager topology,
			ISouthboundClient sclient) {
		this.registry = registry;
		this.topology = topology;
		this.sserver = new SouthboundServer();
	}
	
	public void start(){
		List<BoxApplication> apps = this.registry.getApplications();
		
		ApplicationAggregator aggregator = ApplicationAggregator.getInstance();
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