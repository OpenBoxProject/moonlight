package org.moonlightcontroller.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ControllerProperties {
	private static ControllerProperties instance;
	private int keepaliveInterval;

	private ControllerProperties() {
		init();
	}

	public static ControllerProperties getInstance() {
		if (instance == null) {
			instance = new ControllerProperties();
		}

		return instance;
	}

	private void init() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			keepaliveInterval = Integer.parseInt(prop.getProperty("keepaliveInterval"));


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getKeepAliveInterval() {
		return keepaliveInterval;
	}
}
