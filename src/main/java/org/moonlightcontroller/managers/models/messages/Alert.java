package org.moonlightcontroller.managers.models.messages;

import java.util.List;

public class Alert extends Message {

	private int origin_dpid;
	private List<AlertMessage> messages;
	
	// Default constructor to support Jersy
	public Alert() {}
	
	public Alert(int xid, int origin_dpid, List<AlertMessage> messages) {
		super(xid);
		this.origin_dpid = origin_dpid;
		this.messages = messages;
	}
 	
	public int getOrigin_dpid() {
		return origin_dpid;
	}

	public List<AlertMessage> getMessages() {
		return messages;
	}
}
