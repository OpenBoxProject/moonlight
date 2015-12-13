package org.moonlightcontroller.aggregator.temp;
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
		Alert o = (Alert)other;
		return this.message.equals(o.message);
	}
}
