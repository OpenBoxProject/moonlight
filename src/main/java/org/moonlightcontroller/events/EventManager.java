package org.moonlightcontroller.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.southbound.client.SouthboundClient;
import org.openboxprotocol.protocol.topology.ApplicationTopology;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class EventManager implements IEventManager {

	private static IEventManager instance;
	
	private Map<EventType, ArrayList<BoxApplication>> registeredHandlers;
	private HashMap<String, BoxApplication> apps;
	
	private EventManager() {
		this.apps = new HashMap<>();
		this.registeredHandlers = new HashMap<>();
		for (EventType t : EventType.values()) {
			this.registeredHandlers.put(t, new ArrayList<>());
		}
	}
	
	public static IEventManager getInstance(){
		if (instance == null){
			instance = new EventManager();
		}
		
		return instance;
	}
	
	@Override
	public void addApplications(List<BoxApplication> apps) {
		for (BoxApplication app : apps){
			this.registerForEvents(app);
			this.apps.put(app.getName(), app);
		}
	}
	
	@Override
	public void HandleAlert(InstanceAlertArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.Alert)) {
			app.getAlertListener().Handle(args);
		}
	}

	@Override
	public void HandleInstanceDown(InstanceDownArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceDown)) {
			app.getInstanceDownListener().Handle(args);
		}
	}

	@Override
	public void HandleInstanceUp(InstanceUpArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceUp)) {
			app.getInstanceUpListener().Handle(args);
		}
	}
	
	@Override
	public void HandleAppStart(String appId) {
		this.apps.get(appId).handleAppStart(
				new ApplicationTopology(TopologyManager.getInstance()),
				(IHandleClient) new HandleClient(SouthboundClient.getInstance()));
	}

	@Override
	public void HandleAppStop(String appId) {
		this.apps.get(appId).handleAppStop();
	}

	@Override
	public void HandleError(String appId) {
		this.apps.get(appId).handleError();
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
