package org.moonlightcontroller.managers.models.messages;

import java.util.Map;

public class GlobalStatsResponse extends Message {
	private Map<String, Double> stats;
	
	// Default constructor to support Jersy
	public GlobalStatsResponse() {}
	
	
	public GlobalStatsResponse(int xid, Map<String, Double> stats) {
		super(xid);
		this.stats = stats;
	}
	
	public Map<String, Double> getStats() {
		return stats;
	}
}