package org.moonlightcontroller.managers.models.messages;

public class AddCustomModuleRequest extends Message {

	private String module_name;
	private String module_content;
	private String content_type;
	private String content_transfer_encoding;
	private Object translation;
	
	// Default constructor to support Jersey
	public AddCustomModuleRequest(int xid, String module_name, String module_content, Object translation) {
		super(xid);
		this.module_name = module_name;
		this.module_content = module_content;
		this.translation = translation;
		this.content_type = "application/octet-stream";
		this.content_transfer_encoding = "base64";
	}

	public String getModule_name() {
		return module_name;
	}

	public String getModule_content() {
		return module_content;
	}

	public String getContent_type() {
		return content_type;
	}

	public String getContent_transfer_encoding() {
		return content_transfer_encoding;
	}

	public Object getTranslation() {
		return translation;
	}
}
