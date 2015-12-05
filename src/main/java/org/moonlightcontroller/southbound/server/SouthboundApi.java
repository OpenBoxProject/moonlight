package org.moonlightcontroller.southbound.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.managers.ServerConnectionManager;
import org.moonlightcontroller.managers.models.messages.AcknowledgeMessage;
import org.moonlightcontroller.managers.models.messages.HelloMessage;
import org.moonlightcontroller.managers.models.messages.IResponseMessage;
import org.moonlightcontroller.managers.models.messages.KeepAliveMessage;
import org.moonlightcontroller.managers.models.messages.SuccessMessage;

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
  public Response hello(HelloMessage message) {
  	IResponseMessage result = ServerConnectionManager.getInstance().handleHelloRequest(message);

  	return (result instanceof SuccessMessage)? Response.status(Status.OK).build() :
  		Response.status(Status.BAD_REQUEST).entity(result.toString()).build();
  }
  
  @POST
  @Path("message/KeepAlive")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response keepalive(KeepAliveMessage message) {
  	IResponseMessage result = ServerConnectionManager.getInstance().handleKeepaliveRequest(message);
  	
  	return (result instanceof SuccessMessage)? Response.status(Status.OK).build() :
  		Response.status(Status.BAD_REQUEST).entity(result.toString()).build();
  }
    
    @GET
    @Path("message/ListCapabilitiesResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String ListCapabilitiesResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/SetParametersResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String SetParametersResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/GetParametersResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String GetParametersResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/GlobalStatsResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String GlobalStatsResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/ReadResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String ReadResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/WriteResponse")
    @Consumes(MediaType.TEXT_PLAIN)
    public String WriteResponse() {
        return "Test";
    }
    
    @GET
    @Path("message/SetProcessingGraphResponse")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SetProcessingGraphResponse(AcknowledgeMessage message) {
    	IResponseMessage result = ServerConnectionManager.getInstance().handleProcessingGraphResponse(message);
    	return (result instanceof SuccessMessage)? Response.status(Status.OK).build() :
      		Response.status(Status.BAD_REQUEST).entity(result.toString()).build();
    }
    
    @GET
    @Path("message/Alert")
    @Consumes(MediaType.TEXT_PLAIN)
    public String Alert() {
        return "Test";
    }
    
    @GET
    @Path("message/Error")
    @Consumes(MediaType.TEXT_PLAIN)
    public String Error() {
        return "Test";
    }
}
