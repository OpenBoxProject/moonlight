package org.openboxprotocol.protocol.topology;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TopologyManager implements ITopologyManager {
	private final static Logger LOG = Logger.getLogger(TopologyManager.class.getName()); 

	final String path;
	private Segment segment;

	public TopologyManager (String path) {
		LOG.info("Initializing Topology Manager");
		this.path = path;
		buildTopologyTree();
	}

	private void buildTopologyTree() {
		Gson gb = new GsonBuilder().create();
		String string = readFile();
		segment = gb.fromJson(string, Segment.class);
		System.out.println(segment.toString());
	}

	private String readFile() {
		BufferedReader br;
		try {
		br = new BufferedReader(new FileReader(path));
	    StringBuilder sb = new StringBuilder();
		
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    br.close();

		    return sb.toString();
		} catch (FileNotFoundException e) {
			LOG.warning("File Not Found exception was catched");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return "";
	}

	@Override
	public List<InstanceLocationSpecifier> getSubInstances(ILocationSpecifier loc) {
		List<InstanceLocationSpecifier> list = new ArrayList<>();
		if (loc instanceof InstanceLocationSpecifier) {
			list.add((InstanceLocationSpecifier) loc);
		} else if (loc instanceof Segment) {
			list.addAll(((Segment)loc).getEndpoints());
		}
		
		return list;
	}

	@Override
	public List<InstanceLocationSpecifier> getAllEndpoints() {
		return segment.getEndpoints();
	}
}
