package org.moonlightcontroller.topology;

import java.net.InetAddress;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.net.InetAddresses;

public class InstanceLocationSpecifier implements ILocationSpecifier {

	long id;

	public InstanceLocationSpecifier(long id) {
		this.id = id;
	}

	@Override
	public boolean isSingleLocation() {
		return true;
	}

	@Override
	public long getId() {
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
		if (other.id == this.id){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder(17, 31).append(this.id).toHashCode();
	}
	
	@Override
	public String toString(){
		return this.id + "";
	}

	@Override
	public boolean isMatch(long m) {		
		if (this.id == m){
			return true;
		}
		return false;
	}

	@Override
	public ILocationSpecifier findChild(long m) {
		if (this.isMatch(m)) {
			return this;
		}
		return null;
	}
}