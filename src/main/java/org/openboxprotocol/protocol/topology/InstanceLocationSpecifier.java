package org.openboxprotocol.protocol.topology;

import org.apache.commons.lang.builder.HashCodeBuilder;

public class InstanceLocationSpecifier implements ILocationSpecifier {

	String id;
	long ip;
	
	public InstanceLocationSpecifier(String id, long ip) {
		this.id = id;
		this.ip = ip;
	}

	@Override
	public boolean isSingleLocation() {
		return true;
	}

	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof InstanceLocationSpecifier)){
			return false;
		}
		if (obj == this){
			return true;
		}
		InstanceLocationSpecifier other = (InstanceLocationSpecifier)obj;
		if (other.id.equals(this.id) && other.ip == this.ip){
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		return new HashCodeBuilder(17, 31).append(this.id).append(this.ip).toHashCode();
	}
}
