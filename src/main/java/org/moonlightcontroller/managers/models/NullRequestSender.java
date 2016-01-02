package org.moonlightcontroller.managers.models;

import java.util.logging.Logger;

import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;

public class NullRequestSender implements IRequestSender {

	private final static Logger LOG = Logger.getLogger(NullRequestSender.class.getName());
	
	public NullRequestSender() {
	}

	@Override
	public void onSuccess(IMessage message) {
		LOG.info("Success sending message: " + message.getXid());
	}

	@Override
	public void onFailure(Error err) {
		LOG.info("Error sending message: " 
			+ err.getXid() + ":" 
			+ err.getType() + ":" 
			+ err.getError_subtype() + ":" 
			+ err.getMessage());
	}
}
