package org.moonlightcontroller.obimock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.ReadRequest;
import org.moonlightcontroller.managers.models.messages.ReadResponse;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphRequest;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphResponse;

@Path("/message/")
public class ObiMockApi {

	private final static Logger LOG = Logger.getLogger(ObiMockApi.class.getName());
	
	@GET
	@Path("Test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "test";
	}

	@POST
	@Path("SayHello")
	@Produces(MediaType.TEXT_PLAIN)
	public void sayhello() {
		HashMap<String, List<String>> caps = new HashMap<>();
		caps.put("Caps1", Arrays.asList("cap1_1", "cap1_2"));
		int xid = ObiMock.getInstance().fetchAndIncxid();
		Hello hello = new Hello(xid, ObiMock.getInstance().getObiIp(), "1.0", caps);
		this.sendMessage(hello);
		
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
		new Thread(()-> this.sendMessage(msg)).start();
		
		return Response.status(Status.OK).build();
	}

	@POST
	@Path("ReadRequest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response readRequest(ReadRequest message) {
		LOG.info("Got a a read request" + message.toString());
		ReadResponse rr = new ReadResponse(message.getXid(), message.getBlockId(), message.getReadHandle(), "100");
		new Thread(()-> this.sendMessage(rr)).start();
		return Response.status(Status.OK).build();
	}

	private void sendMessage(IMessage msg) {
		ObiMock.getInstance().getClient().sendMessage(msg);
	}
}
