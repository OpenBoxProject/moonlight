package org.moonlightcontroller.southbound.client;

import java.util.List;

import org.moonlightcontroller.managers.ISouthboundClient;
import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.openboxprotocol.protocol.IStatement;

public class SouthboundClientMock implements ISouthboundClient{
	
	public void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts){
		System.out.println("Sending statements to location" + loc.getId());
	}

	@Override
	public void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender) {
		// TODO Auto-generated method stub
		
	}

}
