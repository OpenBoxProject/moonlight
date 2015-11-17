package org.openboxprotocol.protocol;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class OpenBoxPayloadMatch implements PayloadMatch {

	private List<PayloadPattern> patterns;
	
	private OpenBoxPayloadMatch(List<PayloadPattern> patterns) {
		this.patterns = patterns;
	}
	
	@Override
	public List<PayloadPattern> getPatterns() {
		return patterns;
	}
	
	public static class Builder implements PayloadMatch.Builder {

		private List<PayloadPattern> patterns;
		
		public Builder() {
			this.patterns = new ArrayList<PayloadPattern>();
		}
		
		@Override
		public org.openboxprotocol.protocol.PayloadMatch.Builder addPattern(
				PayloadPattern pattern) {
			patterns.add(pattern);
			return this;
		}

		@Override
		public org.openboxprotocol.protocol.PayloadMatch.Builder addPatterns(
				List<PayloadPattern> patterns) {
			this.patterns.addAll(patterns);
			return this;
		}

		@Override
		public PayloadMatch build() {
			return new OpenBoxPayloadMatch(ImmutableList.copyOf(patterns));
		}
		
	}

}
