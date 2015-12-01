package org.moonlightcontroller.southbound.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.models.messages.HelloMessage;
import org.moonlightcontroller.managers.models.messages.IResponseMessage;
import org.moonlightcontroller.managers.models.messages.KeepAliveMessage;
import org.moonlightcontroller.managers.models.messages.SuccessMessage;

import com.sun.jersey.api.client.ClientResponse.Status;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hello(HelloMessage message) {
    	IResponseMessage result = ConnectionManager.getInstance().registerInstance(message);

    	return (result instanceof SuccessMessage)? Response.status(Status.OK).build() :
    		Response.status(Status.BAD_REQUEST).entity(result.toString()).build();
    }
    
    @GET
    @Path("KeepAlive")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response keepalive(KeepAliveMessage message) {
    	IResponseMessage result = ConnectionManager.getInstance().updateInstanceKeepAlive(message);
    	
    	return (result instanceof SuccessMessage)? Response.status(Status.OK).build() :
    		Response.status(Status.BAD_REQUEST).entity(result.toString()).build();
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
