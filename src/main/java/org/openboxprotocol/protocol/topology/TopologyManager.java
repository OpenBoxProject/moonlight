package org.openboxprotocol.protocol.topology;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

public class TopologyManager implements ITopologyManager {
	private final static Logger LOG = Logger.getLogger(TopologyManager.class.getName()); 

	private final String path = "./topology/topology.json";
	private Segment segment;
	private static TopologyManager instance;

	private TopologyManager () {
		LOG.info("Initializing Topology Manager");
		buildTopologyTree();
	}
	
	public synchronized static TopologyManager getInstance() {
		if (instance == null) {
			instance = new TopologyManager();
		}
		
		return instance;
	}

	private void buildTopologyTree() {
		Gson gb = new GsonBuilder().create();
		String string = readFile();
		segment = gb.fromJson(string, Segment.class);
		System.out.println(segment.toString());
	}

	private String readFile() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(path);
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer, "UTF-8");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return writer.toString();
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
	
	public Segment getSegment() {
		return segment;
	}
	
	@Override
	public ILocationSpecifier resolve(String id) {
		// TODO: Should we through exception if the result is null
		return this.segment.findChild(id);
	}

	@Override
	public List<ILocationSpecifier> bfs() {
		List<ILocationSpecifier> bfs = new ArrayList<>();
		Queue<ILocationSpecifier> q = new LinkedList<>();
		q.add(this.segment);
		while (q.peek() != null){
			ILocationSpecifier loc = q.remove();
			bfs.add(loc);
			if (!loc.isSingleLocation()){
				Segment seg = (Segment)loc;
				q.addAll(seg.getDirectSegments());
				q.addAll(seg.getDirectEndpoints());
			}
		}
		return bfs;
	}
}
