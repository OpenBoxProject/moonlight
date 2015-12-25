package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class GlobalStatsResponse implements IMessage {
	private String type;
	private int xid;
	private Map<String, List<Double>> stats;
	
	// Default constructor to support Jersy
	public GlobalStatsResponse() {}
	
	
	public GlobalStatsResponse(int xid, Map<String, List<Double>> stats) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.stats = stats;
	}
	
	public String getType() {
		return type;
	}

	public int getXid() {
		return xid;
	}
	
	@Override
	public void setXid(int xid) {
		this.xid = xid;
	}

	public Map<String, List<Double>> getStats() {
		return stats;
	}
}