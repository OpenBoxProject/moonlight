package org.openboxprotocol.protocol.topology;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TopologyManager implements ITopologyManager {

	final String path;
	private Segment segment;

	public TopologyManager (String path) {
		this.path = path;
		buildTopologyTree();
	}

	private void buildTopologyTree() {
		Gson gb = new GsonBuilder().create();
		segment = gb.fromJson(path, Segment.class);
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
