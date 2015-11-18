package org.moonlightcontroller.controller;

import java.util.List;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.southbound.client.ISouthboundClient;
import org.moonlightcontroller.southbound.server.ISouthboundServer;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.ITopologyManager;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class MoonlightController {

	private IApplicationRegistry registry;
	private ITopologyManager topology;
	private ISouthboundClient sclient;
	private ISouthboundServer sserver;
	
	public MoonlightController(
			IApplicationRegistry registry, 
			ITopologyManager topology,
			ISouthboundClient sclient) {
		this.registry = registry;
		this.topology = topology;
		this.sclient = sclient;
	}
	
	public void start(){
		ApplicationAggregator aggregator = new ApplicationAggregator(this.topology);
		aggregator.addApplications(this.registry.getApplications());
		aggregator.performAggregation();
		for (InstanceLocationSpecifier endpoint : topology.getAllEndpoints()){
			List<IStatement> stmts = aggregator.getBlocks(endpoint);
			this.sclient.sendProcessingGraph(endpoint, stmts);
		}
	}
	
	
	public void spin(){
		// Here the controller should wait for incoming events or incoming calls from OBIs
	}
}