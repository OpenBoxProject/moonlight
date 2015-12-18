package org.moonlightcontroller.blocks;

import org.moonlightcontroller.processing.ProcessingBlock;
import java.util.Map;
import org.moonlightcontroller.processing.BlockClass;

public class Ipv4AddressTranslator extends ProcessingBlock {
	//TODO fix
	private ipv4_translator_rules input_spec;
	private int tcp_done_timeout;
	private int tcp_nodata_timeout;
	private int tcp_guarantee;
	private int udp_timeout;
	private int udp_streaming_timeout;
	private int udp_guarantee;
	private int reap_interval;
	private int mapping_capacity;

	public Ipv4AddressTranslator(String id, ipv4_translator_rules input_spec, int tcp_done_timeout, int tcp_nodata_timeout, int tcp_guarantee, int udp_timeout, int udp_streaming_timeout, int udp_guarantee, int reap_interval, int mapping_capacity) {
		super(id);
		this.input_spec = input_spec;
		this.tcp_done_timeout = tcp_done_timeout;
		this.tcp_nodata_timeout = tcp_nodata_timeout;
		this.tcp_guarantee = tcp_guarantee;
		this.udp_timeout = udp_timeout;
		this.udp_streaming_timeout = udp_streaming_timeout;
		this.udp_guarantee = udp_guarantee;
		this.reap_interval = reap_interval;
		this.mapping_capacity = mapping_capacity;
	}
	public ipv4_translator_rules getInput_spec() {
		return input_spec;
	}
	public int getTcp_done_timeout() {
		return tcp_done_timeout;
	}
	public int getTcp_nodata_timeout() {
		return tcp_nodata_timeout;
	}
	public int getTcp_guarantee() {
		return tcp_guarantee;
	}
	public int getUdp_timeout() {
		return udp_timeout;
	}
	public int getUdp_streaming_timeout() {
		return udp_streaming_timeout;
	}
	public int getUdp_guarantee() {
		return udp_guarantee;
	}
	public int getReap_interval() {
		return reap_interval;
	}
	public int getMapping_capacity() {
		return mapping_capacity;
	}
	public void setInput_spec(ipv4_translator_rules input_spec) {
		this.input_spec = input_spec;
	}
	public void setTcp_done_timeout(int tcp_done_timeout) {
		this.tcp_done_timeout = tcp_done_timeout;
	}
	public void setTcp_nodata_timeout(int tcp_nodata_timeout) {
		this.tcp_nodata_timeout = tcp_nodata_timeout;
	}
	public void setTcp_guarantee(int tcp_guarantee) {
		this.tcp_guarantee = tcp_guarantee;
	}
	public void setUdp_timeout(int udp_timeout) {
		this.udp_timeout = udp_timeout;
	}
	public void setUdp_streaming_timeout(int udp_streaming_timeout) {
		this.udp_streaming_timeout = udp_streaming_timeout;
	}
	public void setUdp_guarantee(int udp_guarantee) {
		this.udp_guarantee = udp_guarantee;
	}
	public void setReap_interval(int reap_interval) {
		this.reap_interval = reap_interval;
	}
	public void setMapping_capacity(int mapping_capacity) {
		this.mapping_capacity = mapping_capacity;
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
		config.put("input_spec", this.input_spec.toString());
		config.put("tcp_done_timeout", this.tcp_done_timeout+ "");
		config.put("tcp_nodata_timeout", this.tcp_nodata_timeout+ "");
		config.put("tcp_guarantee", this.tcp_guarantee+ "");
		config.put("udp_timeout", this.udp_timeout+ "");
		config.put("udp_streaming_timeout", this.udp_streaming_timeout+ "");
		config.put("udp_guarantee", this.udp_guarantee+ "");
		config.put("reap_interval", this.reap_interval+ "");
		config.put("mapping_capacity", this.mapping_capacity+ "");
	}
}

class ipv4_translator_rules{}
