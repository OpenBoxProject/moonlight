package org.moonlightcontroller.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.aggregator.ApplicationAggregator;
import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.events.EventType;
import org.moonlightcontroller.events.InstanceAlertArgs;
import org.moonlightcontroller.events.InstanceDownArgs;
import org.moonlightcontroller.events.InstanceUpArgs;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.southbound.client.ISouthboundClient;
import org.moonlightcontroller.southbound.server.ISouthboundServer;
import org.moonlightcontroller.southbound.server.SouthboundServer;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ITopologyManager;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class MoonlightController {

	private IApplicationRegistry registry;
	private ITopologyManager topology;
	private ISouthboundClient sclient;
	private ISouthboundServer sserver;
	private Map<EventType, ArrayList<BoxApplication>> registeredHandlers;

	public MoonlightController(IApplicationRegistry registry, 
			ITopologyManager topology,
			ISouthboundClient sclient) {
		this.registry = registry;
		this.topology = topology;
		this.sclient = sclient;
		this.sserver = new SouthboundServer();
		this.registeredHandlers = new HashMap<>();
		for (EventType t : EventType.values()) {
			this.registeredHandlers.put(t, new ArrayList<>());
		}
	}

	public void start(){
		ApplicationAggregator aggregator = ApplicationAggregator.getInstance();
		List<BoxApplication> apps = this.registry.getApplications();
		aggregator.addApplications(apps);
		aggregator.performAggregation();

		for (BoxApplication app : apps){
			this.registerForEvents(app);
		}

		for (InstanceLocationSpecifier endpoint : topology.getAllEndpoints()){
			List<IStatement> stmts = aggregator.getStatements(endpoint);
			if (stmts != null) {
				this.sclient.sendProcessingGraph(endpoint, stmts);	
			}
		}

		try {
			this.sserver.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void HandleAlert(InstanceAlertArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.Alert)) {
			app.getAlertListener().Handle(args);
		}
	}

	public void HandleInstanceDown(InstanceDownArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceDown)) {
			app.getInstanceDownListener().Handle(args);
		}
	}

	public void HandleInstanceUp(InstanceUpArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceUp)) {
			app.getInstanceUpListener().Handle(args);
		}
	}

	private void registerForEvents(BoxApplication app) {
		if (app.getAlertListener() != null) {
			ArrayList<BoxApplication> l = this.registeredHandlers.get(EventType.Alert);
			l.add(app);
		}
		if (app.getInstanceDownListener() != null) {
			ArrayList<BoxApplication> l = this.registeredHandlers.get(EventType.InstanceDown);
			l.add(app);
		}
		if (app.getInstanceUpListener() != null) {
			ArrayList<BoxApplication> l = this.registeredHandlers.get(EventType.InstanceUp);
			l.add(app);
		}
	}
}