package org.openboxprotocol.protocol;

import java.util.List;

import org.openboxprotocol.protocol.instructions.Instruction;
import org.openboxprotocol.types.Cookie;

public interface Rule {
	
	public Cookie getCookie();
	
	public int getPriority();
	
	public HeaderMatch getHeaderMatch();
	
	public PayloadMatch getPayloadMatch();
	
	public List<Instruction> getInstructions();
	
	public interface Builder {
		public Builder setCookie(Cookie cookie);
		
		public Builder setPriority(int priority);
		
		public Builder setHeaderMatch(HeaderMatch headerMatch);
		
		public Builder setPayloadMatch(PayloadMatch payloadMatch);
		
		public Builder addInstruction(Instruction instruction);
		
		public Builder addInstructions(List<Instruction> instructions);
		
		public Rule build();
	}
}
