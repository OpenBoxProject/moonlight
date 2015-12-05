package org.moonlightcontroller.managers;

import org.moonlightcontroller.managers.models.messages.HelloMessage;
import org.moonlightcontroller.managers.models.messages.IResponseMessage;
import org.moonlightcontroller.managers.models.messages.KeepAliveMessage;

public interface IServerConnectionManager {
	
	IResponseMessage handleHelloRequest(HelloMessage message);
	
	IResponseMessage handleKeepaliveRequest(KeepAliveMessage message);
}
