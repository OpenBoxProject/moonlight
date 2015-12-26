package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class VlanEncapsulate extends ProcessingBlock {
	private int vlan_vid;
	private int vlan_dei;
	private int vlan_pcp;
	private int ethertype;

	public VlanEncapsulate(String id, int vlan_vid) {
		super(id);
		this.vlan_vid = vlan_vid;
	}

	public VlanEncapsulate(String id, int vlan_vid, int vlan_dei, int vlan_pcp, int ethertype) {
		super(id);
		this.vlan_vid = vlan_vid;
		this.vlan_dei = vlan_dei;
		this.vlan_pcp = vlan_pcp;
		this.ethertype = ethertype;
	}

	public int getVlan_vid() {
		return vlan_vid;
	}

	public int getVlan_dei() {
		return vlan_dei;
	}

	public int getVlan_pcp() {
		return vlan_pcp;
	}

	public int getEthertype() {
		return ethertype;
	}

	@Override
	public BlockClass getBlockClass() {
		return BlockClass.BLOCK_CLASS_MODIFIER;
	}

	@Override
	protected void putConfiguration(Map<String, String> config) {
		config.put("vlan_vid", this.vlan_vid+ "");
		config.put("vlan_dei", this.vlan_dei+ "");
		config.put("vlan_pcp", this.vlan_pcp+ "");
		config.put("ethertype", this.ethertype+ "");
	}

	@Override
	protected ProcessingBlock spawn(String id) {
		return new VlanEncapsulate(id, vlan_vid, vlan_dei, vlan_pcp, ethertype);
	}
}