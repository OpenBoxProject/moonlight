package org.moonlightcontroller.obimock;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.moonlightcontroller.southbound.client.SingleInstanceConnection;

public class ObiMock {

	private static ObiMock instance;

	private int obiIp;
	private int xid;
	private Server jetty;
	private SingleInstanceConnection client;

	public static ObiMock getInstance() {
		return instance;
	}
	
	public SingleInstanceConnection getClient() {
		return this.client;
	}
	
	public int getObiIp() {
		return this.obiIp;
	}
	
	public ObiMock(int obiIp, int serverIp, int serverPort) {
		this.obiIp = obiIp;
		this.client = new SingleInstanceConnection(serverIp, serverPort);
		instance = this;
	}
	
	public int fetchAndIncxid(){
		int ret = this.xid;
		this.xid++;
		return ret;
	}

	public void start() throws Exception {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");

		Server jettyServer = new Server(3636);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", ObiMockApi.class.getCanonicalName());

		this.jetty = jettyServer;
		try {
			this.jetty.start();
			this.jetty.join();
		} finally {
			this.jetty.destroy();
		}
	}

	public static void main(String[] args) {
		ObiMock obi = new ObiMock(2130706433, 2130706433, 3637);
		try {
			obi.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
