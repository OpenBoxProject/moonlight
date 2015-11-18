package org.openboxprotocol.protocol.topology;

import java.util.ArrayList;
import java.util.List;

public class Segment implements ILocationSpecifier {

	String id;
	List<Segment> segments;
	List<InstanceLocationSpecifier> endpoints;
	
	public Segment(String id) {
		this.id = id;
		segments = new ArrayList<>();
		endpoints = new ArrayList<>();
	}
	
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
	
}
