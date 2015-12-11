package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.ErrorMessage;
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
	public void onFailure(ErrorMessage err) {
		
	}


	public int getXid() {
		return xid;
	}
}
