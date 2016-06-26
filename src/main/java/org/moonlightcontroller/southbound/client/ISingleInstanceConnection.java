package org.moonlightcontroller.southbound.client;

import org.moonlightcontroller.managers.models.messages.IMessage;

/**
 * Interface for sending a message over a tcp connection
 *
 */
public interface ISingleInstanceConnection {
	public void sendMessage(IMessage msg);
}
