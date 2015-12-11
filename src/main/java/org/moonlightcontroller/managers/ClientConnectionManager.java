package org.moonlightcontroller.managers;

import java.util.List;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.topology.InstanceLocationSpecifier;

public class ClientConnectionManager implements ISouthboundClient{
	
	private static ClientConnectionManager instance;
	
	private ClientConnectionManager() {
		
	}
	
	public synchronized static ClientConnectionManager getInstance() {
		if (instance == null) {
			instance = new ClientConnectionManager();
		}

		return instance;
	}

	@Override
	public void sendProcessingGraph(InstanceLocationSpecifier loc, List<IStatement> stmts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(ILocationSpecifier loc, IMessage msg, IRequestSender sender) {
		// TODO Auto-generated method stub
		
	}
	
}
