package org.openbox.dashboard;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class DashboardServer {

	private final int port;
    private Server jetty;

	public DashboardServer(int serverPort) {
		this.port = serverPort;
	}

	public void start() throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addFilter(CORSFilter.class, "*", EnumSet.of(DispatcherType.REQUEST));

		Server jettyServer = new Server(this.port);
		jettyServer.setHandler(context);

        ResourceConfig resourceConfig = new ResourceConfig()
                .packages(DashboardServerApi.class.getPackage().getName())
                .register(MultiPartFeature.class);

		ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(resourceConfig));
		jerseyServlet.setInitOrder(0);
		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", DashboardServerApi.class.getCanonicalName());
        context.addServlet(jerseyServlet, "/*");

		this.jetty = jettyServer;
		try {
			this.jetty.start();
			this.jetty.join();
		} finally {
			this.jetty.destroy();
		}
	}

}
