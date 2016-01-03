package org.openboxprotocol.types;

import java.io.IOException;

import org.openboxprotocol.exceptions.JSONParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class VlanPcp extends AbstractValueType<VlanPcp> {

	private int pcp;
	
	public static final VlanPcp EMPTY_MASK = new VlanPcp(0);
	
	private VlanPcp(int pcp) {
		this.pcp = pcp;
	}

	@Override
	public VlanPcp applyMask(VlanPcp mask) {
		// TODO Auto-generated method stub
		return null;
	}

	public static VlanPcp fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return this.pcp * 19;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof VlanPcp) && ((VlanPcp)other).pcp == this.pcp;
	}


	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.pcp);
	}
}
