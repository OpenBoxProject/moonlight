package org.moonlightcontroller.registry;

import java.io.IOException;
import java.util.List;

import org.moonlightcontroller.bal.BoxApplication;

public interface IApplicationRegistry {
	void addApplication(BoxApplication app);

	boolean loadFromPath(String path) throws IOException;
	
	List<BoxApplication> getApplications();
	
	BoxApplication getApplicationByName(String name);
}
