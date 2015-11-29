package org.openboxprotocol.protocol.topology;

import org.junit.Test;

public class TestTopologyManager {
	@Test
	public void testTopology() {
		TopologyManager manager = TopologyManager.getInstance();
		manager.getAllEndpoints();
	}
	
//	@Test
//	public void testTopology1() {
//		new TopologyManager("C:\\dev\\topology.json");
//	}
}
