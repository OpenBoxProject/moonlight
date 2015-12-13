package org.moonlightcontroller.aggregator.temp;
public class NetworkHeaderFieldsRewriter extends AbstractProcessingBlock {

	private String id;
	
	public NetworkHeaderFieldsRewriter(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	public String getBlockType() {
		return "NetworkHeaderFieldsRewriter";
	}

	@Override
	protected AbstractProcessingBlock spawn(String id) {
		return new NetworkHeaderFieldsRewriter(id);
	}
}
