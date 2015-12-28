package org.openboxprotocol.protocol.topology;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Segment implements ILocationSpecifier {

	String id;
	List<Segment> segments;
	List<InstanceLocationSpecifier> endpoints;
	
	public Segment(){	
	}
	
	public Segment(String id){
		this.id = id;
		this.segments = new ArrayList<>();
		this.endpoints = new ArrayList<>();
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

	public List<? extends ILocationSpecifier> getDirectSegments() {
		return this.segments;
	}

	public List<? extends ILocationSpecifier> getDirectEndpoints() {
		return this.endpoints;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Segment)){
			return false;
		}
		if (obj == this){
			return true;
		}
		Segment other = (Segment)obj;
		if (other.id.equals(this.id)){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(27, 31).append(this.id).toHashCode();
	}
}