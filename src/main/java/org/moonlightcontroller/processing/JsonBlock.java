package org.moonlightcontroller.processing;

import java.util.Map;

public class JsonBlock {
	private String type;
	private String name;
	private Map<String, Object> config;
	
	public JsonBlock(){	
	}
	
	public JsonBlock(String type, String name, Map<String, Object> config) {
		this.type = type;
		this.name = name;
		this.config = config;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getConfig() {
		return config;
	}
	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}	 
}
