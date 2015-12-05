package org.moonlightcontroller.southbound.client;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IHandleClient {
	// TODO: are handles represented as strings?
	void readHandle(ILocationSpecifier loc, String handle, String blockId, IRequestSender sender);
	
	void writeHandle(ILocationSpecifier loc, String blockId, String handle, String value,
			IRequestSender sender);
}
