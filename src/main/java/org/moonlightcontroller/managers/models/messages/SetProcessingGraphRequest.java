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

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public List<JsonBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<JsonBlock> blocks) {
		this.blocks = blocks;
	}

	public List<JsonConnector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<JsonConnector> connectors) {
		this.connectors = connectors;
	}
}