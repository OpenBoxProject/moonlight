package org.moonlightcontroller.southbound.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.IOutMessage;

/**
 * Sends REST messages over a tcp connection.
 * This class knows how to set the right path for each message.
 */
public class SingleInstanceConnection implements ISingleInstanceConnection {

	private final String sourceAddr;
	private final String target;

	public SingleInstanceConnection(String targetServerHost, int targetServerPort, String host, int port){
		this.target = String.format("http://%s:%d/message", targetServerHost, targetServerPort);
		this.sourceAddr =  String.format("%s:%d", host, port);
	}

	@Override
	public void sendMessage(IMessage msg) {
		WebTarget webTarget = RestClient.getInstance().target(this.target).path(msg.getType());
		Invocation.Builder builder = webTarget.request(
				MediaType.APPLICATION_JSON_TYPE);
		msg.setSourceAddr(this.sourceAddr);

		Response resp = builder.post(Entity.entity((IOutMessage)msg, MediaType.APPLICATION_JSON_TYPE));
		if(resp.getStatus() != Status.OK.getStatusCode()){
			throw new RuntimeException("HTTP Error: "+ resp.getStatus());
		}
	}

	public String getTarget() {
		return target;
	}
}