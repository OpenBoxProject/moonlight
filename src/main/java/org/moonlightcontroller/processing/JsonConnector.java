package org.moonlightcontroller.processing;

public class JsonConnector {
	private String src;
	private int src_port;
	private String dst;
	private int dst_port;
	
	public JsonConnector(){
	}
	
	public JsonConnector(String src, int src_port, String dst, int dst_port) {
		this.src = src;
		this.src_port = src_port;
		this.dst = dst;
		this.dst_port = dst_port;
	}

	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public int getSrc_port() {
		return src_port;
	}
	public void setSrc_port(int src_port) {
		this.src_port = src_port;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public int getDst_port() {
		return dst_port;
	}
	public void setDst_port(int dst_port) {
		this.dst_port = dst_port;
	} 
}
