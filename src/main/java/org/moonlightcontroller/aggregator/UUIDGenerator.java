package org.moonlightcontroller.aggregator;

import java.util.Random;
import java.util.UUID;

public class UUIDGenerator {

	public static final boolean DEBUG_MODE = true;
	
	private Random rand;
	
	public UUIDGenerator() {
		rand = new Random();
	}
	
	public UUIDGenerator(long seed) {
		rand = new Random(seed);
	}
	
	private static UUIDGenerator _instance;
	
	public static UUIDGenerator getSystemInstance() {
		if (_instance == null) {
			synchronized (UUIDGenerator.class) {
				if (_instance == null) {
					_instance = (DEBUG_MODE ? new UUIDGenerator(1) : new UUIDGenerator());
				}
			}
		}
		return _instance;
	}
	
	public UUID getUUID() {
		return new UUID(rand.nextLong(), rand.nextLong());
	}
	
}
