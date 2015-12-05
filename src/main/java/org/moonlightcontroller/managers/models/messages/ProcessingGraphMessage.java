package org.moonlightcontroller.managers.models.messages;

import java.util.List;

public class ProcessingGraphMessage implements IMessage {

	private String type;
	private int xid;
	private int dpid;
	private List<String> modules;
	private List<String> blocks;
	private List<String> connectors;
	
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

	public void setDpid(int dpid) {
		this.dpid = dpid;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setXid(int xid) {
		this.xid = xid;
	}

	public List<String> getModules() {
		return modules;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}

	public List<String> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<String> connectors) {
		this.connectors = connectors;
	}

	public List<String> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<String> blocks) {
		this.blocks = blocks;
	}

}
