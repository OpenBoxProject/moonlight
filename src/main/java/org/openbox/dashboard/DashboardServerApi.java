package org.openbox.dashboard;

import org.moonlightcontroller.events.HandleClient;
import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.models.messages.AdminReadRequest;
import org.moonlightcontroller.managers.models.messages.ReadRequest;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

@Path("/")
public class DashboardServerApi {

	private final static Logger LOG = Logger.getLogger(DashboardServerApi.class.getName());
	
	@GET
	@Path("ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "pong";
	}

	@GET
	@Path("topology.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, List> geTopology() {
		return NetworkInformationService.getInstance().getTopologyGraph();
	}

	@GET
	@Path("obi.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getOBI(@QueryParam("dpid") Long dpid) {
		return SouthboundProfiler.getInstance().getObi(dpid);
	}

	@GET
	@Path("apps.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map> getApps() {
		return NetworkInformationService.getInstance().getApps();
	}

	@GET
	@Path("aggregated.json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map> getAggregated() {
		return NetworkInformationService.getInstance().getAggregated();
	}

	@GET
	@Path("network/log.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Queue<Map<String, Object>> getNetworkLog() {
		return SouthboundProfiler.getInstance().getMessages();
	}

	@POST
	@Path("message/read")
	@Consumes(MediaType.APPLICATION_JSON)
	public void read(AdminReadRequest message) throws InstanceNotAvailableException {
		ConnectionManager.getInstance().sendMessage(
				message.getLocationSpecifier(),
				message.getReadRequest(),
				new DashboardRequestSender());
	}


}
