package org.openboxprotocol.types;

import java.io.IOException;

import org.openboxprotocol.exceptions.JSONParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class IpDscp extends AbstractValueType<IpDscp> {

	private int ipDscp;
	
	public static final IpDscp EMPTY_MASK = new IpDscp(0x0);

	private IpDscp(int ipDscp) {
		this.ipDscp = ipDscp;
	}
	
	public int getValue() {
		return ipDscp;
	}
	
	@Override
	public IpDscp applyMask(IpDscp mask) {
		return IpDscp.of(this.ipDscp & mask.ipDscp);
	}
	
	@Override
	public int hashCode() {
		return this.ipDscp * 7;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof IpDscp) && ((IpDscp)other).ipDscp == this.ipDscp;
	}
	
	public static IpDscp of(int ipDscp) {
		return new IpDscp(ipDscp);
	}

	public static IpDscp fromJson(Object json) throws JSONParseException {
		if (!(json instanceof Integer))
			throw new JSONParseException("IP DSCP must be an integer");
		return new IpDscp((Integer)json);
	}

	@Override
	public String toString() {
		return this.ipDscp + "";
	}

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.ipDscp);
	}
	
	@Override
	public String toJson() {
		return this.ipDscp + "";
	}
}
