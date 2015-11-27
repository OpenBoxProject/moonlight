package org.openboxprotocol.protocol.topology;

import java.net.InetAddress;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.net.InetAddresses;

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

	@Override
	public boolean isMatch(String m) {		
		if (InetAddresses.isInetAddress(m)){
			InetAddress addr = InetAddresses.forString(m);
			int address = InetAddresses.coerceToInteger(addr);
			if (address == this.ip){
				return true;
			}
		}
		if (this.id.equals(m)){
			return true;
		}
		return false;
	}

	@Override
	public ILocationSpecifier findChild(String m) {
		if (this.isMatch(m)) {
			return this;
		}
		return null;
	}
}
