package org.moonlightcontroller.managers;

import java.util.List;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public interface ISouthboundClient {
	void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts);
	void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender);
}
