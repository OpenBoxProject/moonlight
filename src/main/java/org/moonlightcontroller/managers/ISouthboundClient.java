package org.moonlightcontroller.managers;


import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface ISouthboundClient {
	void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender) throws InstanceNotAvailableException;
}
