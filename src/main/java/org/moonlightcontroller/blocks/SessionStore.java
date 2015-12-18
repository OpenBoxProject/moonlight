package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class SessionStore extends ProcessingBlock {
	private Map<String, String> fields;

	public SessionStore(String id, Map<String, String> fields) {
		super(id);
		this.fields = fields;
	}
	public Map<String, String> getFields() {
		return fields;
	}
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	@Override
	public String getBlockType() {
		return null;
	}
	@Override
	public String toShortString() {
		return null;
	}
	@Override
	public ProcessingBlock clone() {
		return null;
	}
	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_CLASSIFIER;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("fields", this.fields + "");
	}
}
