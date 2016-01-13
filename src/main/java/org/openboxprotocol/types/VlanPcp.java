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
		return new VlanPcp(this.pcp & mask.pcp);
	}

	public static VlanPcp fromJson(Object json) throws JSONParseException {
		if (json instanceof Long) {
			return new VlanPcp(((Long)json).intValue());
		} else if (json instanceof Integer) {
			return new VlanPcp((Integer)json);
		}
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
	public String toString() {
		return this.pcp + "";
	}

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.pcp);
	}

	@Override
	public String toJson() {
		return this.pcp + "";
	}
	
}
