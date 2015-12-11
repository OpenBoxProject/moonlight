package org.moonlightcontroller.managers.models.messages;

import java.util.ArrayList;
import java.util.List;

import org.openboxprotocol.protocol.IStatement;

public class ProcessingGraphMessage implements IMessage {

	private String type;
	private int xid;
	private int dpid;
	private List<IStatement> statements;
	
	public ProcessingGraphMessage(int xid, int dpid, List<IStatement> statements) {
		this.type = "ProcessingGraphMessage";
		this.xid = xid;
		this.dpid = dpid;
		this.statements = statements;
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

	public List<IStatement> getStatements() {
		return statements;
	}
	
	public static class Builder {
		private int xid;
		private int dpid;
		private List<IStatement> statements = new ArrayList<IStatement>();
		
		public Builder setXid(int xid) {
			this.xid = xid;
			return this;
		}
		public Builder setDpid(int dpid) {
			this.dpid = dpid;
			return this;
		}
		public Builder setStatements(List<IStatement> statements) {
			this.statements = statements;
			return this;
		}
		public ProcessingGraphMessage build() {
			return new ProcessingGraphMessage(xid, dpid, statements);
		}
	}
}
