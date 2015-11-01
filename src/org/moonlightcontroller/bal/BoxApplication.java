package org.moonlightcontroller.bal;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.processing.IProcessingStage;
import org.openboxprotocol.protocol.Priority;
import org.openboxprotocol.protocol.Rule;
import org.openboxprotocol.protocol.Statement;

public abstract class BoxApplication {
	
	protected String name;
	
	private Priority priority;
	private List<Statement> statements;
	
	public BoxApplication(String name) {
		this(name, Priority.MEDIUM);
	}
	
	public BoxApplication(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
		this.statements = new ArrayList<Statement>();
	}
	
	public String getName() {
		return name;
	}
	
	public Priority getPriority() {
		return priority;
	}
		
	protected void setStatements(List<Statement> statements) {
		this.statements = statements;
	}
	
	public List<Statement> getStatemens() {
		return this.statements;
	}	
}
