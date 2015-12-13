package org.moonlightcontroller.southbound.client;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.moonlightcontroller.managers.models.messages.IMessage;

import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

import com.google.common.net.InetAddresses;

public class SingleInstanceConnection implements ISingleInstanceConnection {

	private String target;
	
	public SingleInstanceConnection(InstanceLocationSpecifier spec) {
		// TODO: The cast to int might be problematic in case of ipv6
		String ipAsStr = InetAddresses.fromInteger((int)spec.getIp()).toString();
		this.target = String.format("http:/%s:3636/message", ipAsStr);
	}

	public SingleInstanceConnection(int ip, int port) {
		// TODO: The cast to int might be problematic in case of ipv6
		String ipAsStr = InetAddresses.fromInteger(ip).toString();
		this.target = String.format("http:/%s:%d/message", ipAsStr, port);
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