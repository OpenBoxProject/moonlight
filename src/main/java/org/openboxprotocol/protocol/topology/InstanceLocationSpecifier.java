package org.openboxprotocol.protocol.topology;

import org.openboxprotocol.types.IPv4Address;

public class InstanceLocationSpecifier implements ILocationSpecifier {

	String id;
	long ip;
	
	public InstanceLocationSpecifier(String id, long ip) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSingleLocation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
