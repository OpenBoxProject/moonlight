package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.IMessage;

/**
 * Interface for sending requests to OBIs
 *
 */
public interface IConnectionInstance {
	/**
	 * Sends a message on this connection
	 * @param message the message to send
	 * @param requestSender the request sender to use when a response is received
	 */
	void sendRequest(IMessage message, IRequestSender requestSender);

}
