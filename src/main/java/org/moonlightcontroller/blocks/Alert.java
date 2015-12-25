package org.moonlightcontroller.blocks;

import java.util.Map;

import org.moonlightcontroller.aggregator.UUIDGenerator;
import org.moonlightcontroller.exceptions.MergeException;
import org.moonlightcontroller.processing.BlockClass;
import org.moonlightcontroller.processing.ProcessingBlock;


public class Alert extends ProcessingBlock implements IStaticProcessingBlock {

	private String message;
	
	private Alert(String id, String message) {
		super(id);
		this.message = message;
	}
		
	public String getMessage() {
		return message;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_STATIC;
	}

	@Override
	public String getBlockType() {
		return "Alert";
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new Alert(id, this.message);
	}

	@Override
	public boolean canMergeWith(IStaticProcessingBlock other) {
		if (!(other instanceof Alert))
			return false;
		return true;
	}

	@Override
	public IStaticProcessingBlock mergeWith(IStaticProcessingBlock other) throws MergeException {
		if (other instanceof Alert) {
			Alert o = (Alert)other;
			return new Alert("MERGED##" + this.getId() + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), this.message + ";;" + o.message);
		} else {
			throw new MergeException("Cannot merge statics of different type");
		}
	}
	
	@Override
	protected void putConfiguration(Map<String, String> config) {
		// TODO: Does alert have config?
		// add message and other fields from JSON file (NOT ONLY HERE)
	}
	
	public static class Builder extends ProcessingBlock.Builder {
		private String msg;
		
		@Override
		public Alert build(){
			this.addPort();
			return new Alert(super.id, msg);
		}
		
		public Builder setMessage(String msg){
			this.msg = msg;
			return this;
		}
	}
}
