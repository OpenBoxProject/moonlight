package org.moonlightcontroller.managers.models.messages;

import java.util.List;
import org.moonlightcontroller.processing.IConnector;
import org.moonlightcontroller.processing.IProcessingBlock;

public class SetProcessingGraphRequest implements IMessage {

	private String type;
	private int xid;
	private int dpid;
	private List<String> modules;
	private List<IProcessingBlock> blocks;
//	private Map<String, Object> blocks;
//	private List<JConnector> connectors;
	private List<IConnector> connectors;
	
	public SetProcessingGraphRequest(int xid, int dpid, List<String> modules, List<IProcessingBlock> blocks, List<IConnector> connectors) {
		this.type = "SetProcessingGraphRequest";
		this.xid = xid;
		this.dpid = dpid;
		this.modules = modules;
		this.setBlocks(blocks);
		this.setConnectors(connectors);
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

	public List<IProcessingBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<IProcessingBlock> blocks) {
		this.blocks = blocks;
	}

	public List<IConnector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<IConnector> connectors) {
		this.connectors = connectors;
	}

//	public Map<String, Object> getBlocks() {
//		return blocks;
//	}
//
//	public void setBlocks(Map<String, Object> blocks) {
//		this.blocks = blocks;
//	}
//
//	public List<JConnector> getConnectors() {
//		return connectors;
//	}
//
//	public void setConnectors(List<JConnector> connectors) {
//		this.connectors = connectors;
//	}

}