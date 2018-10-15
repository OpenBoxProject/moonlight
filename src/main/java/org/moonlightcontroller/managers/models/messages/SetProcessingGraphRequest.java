package org.moonlightcontroller.managers.models.messages;

import java.util.ArrayList;
import java.util.List;
import org.moonlightcontroller.processing.JsonBlock;
import org.moonlightcontroller.processing.JsonConnector;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SetProcessingGraphRequest extends Message {

	private List<String> modules;
	private List<JsonBlock> blocks;
	private List<JsonConnector> connectors;	
	private List<String> required_modules;
	
	public SetProcessingGraphRequest(){
	}
	
	public SetProcessingGraphRequest(int xid, long dpid, List<String> modules, List<JsonBlock> blocks, List<JsonConnector> connectors) {
		super(xid);
		this.setDpid(dpid);
		this.modules = modules;
		this.blocks = blocks;
		this.connectors = connectors;
		this.required_modules = new ArrayList<>();
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
	
	@JsonProperty("required_modules")
	public List<String> getRequired_modules() {
		return required_modules;
	}
}

