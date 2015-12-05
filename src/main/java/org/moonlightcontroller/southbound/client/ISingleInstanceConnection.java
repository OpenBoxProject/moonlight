package org.moonlightcontroller.southbound.client;

import org.moonlightcontroller.managers.models.messages.IMessage;

public interface ISingleInstanceConnection {
	public void sendMessage(IMessage msg);
}
