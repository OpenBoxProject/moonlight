package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class RandomEarlyDetectionQueue extends ProcessingBlock {
	private double capacity;
	private double min_threash;
	private double max_threash;
	private double max_p;
	private double stability;
	private boolean gentle;

	public RandomEarlyDetectionQueue(String id, double capacity, double min_threash, double max_threash, double max_p, double stability, boolean gentle) {
		super(id);
		this.capacity = capacity;
		this.min_threash = min_threash;
		this.max_threash = max_threash;
		this.max_p = max_p;
		this.stability = stability;
		this.gentle = gentle;
	}
	public double getCapacity() {
		return capacity;
	}
	public double getMin_threash() {
		return min_threash;
	}
	public double getMax_threash() {
		return max_threash;
	}
	public double getMax_p() {
		return max_p;
	}
	public double getStability() {
		return stability;
	}
	public boolean getGentle() {
		return gentle;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	public void setMin_threash(double min_threash) {
		this.min_threash = min_threash;
	}
	public void setMax_threash(double max_threash) {
		this.max_threash = max_threash;
	}
	public void setMax_p(double max_p) {
		this.max_p = max_p;
	}
	public void setStability(double stability) {
		this.stability = stability;
	}
	public void setGentle(boolean gentle) {
		this.gentle = gentle;
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
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}
	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("capacity", this.capacity + "");
		config.put("min_threash", this.min_threash + "");
		config.put("max_threash", this.max_threash + "");
		config.put("max_p", this.max_p + "");
		config.put("stability", this.stability + "");
		config.put("gentle", this.gentle? "true" : "false");
	}
}
