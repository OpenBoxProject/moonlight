package org.moonlightcontroller.southbound.client;

import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface IHandleClient {
	// TODO: are handles represented as strings?
	// TODO: what should be returned here
	void readHandle(ILocationSpecifier loc, String handle);
	
	void writeHandle(ILocationSpecifier loc, String handle);
}
