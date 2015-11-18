package org.moonlightcontroller.southbound.client;

import java.util.List;

import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public interface ISouthboundClient {
	void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts);
}
