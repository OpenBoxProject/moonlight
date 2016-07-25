package org.moonlightcontroller.events;

/**
 * An interface which application can implement to receive alert from the control plane
 */
public interface IInstanceDownListener {
	void Handle(InstanceDownArgs args);
}
