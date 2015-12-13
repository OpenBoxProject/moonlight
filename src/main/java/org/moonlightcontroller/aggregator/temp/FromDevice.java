package org.moonlightcontroller.aggregator.temp;
public class FromDevice extends AbstractProcessingBlock {

	private String id;
	
	public FromDevice(String id) {
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
		return "FromDevice";
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new FromDevice(id);
	}
}
