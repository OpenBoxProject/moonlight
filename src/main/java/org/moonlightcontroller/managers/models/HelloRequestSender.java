package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;

public class HelloRequestSender implements IRequestSender {

	private int xid;
	
	public HelloRequestSender(int xid) {
		this.xid = xid;
	}
	
	
	@Override
	public void onSuccess(IMessage message) {

	}

	@Override
	public void onFailure(Error err) {
		
	}


	public int getXid() {
		return xid;
	}
}
