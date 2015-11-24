package org.moonlightcontroller.bal;

import java.util.ArrayList;
import java.util.List;

import org.moonlightcontroller.events.IAlertListener;
import org.moonlightcontroller.events.IInstanceDownListener;
import org.moonlightcontroller.events.IInstanceUpListener;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.Priority;

public abstract class BoxApplication {
	
	protected String name;
	
	private Priority priority;
	private List<IStatement> statements;
	private IAlertListener alertListener;
	private IInstanceDownListener instanceDownListener;
	private IInstanceUpListener instanceUpListener;
	
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
			
	public List<IStatement> getStatemens() {
		return this.statements;
	}
	
	public IAlertListener getAlertListener(){
		return this.alertListener;
	}
	
	public IInstanceDownListener getInstanceDownListener(){
		return this.instanceDownListener;
	}

	public IInstanceUpListener getInstanceUpListener(){
		return this.instanceUpListener;
	}

	protected void setStatements(List<IStatement> statements) {
		this.statements = statements;
	}

	protected void setAlertListener(IAlertListener al){
		this.alertListener = al;
	}
	
	protected void setInstanceDowntListener(IInstanceDownListener dl){
		this.instanceDownListener = dl;
	}
	
	protected void setInstanceUpListener(IInstanceUpListener ul){
		this.instanceUpListener = ul;
	}
}
