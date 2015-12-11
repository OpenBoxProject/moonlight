package org.moonlightcontroller.southbound.client;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface ISouthboundClient {
	void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender);
}
