package org.moonlightcontroller.managers.models.messages;

import org.moonlightcontroller.processing.IProcessingGraph;

public class SetProcessingGraphRequest implements IMessage {

	private String type;
	private int xid;
	private int dpid;
	private IProcessingGraph graph;
	
	public SetProcessingGraphRequest(){	
	}
	
	public SetProcessingGraphRequest(int xid, int dpid, IProcessingGraph graph) {
		this.type = "SetProcessingGraphRequest";
		this.xid = xid;
		this.dpid = dpid;
		this.graph = graph;
	}
	
	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return type;
	}

	public int getDpid() {
		return dpid;
	}

	public IProcessingGraph getProcessingGraph() {
		return graph;
	}
	
	public static class Builder {
		private int xid;
		private int dpid;
		private IProcessingGraph graph;
		
		public Builder setXid(int xid) {
			this.xid = xid;
			return this;
		}
		public Builder setDpid(int dpid) {
			this.dpid = dpid;
			return this;
		}
		public Builder setProcessingGraph(IProcessingGraph graph) {
			this.graph = graph;
			return this;
		}
		public SetProcessingGraphRequest build() {
			return new SetProcessingGraphRequest(xid, dpid, graph);
		}
	}
}
