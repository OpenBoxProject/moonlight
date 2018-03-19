package org.moonlightcontroller.southbound.server;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.models.messages.*;
import org.moonlightcontroller.managers.models.messages.Error;
import org.openbox.dashboard.SouthboundProfiler;

/**
 * This class has all listeners for incoming messages from OBIs
 * Once a message is received, it invokes the correct method in ConnectionManager.
 *
 */
@Path("/message/")
public class SouthboundApi {
	private static final Boolean direction = true;

	@GET
	@Path("test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Test";
	}

	@POST
	@Path("Hello")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hello(@Context HttpServletRequest request, Hello message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleHelloRequest(message);
	}

	@POST
	@Path("KeepAlive")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response keepalive(KeepAlive message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleKeepaliveRequest(message);
	}
	
	@POST
	@Path("ReadResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ReadResponse(ReadResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleResponse(message);
	}
	
	@POST
	@Path("Error")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Error(Error message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleErrorMessage(message);
	}

	@POST
	@Path("ListCapabilitiesResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ListCapabilitiesResponse(ListCapabilitiesResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleListCapabilitiesResponse(message);
	}

	@POST
	@Path("SetParametersResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SetParametersResponse(SetParametersResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleResponse(message);
	}

	@POST
	@Path("GetParametersResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response GetParametersResponse(GetParametersResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleResponse(message);
	}

	@POST
	@Path("GlobalStatsResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response GlobalStatsResponse(GlobalStatsResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleResponse(message);
	}

	@POST
	@Path("WriteResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response WriteResponse(WriteResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleResponse(message);
	}

	@POST
	@Path("SetProcessingGraphResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response SetProcessingGraphResponse(SetProcessingGraphResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleSetProcessingGraphResponse(message);
	}

	@POST
	@Path("Alert")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alert(Alert message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleAlert(message);
	}
	
	@POST
	@Path("AddCustomModuleResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddCustomModuleResponse(AddCustomModuleResponse message) {
		SouthboundProfiler.getInstance().onMessage(message, direction);
		return ConnectionManager.getInstance().handleAddCustomModuleResponse(message);
	}
}
