package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class FromDump extends ProcessingBlock {
	private String filename;
	private boolean timing;
	private boolean active;

	public FromDump(String id, String filename, boolean timing, boolean active) {
		super(id);
		this.filename = filename;
		this.timing = timing;
		this.active = active;
	}
	public String getFilename() {
		return filename;
	}
	public boolean getTiming() {
		return timing;
	}
	public boolean getActive() {
		return active;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setTiming(boolean timing) {
		this.timing = timing;
	}
	public void setActive(boolean active) {
		this.active = active;
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
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("filename", this.filename);
		config.put("timing", this.timing? "true" : "false");
		config.put("active", this.active? "true" : "false");
	}
}
