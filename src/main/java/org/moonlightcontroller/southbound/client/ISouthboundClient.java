package org.moonlightcontroller.southbound.client;

import java.util.List;

import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface ISouthboundClient {
	void sendProcessingGraph(ILocationSpecifier loc, List<IStatement> stmts);
}
