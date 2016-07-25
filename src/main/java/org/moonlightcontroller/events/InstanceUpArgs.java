package org.moonlightcontroller.events;

import org.moonlightcontroller.topology.InstanceLocationSpecifier;

/**
 * Arguments for the instance up event
 */
public class InstanceUpArgs {

	private InstanceLocationSpecifier instance;
	
	public InstanceUpArgs(InstanceLocationSpecifier instance) {
		this.instance = instance;
	}
	
	public InstanceLocationSpecifier getInstance(){
		return this.instance;
	}
}
