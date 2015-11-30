package org.moonlightcontroller.southbound.server;

import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.moonlightcontroller.managers.ConnectionManager;

@Path("/")
public class SouthboundApi {
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Test";
    }
    
    @POST
    @Path("Hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("xid") int xid, @QueryParam("dpid") int dpid, @QueryParam("version") String version, @QueryParam("capabilities") Map<String, List<String>> capabilities) {
    	boolean success = ConnectionManager.getInstance().registerInstance(xid, dpid, version, capabilities);
    	return success? "Successful" : "Failed";
    }
    
    @GET
    @Path("KeepAlive")
    @Produces(MediaType.TEXT_PLAIN)
    public String keepalive(@QueryParam("xid") int xid, @QueryParam("dpid") int dpid) {
    	boolean success = ConnectionManager.getInstance().updateInstanceKeepAlive(xid, dpid);
    	
        return success? "Successful" : "Failed";
    }
    
    @GET
    @Path("ListCapabilitiesResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String ListCapabilitiesResponse() {
        return "Test";
    }
    
    @GET
    @Path("SetParametersResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String SetParametersResponse() {
        return "Test";
    }
    
    @GET
    @Path("GetParametersResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String GetParametersResponse() {
        return "Test";
    }
    
    @GET
    @Path("GlobalStatsResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String GlobalStatsResponse() {
        return "Test";
    }
    
    @GET
    @Path("ReadResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String ReadResponse() {
        return "Test";
    }
    
    @GET
    @Path("WriteResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String WriteResponse() {
        return "Test";
    }
    
    @GET
    @Path("SetProcessingGraphResponse")
    @Produces(MediaType.TEXT_PLAIN)
    public String SetProcessingGraphResponse() {
        return "Test";
    }
    
    @GET
    @Path("Alert")
    @Produces(MediaType.TEXT_PLAIN)
    public String Alert() {
        return "Test";
    }
    
    @GET
    @Path("Error")
    @Produces(MediaType.TEXT_PLAIN)
    public String Error() {
        return "Test";
    }
}
