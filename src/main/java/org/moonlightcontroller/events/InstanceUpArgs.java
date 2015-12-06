package org.moonlightcontroller.events;

import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class InstanceUpArgs {

	private InstanceLocationSpecifier instance;
	
	public InstanceUpArgs(InstanceLocationSpecifier instance) {
		this.instance = instance;
	}
	
	public InstanceLocationSpecifier getInstance(){
		return this.instance;
	}
}
