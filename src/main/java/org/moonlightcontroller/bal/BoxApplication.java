package org.moonlightcontroller.bal;

import java.util.ArrayList;
import java.util.List;

import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.Priority;

public abstract class BoxApplication {
	
	protected String name;
	
	private Priority priority;
	private List<IStatement> statements;
	
	public BoxApplication(String name) {
		this(name, Priority.MEDIUM);
	}
	
	public BoxApplication(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
		this.statements = new ArrayList<IStatement>();
	}
	
	public String getName() {
		return name;
	}
	
	public Priority getPriority() {
		return priority;
	}
		
	protected void setStatements(List<IStatement> statements) {
		this.statements = statements;
	}
	
	public List<IStatement> getStatemens() {
		return this.statements;
	}	
}
