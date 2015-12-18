package org.moonlightcontroller.main;

import java.io.IOException;

import org.moonlightcontroller.controller.MoonlightController;
import org.moonlightcontroller.registry.ApplicationRegistry;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.southbound.client.SouthboundClientMock;
import org.openboxprotocol.protocol.topology.ITopologyManager;
import org.openboxprotocol.protocol.topology.TopologyManager;

public class Main {
	public static void main(String[] args) throws IOException {
		IApplicationRegistry reg = new ApplicationRegistry();
		reg.loadFromPath("/home/tilon/workspace/input/apps/");
		
		ITopologyManager topology = TopologyManager.getInstance();
		SouthboundClientMock sclient = new SouthboundClientMock();
		MoonlightController mc = new MoonlightController(reg, topology, sclient);
		mc.start();
		return;
	}
}
