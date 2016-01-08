package org.moonlightcontroller.events;

import org.moonlightcontroller.managers.models.messages.Alert;
import org.moonlightcontroller.processing.IProcessingBlock;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class InstanceAlertArgs {

	private InstanceLocationSpecifier instance;
	private Alert alert;
	private IProcessingBlock block;

	public InstanceAlertArgs(InstanceLocationSpecifier loc, Alert alert, IProcessingBlock bl) {
		this.instance = loc;
		this.alert = alert;
		this.block = bl;
	}

	public InstanceLocationSpecifier getInstance() {
		return instance;
	}

	public Alert getAlert() {
		return alert;
	}

	public IProcessingBlock getBlock() {
		return block;
	}
}
