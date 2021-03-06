package org.openbox.dashboard;

import org.moonlightcontroller.managers.models.messages.GlobalStatsRequest;
import org.moonlightcontroller.managers.models.messages.Message;
import org.moonlightcontroller.managers.models.messages.ReadRequest;
import org.moonlightcontroller.managers.models.messages.WriteRequest;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;

public class DashboardMessageRequest {

	private String type = GlobalStatsRequest.class.getSimpleName();
	private String blockId;
	private String handle;
	private String value = null;
	private InstanceLocationSpecifier locationSpecifier;

	// Default constructor to support Jersey
	public DashboardMessageRequest() {
	}

	public DashboardMessageRequest(String type, String block, long dpid, String handle) {
		this.type = type;
		this.blockId = block;
		this.handle = handle;
		this.locationSpecifier = new InstanceLocationSpecifier(dpid);
	}

	public String getValue() {
		return value;
	}

	public String getType() {
		return type;
	}

	public String getBlockId() {
		return blockId;
	}

	public String getHandle() {
		return handle;
	}

    public void setLocationSpecifier(InstanceLocationSpecifier locationSpecifier) {
        this.locationSpecifier = locationSpecifier;
    }

    public ILocationSpecifier getLocationSpecifier() {
		return locationSpecifier;
	}

	public Message getRequestMessage() {
		if (this.type.equals("read"))
			return new ReadRequest(blockId, this.handle);
		else if (this.type.equals("write"))
			return new WriteRequest(blockId, this.handle, this.value);

		// global stats
		return new GlobalStatsRequest();

	}
}
