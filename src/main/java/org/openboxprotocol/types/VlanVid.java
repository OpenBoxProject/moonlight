package org.openboxprotocol.types;

import java.io.IOException;

import org.openboxprotocol.exceptions.JSONParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

public class VlanVid extends AbstractValueType<VlanVid> {

	private int vid;
	
	public static final VlanVid EMPTY_MASK = new VlanVid(0);
	
	private VlanVid(int vid) {
		this.vid = vid;
	}
	
	@Override
	public VlanVid applyMask(VlanVid mask) {
		return VlanVid.of(this.vid & mask.vid);
	}
	
	public static VlanVid of(int vid) {
		return new VlanVid(vid);
	}

	public static VlanVid fromJson(Object json) throws JSONParseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		return this.vid * 29;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof VlanVid) && ((VlanVid)other).vid == this.vid;
	}

	@Override
	public void serialize(JsonGenerator arg0, SerializerProvider arg1)
			throws IOException {
		arg0.writeNumber(this.vid);
	}
}
