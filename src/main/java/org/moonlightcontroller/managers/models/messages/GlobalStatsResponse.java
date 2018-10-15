package org.moonlightcontroller.managers.models.messages;

import java.util.Map;

public class GlobalStatsResponse extends Message {
	private Map<String, Object> stats;
	
	// Default constructor to support Jersy
	public GlobalStatsResponse() {}
	
	
	public GlobalStatsResponse(int xid, Map<String, Object> stats) {
		super(xid);
		this.stats = stats;
	}
	
	public Map<String, Object> getStats() {
		return stats;
	}
}