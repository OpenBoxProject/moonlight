package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;

public interface IRequestSender {
	void onSuccess(IMessage message);
	void onFailure(Error err);
}
