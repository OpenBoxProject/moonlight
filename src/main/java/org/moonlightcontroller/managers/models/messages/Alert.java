package org.moonlightcontroller.managers.models.messages;

import java.util.List;

public class Alert extends Message {

	private long origin_dpid;
	private List<AlertMessage> messages;
	
	// Default constructor to support Jersy
	public Alert() {}
	
	public Alert(int xid, long origin_dpid, List<AlertMessage> messages) {
		super(xid);
		this.origin_dpid = origin_dpid;
		this.messages = messages;
	}
 	
	public long getOrigin_dpid() {
		return origin_dpid;
	}

	public List<AlertMessage> getMessages() {
		return messages;
	}
}
