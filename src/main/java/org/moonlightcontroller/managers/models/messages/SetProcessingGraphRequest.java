package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import org.moonlightcontroller.processing.JsonBlock;
import org.moonlightcontroller.processing.JsonConnector;

public class SetProcessingGraphRequest implements IMessage {

	private String type;
	private int xid;
	private int dpid;
	private List<String> modules;
	private List<JsonBlock> blocks;
	private List<JsonConnector> connectors;
	
	public SetProcessingGraphRequest(){
	}
	
	public SetProcessingGraphRequest(int xid, int dpid, List<String> modules, List<JsonBlock> blocks, List<JsonConnector> connectors) {
		this.type = "SetProcessingGraphRequest";
		this.xid = xid;
		this.dpid = dpid;
		this.modules = modules;
		this.blocks = blocks;
		this.connectors = connectors;
	}
	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return type;
	}

	public int getDpid() {
		return dpid;
	}

	public List<String> getModules() {
		return modules;
	}

	public List<JsonBlock> getBlocks() {
		return blocks;
	}

	public List<JsonConnector> getConnectors() {
		return connectors;
	}

	@Override
	public void setXid(int xid) {
		
	}
}

