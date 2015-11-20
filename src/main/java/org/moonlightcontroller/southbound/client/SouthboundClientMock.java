package org.moonlightcontroller.southbound.client;

import java.util.List;

import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class SouthboundClientMock implements ISouthboundClient{
	
	public void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts){
		System.out.println("Sending statements to location" + loc.getId());
	}

}
