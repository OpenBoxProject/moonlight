package org.moonlightcontroller.southbound.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;

import com.google.common.net.InetAddresses;

/**
 * Sends REST messages over a tcp connection.
 * This class knows how to set the right path for each message.
 */
public class SingleInstanceConnection implements ISingleInstanceConnection {

	private String target;
		
	public SingleInstanceConnection(String ip, int port){
		this.target = String.format("http://%s:%d/message", ip, port);		
	}

	@Override
	public void sendMessage(IMessage msg) {
		WebTarget webTarget = RestClient.getInstance().target(this.target).path(msg.getType());
		Invocation.Builder builder = webTarget.request(
				MediaType.APPLICATION_JSON_TYPE);
		Response resp = builder.post(Entity.entity(msg, MediaType.APPLICATION_JSON_TYPE));
		if(resp.getStatus() != Status.OK.getStatusCode()){
			throw new RuntimeException("HTTP Error: "+ resp.getStatus());
		}
	}
}