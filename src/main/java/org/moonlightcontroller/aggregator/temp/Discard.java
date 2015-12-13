package org.moonlightcontroller.aggregator.temp;
public class Discard extends AbstractProcessingBlock {

	private String id;
	
	public Discard(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_TERMINAL;
	}

	@Override
	public String getBlockType() {
		return "Discard";
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new Discard(id);
	}
}
