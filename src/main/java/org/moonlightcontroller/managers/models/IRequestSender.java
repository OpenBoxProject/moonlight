package org.moonlightcontroller.managers.models;

import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;

/**
 * Interface for handling the asynchronous request/response mechanism.
 * When requesting a request from an OBI over an IConnectionInstance.
 * This interface is passed as well, when the asynchronous response for the request arrives
 * the correct method will be invoke on the IRequestSender.
 */
public interface IRequestSender {
	/**
	 * Returns the message of a successful response
	 * This method is called when an asynchronous response arrives with successful result
	 * @param message
	 */
	void onSuccess(IMessage message);
	/**
	 * Returns the error of a failed response
	 * This method is called when an asynchronous response arrives with an error
	 * @param err
	 */
	void onFailure(Error err);
}
