package org.moonlightcontroller.events;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IHandleClient {
	// TODO: are handles represented as strings?
	void readHandle(ILocationSpecifier loc, String handle, String blockId, IRequestSender sender) throws InstanceNotAvailableException;
	
	void writeHandle(ILocationSpecifier loc, String blockId, String handle, String value,
			IRequestSender sender) throws InstanceNotAvailableException;
}
