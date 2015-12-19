package org.moonlightcontroller.managers.models.messages;

import java.util.List;

public class Alert implements IMessage {

	private String type;
	private int xid;
	private int origin_dpid;
	private List<AlertMessage> messages;
	
	// Default constructor to support Jersy
	public Alert() {}
	
	public Alert(int xid, int origin_dpid, List<AlertMessage> messages) {
		this.type = this.getClass().getName();
		this.xid = xid;
		this.origin_dpid = origin_dpid;
		this.messages = messages;
	}
 	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public void setXid(int xid) {
		this.xid = xid;
	}

	@Override
	public String getType() {
		return type;
	}

	public int getOrigin_dpid() {
		return origin_dpid;
	}

	public List<AlertMessage> getMessages() {
		return messages;
	}
}
