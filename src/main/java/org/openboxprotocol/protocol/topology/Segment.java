package org.openboxprotocol.protocol.topology;

import java.util.ArrayList;
import java.util.List;

public class Segment implements ILocationSpecifier {

	String id;
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
	public String getId() {
		return id;
	}

	@Override
	public boolean isMatch(String m) {
		return this.id.equals(m);
	}

	@Override
	public ILocationSpecifier findChild(String m) {
		if (this.isMatch(m)){
			return this;
		}
		
		for (InstanceLocationSpecifier ep : this.endpoints){
			if (ep.findChild(m) != null) {
				return ep;
			}
		}
		for (Segment seg : this.segments){
			ILocationSpecifier loc = seg.findChild(m);
			if (loc != null){
				return loc;
			}
		}
		return null;
	}
}