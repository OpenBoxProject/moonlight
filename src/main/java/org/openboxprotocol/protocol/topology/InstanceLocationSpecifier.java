package org.openboxprotocol.protocol.topology;


import org.apache.commons.lang.builder.HashCodeBuilder;

public class InstanceLocationSpecifier implements ILocationSpecifier {

	int xid;
	long dpid;
	
	public InstanceLocationSpecifier(int id, long ip) {
		this.xid = id;
		this.dpid = ip;
	}

	@Override
	public boolean isSingleLocation() {
		return true;
	}

	@Override
	public int getId() {
		return this.xid;
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
		if (other.xid == this.xid && other.dpid == this.dpid){
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		return new HashCodeBuilder(17, 31).append(this.xid).append(this.dpid).toHashCode();
	}
}
