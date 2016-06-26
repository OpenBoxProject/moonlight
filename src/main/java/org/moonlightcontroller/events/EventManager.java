package org.moonlightcontroller.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.moonlightcontroller.bal.BoxApplication;
import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.topology.ApplicationTopology;
import org.moonlightcontroller.topology.TopologyManager;


/**
 * A class which is responsible for dispatching incoming Alerts/Events
 * to the different applications.
 */
public class EventManager implements IEventManager {

	private final static Logger LOG = Logger.getLogger(EventManager.class.getName()); 
	
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
	
	public synchronized static IEventManager getInstance(){
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
	public void HandleAlert(String app, InstanceAlertArgs args) {
		BoxApplication bapp = this.apps.get(app);
		if (bapp != null) {
			IAlertListener al = bapp.getAlertListener();
			if (al != null) {
				try {
					al.Handle(args);
					} catch (Exception e) {
						LOG.warning("Caught exception while handling app specific alert for app " + app + ":" + e.toString());
					}
			}
		}
		
	}
	
	@Override
	public void HandleInstanceDown(InstanceDownArgs args){
		// TODO: Handle refresh requests from apps
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceDown)) {
			try {
				IInstanceDownListener l = app.getInstanceDownListener();
				if (l != null){
					l.Handle(args);	
				}
			} catch (Exception e){
				LOG.warning("Caught exception while handling instance down for app " + app.getName() + ":" + e.toString());
			}
		}
	}

	@Override
	public void HandleInstanceUp(InstanceUpArgs args){
		for (BoxApplication app : this.registeredHandlers.get(EventType.InstanceUp)) {
			try {
				IInstanceUpListener l = app.getInstanceUpListener();	
				if (l != null){
					l.Handle(args);	
				}
			} catch (Exception e){
				LOG.warning("Caught exception while handling instance up for app " + app.getName() + ":" + e.toString());
			}			
		}
	}
	
	@Override
	public void HandleAppStart(String appId) {
		try {
			this.apps.get(appId).handleAppStart(new ApplicationTopology(TopologyManager.getInstance()),
					new HandleClient(ConnectionManager.getInstance()));
		} catch (Exception e) {
			LOG.warning("Caught exception while handling app start for app " + appId + ":" + e.toString());
		}
	}

	@Override
	public void HandleAppStop(String appId) {
		try {
		this.apps.get(appId).handleAppStop();
		} catch (Exception e){
			LOG.warning("Caught exception while handling app stop for app " + appId + ":" + e.toString());
		}
	}

	@Override
	public void HandleError(String appId) {
		try {
		this.apps.get(appId).handleError();
		} catch (Exception e) {
			LOG.warning("Caught exception while handling error for app " + appId + ":" + e.toString());
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
