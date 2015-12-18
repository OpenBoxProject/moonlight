package org.moonlightcontroller.processing;

import java.util.Map;

public class JsonBlock {
	private String type;
	private String name;
	private Map<String, String> config;
	
	public JsonBlock(String type, String name, Map<String, String> config) {
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
	public Map<String, String> getConfig() {
		return config;
	}
	public void setConfig(Map<String, String> config) {
		this.config = config;
	}	 
}
