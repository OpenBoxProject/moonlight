package org.moonlightcontroller.managers;


import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;

public interface ISouthboundClient {
	void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender) throws InstanceNotAvailableException;
}
