package org.moonlightcontroller.managers.models.messages;

import java.util.Map;

public class GetParametersResponse extends Message {

	private Map<String, String> parameters;

	// Default constructor to support Jersy
	public GetParametersResponse() {}

	public GetParametersResponse (int xid, Map<String, String> parameters) {
		super(xid);
		this.parameters = parameters;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}
}
