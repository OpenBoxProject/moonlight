package org.moonlightcontroller.managers;

public class ClientConnectionManager implements IClientConnectionManager{
	
	private static ClientConnectionManager instance;
	
	private ClientConnectionManager() {
		
	}
	
	public synchronized static ClientConnectionManager getInstance() {
		if (instance == null) {
			instance = new ClientConnectionManager();
		}

		return instance;
	}
	
	public void sendProcessingGraphRequest(int xid) {
		
	}
}
