package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.IMessage;

public interface IConnectionInstance {
	int sendRequest(IMessage message, IRequestSender requestSender);

}
