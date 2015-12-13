package org.moonlightcontroller.aggregator.temp;
public class ToDevice extends AbstractProcessingBlock {

	private String id;
	
	public ToDevice(String id) {
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
		return "ToDevice";
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new ToDevice(id);
	}
}
