package org.openboxprotocol.protocol;

import java.util.List;

import org.moonlightcontroller.processing.IProcessingStage;
import org.openboxprotocol.protocol.topology.ILocationSpecifier;

public interface Statement {

	public ILocationSpecifier getLocation();
	
	public List<IProcessingStage> getStages();
	
	public List<Rule> getRules();
	
	public interface Builder {
		public Builder setLocation(ILocationSpecifier locspec);
		
		public Builder addStage(IProcessingStage stages);
		
		public Builder setRules(List<Rule> rules);
		
		public Statement build();
	}
}
