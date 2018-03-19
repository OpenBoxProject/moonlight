package org.openbox.dashboard;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class DashboardServer {

	private final int port;

	private Server jetty;

	public DashboardServer(int serverPort) {
		this.port = serverPort;
	}

	private void start() throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addFilter(CORSFilter.class, "*", EnumSet.of(DispatcherType.REQUEST));

		Server jettyServer = new Server(this.port);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", DashboardServerApi.class.getCanonicalName());

		this.jetty = jettyServer;
		try {
			this.jetty.start();
			this.jetty.join();
		} finally {
			this.jetty.destroy();
		}
	}

	public static void start(int port) {
		DashboardServer server = new DashboardServer(port);
		System.out.printf("dashboard server listening on port %s\n", port);

		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
