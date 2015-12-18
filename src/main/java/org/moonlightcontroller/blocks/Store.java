package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class Store extends ProcessingBlock {
	private String key;
	private double soft_timeout;
	private double hard_timeout;
	private double packet_size;
	private double buffer_size;
	private double buffer_timeout;

	public Store(String id, String key, double soft_timeout, double hard_timeout, double packet_size, double buffer_size, double buffer_timeout) {
		super(id);
		this.key = key;
		this.soft_timeout = soft_timeout;
		this.hard_timeout = hard_timeout;
		this.packet_size = packet_size;
		this.buffer_size = buffer_size;
		this.buffer_timeout = buffer_timeout;
	}
	public String getKey() {
		return key;
	}
	public double getSoft_timeout() {
		return soft_timeout;
	}
	public double getHard_timeout() {
		return hard_timeout;
	}
	public double getPacket_size() {
		return packet_size;
	}
	public double getBuffer_size() {
		return buffer_size;
	}
	public double getBuffer_timeout() {
		return buffer_timeout;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setSoft_timeout(double soft_timeout) {
		this.soft_timeout = soft_timeout;
	}
	public void setHard_timeout(double hard_timeout) {
		this.hard_timeout = hard_timeout;
	}
	public void setPacket_size(double packet_size) {
		this.packet_size = packet_size;
	}
	public void setBuffer_size(double buffer_size) {
		this.buffer_size = buffer_size;
	}
	public void setBuffer_timeout(double buffer_timeout) {
		this.buffer_timeout = buffer_timeout;
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
		return null;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("key", this.key);
		config.put("soft_timeout", this.soft_timeout + "");
		config.put("hard_timeout", this.hard_timeout + "");
		config.put("packet_size", this.packet_size + "");
		config.put("buffer_size", this.buffer_size + "");
		config.put("buffer_timeout", this.buffer_timeout + "");
	}
}
