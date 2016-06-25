package org.moonlightcontroller.bal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.moonlightcontroller.events.IAlertListener;
import org.moonlightcontroller.events.IHandleClient;
import org.moonlightcontroller.events.IInstanceDownListener;
import org.moonlightcontroller.events.IInstanceUpListener;
import org.moonlightcontroller.processing.IProcessingGraph;
import org.moonlightcontroller.topology.IApplicationTopology;
import org.moonlightcontroller.topology.ILocationSpecifier;
import org.openboxprotocol.protocol.IStatement;
import org.openboxprotocol.protocol.Priority;

public abstract class BoxApplication {
	
	protected String name;
	
	private Priority priority;
	private Map<ILocationSpecifier, IStatement> statements;
	private IAlertListener alertListener;
	private IInstanceDownListener instanceDownListener;
	private IInstanceUpListener instanceUpListener;
	
	public BoxApplication(String name) {
		this(name, Priority.MEDIUM);
	}
	
	public BoxApplication(String name, Priority priority) {
		this.name = name;
		this.priority = priority;
		this.statements = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public Priority getPriority() {
		return priority;
	}
			
	public Collection<IStatement> getStatements() {
		return this.statements.values();
	}
	
	public IProcessingGraph getProcessingGraph(ILocationSpecifier loc) {
		// TODO: This should fetch the 'lowest' processing graph that exists for the given location
		// E.g., if 'loc' is an OBI under segment s1.1 which is in segment s1, and there is no graph
		// for the OBI location specifier, but there are graphs for s1.1 and for s1, then this method
		// should return the graph for s1.1
		if (!this.statements.containsKey(loc))
			return null;
		return this.statements.get(loc).getProcessingGraph();
	}
	
	public void handleAppStart(IApplicationTopology top, IHandleClient handles) {
	}
	
	public void handleAppStop(){
	}
	
	public void handleError(){
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

	protected void setStatements(Collection<IStatement> statements) {
		for (IStatement st: statements) {
			this.statements.put(st.getLocation(), st);
		}
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
