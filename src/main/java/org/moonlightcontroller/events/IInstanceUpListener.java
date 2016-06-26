package org.moonlightcontroller.events;

/**
 * An interface which application can implement to receive instance up event
 */
public interface IInstanceUpListener {
	void Handle(InstanceUpArgs args);
}
