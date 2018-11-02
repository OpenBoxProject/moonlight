package org.openbox.dashboard;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.moonlightcontroller.exceptions.ApplicationsLoadException;
import org.moonlightcontroller.managers.ApplicationsManager;
import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.XidGenerator;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
	@Path("numApps")
	@Produces(MediaType.APPLICATION_JSON)
	public int getNumApps() {
		return NetworkInformationService.getInstance().getApps().size();
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
	@Path("activity.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Queue<Map<String, Object>> getNetworkActivity() {
		return SouthboundProfiler.getInstance().getMessages();
	}

	@GET
	@Path("performance.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Queue<Map<String, Object>> getPerformance() {
		return SouthboundProfiler.getInstance().getPerformanceStats();
	}

	@GET
	@Path("alerts.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Queue<Map<String, Object>> getAlerts() {
		return SouthboundProfiler.getInstance().getAlerts();
	}

	@POST
	@Path("message")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Integer> messageRequest(DashboardMessageRequest message) throws InstanceNotAvailableException {
		int xid = XidGenerator.generateXid();
		ConnectionManager.getInstance().sendMessage(
				xid,
				message.getLocationSpecifier(),
				message.getRequestMessage(),
				new DashboardRequestSender());
		Map<String, Integer> resp = new HashMap<>();
		resp.put("xid", xid);
		return resp;
	}

	private final static String APPS_REPOSITORY_DIRECTORY = "./apps-repo";
	private final static String DEPLOYED_APPS_DIRECTORY = "./apps";

	@GET
    @Path("listRepositoryApps")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listRepositoryApps() throws ApplicationsLoadException {
        return FileUtils.listFiles(new File(APPS_REPOSITORY_DIRECTORY), new String[]{"jar"}, false).stream().map(File::getName).sorted().collect(Collectors.toList());
    }

    @POST
    @Path("reloadApplications")
    public void reloadApplications() throws ApplicationsLoadException {
        ApplicationsManager.getInstance().updateApps();
    }

    @POST
    @Path("deployApplications")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deployApplications(List<String> apps) throws ApplicationsLoadException, IOException {
        File tempDir = null;
        try {
            tempDir = Files.createTempDirectory("apps").toFile();

            for (String app : apps) {
                File srcFile = new File(APPS_REPOSITORY_DIRECTORY + "/" + app);
                File dstFile = new File(tempDir + "/" + app);
                if (!srcFile.exists())
                    return Response.status(409).entity("Application not found in repository directory: " + app).build();

                FileUtils.copyFile(srcFile, dstFile);
            }

            FileUtils.listFiles(new File(DEPLOYED_APPS_DIRECTORY), new String[]{"jar"}, false).forEach(File::delete);
            FileUtils.copyDirectory(tempDir, new File(DEPLOYED_APPS_DIRECTORY));

            reloadApplications();

            return Response.status(200).build();

        } finally {
            try {
                FileUtils.deleteDirectory(tempDir);
            }
            catch (Exception ignore) {}
        }



    }

    @POST
    @Path("uploadApplication")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) throws IOException, ApplicationsLoadException {

        String uploadedFileLocation = "./apps-repo/" + fileDetail.getFileName();

        // save it
        FileUtils.copyInputStreamToFile(uploadedInputStream, new File(uploadedFileLocation));

        return Response.status(200).build();

    }
}
