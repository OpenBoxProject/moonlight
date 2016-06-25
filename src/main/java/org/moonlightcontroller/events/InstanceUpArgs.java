package org.moonlightcontroller.events;

import org.moonlightcontroller.topology.InstanceLocationSpecifier;

public class InstanceUpArgs {

	private InstanceLocationSpecifier instance;
	
	public InstanceUpArgs(InstanceLocationSpecifier instance) {
		this.instance = instance;
	}
	
	public InstanceLocationSpecifier getInstance(){
		return this.instance;
	}
}
