package org.moonlightcontroller.main;

import java.io.IOException;

import org.moonlightcontroller.registry.ApplicationRegistry;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.openboxprotocol.protocol.topology.ITopologyManager;

public class Main {
	public static void main(String[] args) throws IOException {
		IApplicationRegistry reg = new ApplicationRegistry();
		reg.loadFromPath("/home/tilon/workspace/MyApp/apps/");
		
		ITopologyManager topology;
		return;
	}
}
