package org.moonlightcontroller.southbound.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.moonlightcontroller.managers.ServerConnectionManager;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphResponse;
import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.KeepAlive;

@Path("/")
public class SouthboundApi {
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@POST
	@Path("message/Hello")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hello(Hello message) {
		return ServerConnectionManager.getInstance().handleHelloRequest(message);
	}

	@POST
	@Path("message/KeepAlive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response keepalive(KeepAlive message) {
		return ServerConnectionManager.getInstance().handleKeepaliveRequest(message);
	}

	@POST
	@Path("message/ListCapabilitiesResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String ListCapabilitiesResponse() {
		return "Test";
	}

	@POST
	@Path("message/SetParametersResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String SetParametersResponse() {
		return "Test";
	}

	@POST
	@Path("message/GetParametersResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String GetParametersResponse() {
		return "Test";
	}

	@POST
	@Path("message/GlobalStatsResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String GlobalStatsResponse() {
		return "Test";
	}

	@POST
	@Path("message/ReadResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String ReadResponse() {
		return "Test";
	}

	@POST
	@Path("message/WriteResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String WriteResponse() {
		return "Test";
	}

	@POST
	@Path("message/SetProcessingGraphResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SetProcessingGraphResponse(SetProcessingGraphResponse message) {
		return  ServerConnectionManager.getInstance().handleProcessingGraphResponse(message);
	}

	@POST
	@Path("message/Alert")
	@Consumes(MediaType.TEXT_PLAIN)
	public String Alert() {
		return "Test";
	}

	@POST
	@Path("message/Error")
	@Consumes(MediaType.TEXT_PLAIN)
	public String Error() {
		return "Test";
	}
}
