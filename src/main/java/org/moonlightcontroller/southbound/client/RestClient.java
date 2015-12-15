package org.moonlightcontroller.southbound.client;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

public class RestClient {

	private static Client client;

	public static synchronized Client getInstance() {
		if (client == null) {
			ClientConfig clientConfig = new ClientConfig();
			LoggingFilter loggingFilter = new LoggingFilter(
					Logger.getLogger("restapi"), true);
			client = ClientBuilder.newClient(clientConfig).register(
					JacksonFeature.class);
			client = client.register(loggingFilter);		
		}
		
		return RestClient.client;
	}	
}
