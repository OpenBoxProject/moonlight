package org.moonlightcontroller.managers.models.messages;

import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;

public class AdminReadRequest extends Message {

	private String blockId;
	private String readHandle;
	private InstanceLocationSpecifier locationSpecifier;

	// Default constructor to support Jersy
	public AdminReadRequest() {
		super();
		this.type = "ReadRequest";
	}

	public AdminReadRequest(String block, long dpid) {
		this();
		this.blockId = block;
		this.locationSpecifier = new InstanceLocationSpecifier(dpid);
	}



	public String getBlockId() {
		return blockId;
	}

	public String getReadHandle() {
		return readHandle;
	}

	public ILocationSpecifier getLocationSpecifier() {
		return locationSpecifier;
	}

	public ReadRequest getReadRequest() {
		return new ReadRequest((int) this.locationSpecifier.getId(), blockId);
	}
}
