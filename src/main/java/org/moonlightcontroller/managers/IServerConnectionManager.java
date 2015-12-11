package org.moonlightcontroller.managers;


import javax.ws.rs.core.Response;

import org.moonlightcontroller.managers.models.messages.Hello;
import org.moonlightcontroller.managers.models.messages.KeepAlive;

public interface IServerConnectionManager {
	
	Response handleHelloRequest(Hello message);
	
	Response handleKeepaliveRequest(KeepAlive message);
}
