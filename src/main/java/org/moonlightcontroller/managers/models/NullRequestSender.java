package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;

public class NullRequestSender implements IRequestSender {

	public NullRequestSender() {
	}

	@Override
	public void onSuccess(IMessage message) {
	}

	@Override
	public void onFailure(Error err) {
	}
}
