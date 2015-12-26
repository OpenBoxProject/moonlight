package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import java.util.Map;

public class GlobalStatsResponse extends Message {
	private Map<String, List<Double>> stats;
	
	// Default constructor to support Jersy
	public GlobalStatsResponse() {}
	
	
	public GlobalStatsResponse(int xid, Map<String, List<Double>> stats) {
		super(xid);
		this.stats = stats;
	}
	
	public Map<String, List<Double>> getStats() {
		return stats;
	}
}