package org.moonlightcontroller.southbound.client;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.ReadRequest;
import org.moonlightcontroller.managers.models.messages.WriteRequest;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public class HandleClient implements IHandleClient {

	private ISouthboundClient client;
	
	public HandleClient(ISouthboundClient client) {
		this.client = client;
	}

	@Override
	public void readHandle(ILocationSpecifier loc, String blockId, String handle,
			IRequestSender sender) {
		this.client.sendMessage(loc, new ReadRequest(blockId, handle), sender);
	}

	@Override
	public void writeHandle(ILocationSpecifier loc, String blockId, String handle, String value,
			IRequestSender sender) {
		this.client.sendMessage(loc, new WriteRequest(blockId, handle, value), sender);
	}
}
