package org.moonlightcontroller.topology;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class Segment implements ILocationSpecifier {

	long id;
	List<Segment> segments;
	List<InstanceLocationSpecifier> endpoints;
	
	public Segment(){	
	}
	
	public Segment(long id){
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
	public long getId() {
		return id;
	}

	@Override
	public boolean isMatch(long m) {
		return this.id == m;
	}

	@Override
	public ILocationSpecifier findChild(long m) {
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
		if (other.id == this.id){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(27, 31).append(this.id).toHashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ Segment ID: ").append(this.id).append(", Endpoints: [");
		this.endpoints.forEach(ep -> sb.append((ep == null ? "" : ep.toString() + ",")));
		if (sb.charAt(sb.length() - 1) == ',')
			sb.deleteCharAt(sb.length() - 1);
		sb.append("], Segments: [");
		this.segments.forEach(s -> sb.append((s == null ? "" : s.toString() + ",")));
		if (sb.charAt(sb.length() - 1) == ',')
			sb.deleteCharAt(sb.length() - 1);
		sb.append("] ]");
		return sb.toString();
	}
}