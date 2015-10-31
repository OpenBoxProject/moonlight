package org.openboxprotocol.protocol;

import java.util.List;

public interface PayloadMatch {

	public List<PayloadPattern> getPatterns();
	
	public interface Builder {

		public Builder addPattern(PayloadPattern pattern);
		
		public Builder addPatterns(List<PayloadPattern> patterns);
		
		public PayloadMatch build();
	}
}
