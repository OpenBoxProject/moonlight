package org.moonlightcontroller.events;

import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;

public interface IEventManager {	

	/**
	 * Registers application for all events and alerts
	 * @param apps a list of application to register
	 */
	void addApplications(List<BoxApplication> apps);
	
	/**
	 * Sends Alert the the given application with the given parameters
	 * @param app application identifier
	 * @param args the alert arguments
	 */
	void HandleAlert(String app, InstanceAlertArgs args);
	
	/**
	 * Sends instance down event to all applications
	 * @param args the event arguments
	 */	
	void HandleInstanceDown(InstanceDownArgs args);

	/**
	 * Sends instance up event to all applications
	 * @param args the event arguments
	 */	
	void HandleInstanceUp(InstanceUpArgs args);

	/**
	 * Sends application started event to the given application
	 * @param app application identifier
	 */	
	void HandleAppStart(String appId);
	
	/**
	 * Sends application stopped event to the given application
	 * @param app application identifier
	 */	
	void HandleAppStop(String appId);
	
	/**
	 * Sends application error event to the given application
	 * @param app application identifier
	 */	
	void HandleError(String appId);
}
