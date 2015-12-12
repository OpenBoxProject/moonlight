package org.moonlightcontroller.southbound.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.models.messages.SetProcessingGraphResponse;
import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.KeepAlive;

@Path("/message/")
public class SouthboundApi {
	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@POST
	@Path("Hello")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hello(Hello message) {
		return ConnectionManager.getInstance().handleHelloRequest(message);
	}

	@POST
	@Path("KeepAlive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response keepalive(KeepAlive message) {
		return ConnectionManager.getInstance().handleKeepaliveRequest(message);
	}

	@POST
	@Path("ListCapabilitiesResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String ListCapabilitiesResponse() {
		return "Test";
	}

	@POST
	@Path("SetParametersResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String SetParametersResponse() {
		return "Test";
	}

	@POST
	@Path("GetParametersResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String GetParametersResponse() {
		return "Test";
	}

	@POST
	@Path("GlobalStatsResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String GlobalStatsResponse() {
		return "Test";
	}

	@POST
	@Path("ReadResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String ReadResponse() {
		return "Test";
	}

	@POST
	@Path("WriteResponse")
	@Consumes(MediaType.TEXT_PLAIN)
	public String WriteResponse() {
		return "Test";
	}

	@POST
	@Path("SetProcessingGraphResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SetProcessingGraphResponse(SetProcessingGraphResponse message) {
		return  ConnectionManager.getInstance().handleProcessingGraphResponse(message);
	}

	@POST
	@Path("Alert")
	@Consumes(MediaType.TEXT_PLAIN)
	public String Alert() {
		return "Test";
	}

	@POST
	@Path("Error")
	@Consumes(MediaType.TEXT_PLAIN)
	public String Error() {
		return "Test";
	}
}
