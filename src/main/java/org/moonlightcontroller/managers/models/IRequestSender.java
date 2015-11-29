package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.ErrorMessage;
import org.moonlightcontroller.managers.models.messages.IMessage;

public interface IRequestSender {
	void onSuccess(IMessage message);
	void onFailure(ErrorMessage err);
}
