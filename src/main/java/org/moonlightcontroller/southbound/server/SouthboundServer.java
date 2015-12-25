package org.moonlightcontroller.southbound.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SouthboundServer implements ISouthboundServer {

	private Server jetty;

	public SouthboundServer() {
	       ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        context.setContextPath("/");
	 
	        Server jettyServer = new Server(3636);
	        jettyServer.setHandler(context);
	 
	        ServletHolder jerseyServlet = 
	        		context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
	        jerseyServlet.setInitOrder(0);
	 
	        jerseyServlet.setInitParameter(
	           "jersey.config.server.provider.classnames",
	           SouthboundApi.class.getCanonicalName());
	        
	        this.jetty = jettyServer;
	}

	@Override
	public void start() throws Exception {
        try {
            this.jetty.start();
            this.jetty.join();
        } catch(Exception e) {
        	System.out.println(e.toString());
        }finally {
            this.jetty.destroy();
        }
	}
}
