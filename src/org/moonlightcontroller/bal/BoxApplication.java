package org.moonlightcontroller.bal;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.processing.IProcessingStage;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Rule;

public abstract class BoxApplication {
	
	protected String name;
	
	private Priority priority;
	private List<IProcessingStage> stages;
	private List<Rule> rules;
	
	public BoxApplication(String name) {
		this(name, Priority.MEDIUM);
	}
	
	public BoxApplication(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
		this.stages = new ArrayList<IProcessingStage>();
	}
	
	public String getName() {
		return name;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	protected void addModule(IProcessingStage stage) {
		this.stages.add(stage);
	}
	
	protected void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public List<Rule> getRules() {
		return this.rules;
	}	
}
