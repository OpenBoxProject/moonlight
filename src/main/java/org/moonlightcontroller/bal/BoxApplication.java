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

/**
 * Abstract class for all OpenBox Applications to inherit.
 * It supplies the minimal functionality for inheriting classes to create OpenBox Applications.
 * OpenBox Applications must inherit this class in order to be able to register with the registry. 
 */
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
	
	/**
	 * @return the name of the application
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the priority of the application
	 */
	public Priority getPriority() {
		return priority;
	}
			
	/**
	 * @return the statements of the application
	 */
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
	
	/**
	 * A handler for the Application Started event. 
	 * Whenever the application starts, the controller will call this method.
	 * Inheriting classes can override this, to get a notification on application starting.
	 * @param top The topology of the network
	 * @param handles A client for accessing read/write handles
	 */
	public void handleAppStart(IApplicationTopology top, IHandleClient handles) {
	}
	
	/**
	 * A handler for the Application Stop event. 
	 * Whenever the application stops, the controller will call this method.
	 * Inheriting classes can override this, to get a notification on application stopping.
	 */
	public void handleAppStop(){
	}
	
	/**
	 * A handler for the Application error event. 
	 * Whenever the application encounters an error, the controller will call this method.
	 * Inheriting classes can override this, to get a notification on application error.
	 */
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
