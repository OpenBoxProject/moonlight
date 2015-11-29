package org.openboxprotocol.protocol.topology;

import java.util.ArrayList;
import java.util.List;

public class Segment implements ILocationSpecifier {

	int xid;
	Segment[] segments;
	InstanceLocationSpecifier[] endpoints;
	
	public List<InstanceLocationSpecifier> getEndpoints(){
		List<InstanceLocationSpecifier> allObis = new ArrayList<>();
		return getSubEndpoints(this, allObis);
	}
	
	private List<InstanceLocationSpecifier> getSubEndpoints(Segment segment, List<InstanceLocationSpecifier> subList) {
		for (InstanceLocationSpecifier obi : segment.endpoints) {
			subList.add(obi);
		}
		
		for (Segment subSegment: segment.segments) {
			getSubEndpoints(subSegment, subList);
		}
		
		return subList;
	}

	@Override
	public boolean isSingleLocation() {
		return false;
	}

	@Override
	public int getId() {
		return xid;
	}
	
}
