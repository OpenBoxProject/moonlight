package org.moonlightcontroller.obimock;

import java.util.*;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.blocks.ObiType;
import org.moonlightcontroller.managers.models.messages.*;

@Path("/message/")
public class ObiMockApi {

	private final static Logger LOG = Logger.getLogger(ObiMockApi.class.getName());
	private int measure = 10;

    @GET
	@Path("Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "test";
	}

    public static void sayHello() {
        HashMap<String, List<String>> caps = new HashMap<>();
        caps.put("Caps1", Arrays.asList("cap1_1", "cap1_2"));
        int xid = ObiMock.getInstance().fetchAndIncxid();
        Hello hello = new Hello(xid, ObiMock.getInstance().getdpid(), "1.0", caps, ObiType.ClickObi);
        sendMessage(hello);
    }

	@POST
	@Path("SayHello")
	@Produces(MediaType.TEXT_PLAIN)
	public void sayHelloRequest() {
		sayHello();
	}

	@POST
	@Path("SendAlert")
	@Produces(MediaType.TEXT_PLAIN)
	public void sendAlert() {
		int xid = ObiMock.getInstance().fetchAndIncxid();
		List<AlertMessage> alerts = new ArrayList<>();
		alerts.add(new AlertMessage(1, System.currentTimeMillis(), "Alert from mock OBI", 1, "packet", "BasicFirewall.Alert"));
		Alert alertMessage = new Alert(xid, ObiMock.getInstance().getdpid(), alerts);
		sendMessage(alertMessage);
	}
	
	@POST
	@Path("SetProcessingGraphRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setProcessingGraph(SetProcessingGraphRequest message) {
		if (message == null) {
			LOG.info("Got a processing graph NULL");	
		} else {
			LOG.info("Got a processing graph " + message.getXid());
		}
		
		SetProcessingGraphResponse msg = new SetProcessingGraphResponse(message.getXid());
		new Thread(()-> sendMessage(msg)).start();
		
		return Response.status(Status.OK).build();
	}

	@POST
	@Path("ReadRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response readRequest(ReadRequest message) {
		LOG.info("received a read request" + message.toString());
		measure += (int) (Math.random() * 20);
		ReadResponse rr = new ReadResponse(message.getXid(), message.getBlockId(), message.getReadHandle(), String.valueOf(measure));
		new Thread(()-> sendMessage(rr)).start();
		return Response.status(Status.OK).build();
	}

	@POST
	@Path("WriteRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response writeRequest(WriteRequest message) {
		LOG.info("received a write request" + message.toString());
		WriteResponse rr = new WriteResponse(message.getXid(), message.getBlockId(), message.getWriteHandle());
		new Thread(()-> sendMessage(rr)).start();
		return Response.status(Status.OK).build();
	}

	private static void sendMessage(IMessage msg) {
			ObiMock.getInstance().getClient().sendMessage(msg);
	}

    private static MockMeasure core1 = new MockMeasure("core1");
    private static MockMeasure core2 = new MockMeasure("core2");
    private static MockMeasure memVMS = new MockMeasure("memVMS");
    private static MockMeasure memRSS = new MockMeasure("memRSS");
    private static MockMeasure memUsage = new MockMeasure("memUsage");

    synchronized private List<Double> getCPUByCore() {

        List<Double> cpuByCore = new ArrayList<>();
        cpuByCore.add(core1.next());
        cpuByCore.add(core2.next());

        return cpuByCore;

    }

    synchronized private double getMemVMS() {
        return memVMS.next();
    }

    synchronized private double getMemRSS() {
        return memRSS.next();
    }

    synchronized private double getMemUsage() {
        return memUsage.next();
    }

	@POST
	@Path("GlobalStatsRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response globalStatsRequest(GlobalStatsRequest message) {
		LOG.info("received a read request" + message.toString());
		Map<String, Object> mock = new HashMap<>();

		// simulating multiple cpu

		mock.put("cpu", getCPUByCore());
		mock.put("memory_rss", getMemRSS());
		mock.put("memory_vms", getMemVMS());
		mock.put("memory_usage", getMemUsage());
        System.out.println("MEMORY RSS " + memRSS);
        GlobalStatsResponse resp = new GlobalStatsResponse(message.getXid(), mock);
		new Thread(()-> this.sendMessage(resp)).start();
		return Response.status(Status.OK).build();
	}

}
