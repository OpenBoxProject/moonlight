package org.moonlightcontroller.events;

import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;

public interface IEventManager {
	void addApplications(List<BoxApplication> apps);
	void HandleAlert(InstanceAlertArgs args);
	void HandleInstanceDown(InstanceDownArgs args);
	void HandleInstanceUp(InstanceUpArgs args);
	void HandleAppStart(String appId);
	void HandleAppStop(String appId);
	void HandleError(String appId);
}
