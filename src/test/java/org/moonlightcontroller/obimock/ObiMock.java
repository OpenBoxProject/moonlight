package org.moonlightcontroller.obimock;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.moonlightcontroller.managers.models.messages.KeepAlive;
import org.moonlightcontroller.managers.models.messages.Message;
import org.moonlightcontroller.southbound.client.SingleInstanceConnection;

public class ObiMock {

	private static ObiMock instance;
    private final int obiPort;

    private long dpid;
	private int xid;
	private Server jetty;
	private SingleInstanceConnection client;

	private final static int KEEP_ALIVE_INTERVAL_SECONDS = 10;

	public static ObiMock getInstance() {
		return instance;
	}
	
	public SingleInstanceConnection getClient() {
		return this.client;
	}
	
	public long getdpid() {
		return this.dpid;
	}
	
	public ObiMock(int dpid, String southboundServerIp, int southboundServerPort, String obiHost, int obiPort) {
		this.dpid = dpid;
		this.client = new SingleInstanceConnection(southboundServerIp, southboundServerPort, obiHost, obiPort);
		this.obiPort = obiPort;
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

		Server jettyServer = new Server(obiPort);
		jettyServer.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
		jerseyServlet.setInitOrder(0);

		jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", ObiMockApi.class.getCanonicalName());

		this.jetty = jettyServer;
		try {
			this.jetty.start();
			try {
                ObiMockApi.sayHello();
            } catch (Exception e) {
                System.out.println(e);
            }

            Thread keepAliveThread = new Thread(()->{
                while (true) {
                    System.out.println("KeepAlive Loop");
                    try {
                        Thread.sleep(KEEP_ALIVE_INTERVAL_SECONDS * 1000);
                        Message msg = new KeepAlive();
                        msg.setDpid(dpid);
                        this.client.sendMessage(msg);
                    } catch (InterruptedException e) {
                        System.out.println("Error while attempting to send keepalive");
                        System.out.println(e.getStackTrace());
                    }
                }
            });
            keepAliveThread.start();

            this.jetty.join();
            keepAliveThread.interrupt();
		} finally {
			this.jetty.destroy();
		}
	}

	public static void main(String[] args) {
	    try {
            String listeningHost = args[0];
            int listeningPort = Integer.valueOf(args[1]);
            String obiHost = args[2];
            int obiPort = Integer.valueOf(args[3]);
            int dpid = Integer.valueOf(args[4]);
            ObiMock obi = new ObiMock(dpid, listeningHost, listeningPort, obiHost, obiPort);

            try {
                obi.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch(Exception e) {
            System.out.println("Usage: <listening_host> <listening_port> <obi_host> <obi_port> <dpid>");
        }
	}
}
