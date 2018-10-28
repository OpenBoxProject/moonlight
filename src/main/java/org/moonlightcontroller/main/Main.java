package org.moonlightcontroller.main;

import java.io.IOException;

import org.moonlightcontroller.controller.MoonlightController;
import org.moonlightcontroller.managers.ApplicationsManager;
import org.moonlightcontroller.registry.ApplicationRegistry;
import org.moonlightcontroller.registry.IApplicationRegistry;
import org.moonlightcontroller.topology.ITopologyManager;
import org.moonlightcontroller.topology.TopologyManager;
import org.openbox.dashboard.DashboardServer;
import org.openbox.dashboard.websocket.WebSocketApplication;

public class Main {
	public static void main(String[] args) throws IOException {
		
		int server_port = 0;
		if (args.length > 0){
		    try {
		    	server_port = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[0] + " must be a vaild port number.");
		        System.exit(1);
		    }
		}

		IApplicationRegistry reg = new ApplicationRegistry();
		reg.loadFromPath("./apps");

		ITopologyManager topology = TopologyManager.getInstance();
		MoonlightController mc = new MoonlightController(topology, server_port);

        ApplicationsManager.getInstance().setMoonlightController(mc);

        WebSocketApplication.start();
        Thread t = new Thread(() -> {
            DashboardServer dashboardServer = new DashboardServer(3635);
            try {
                dashboardServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
		t.start();

		mc.start(reg);
		t.interrupt();
		return;
	}
}
