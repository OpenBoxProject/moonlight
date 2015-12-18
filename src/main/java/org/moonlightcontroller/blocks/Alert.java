package org.moonlightcontroller.aggregator.temp;

import org.moonlightcontroller.exceptions.MergeException;


public class Alert extends AbstractProcessingBlock implements IStaticProcessingBlock {

	private String id;
	private String message;
	
	public Alert(String id, String message) {
		this.id = id;
		this.message = message;
	}
	
	@Override
	public String getId() {
		return this.id;
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
	protected AbstractProcessingBlock spawn(String id) {
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
			return new Alert("MERGED##" + this.id + "##" + other.getId() + "##" + UUIDGenerator.getSystemInstance().getUUID().toString(), this.message + ";;" + o.message);
		} else {
			throw new MergeException("Cannot merge statics of different type");
		}
	}
}
