package org.moonlightcontroller.managers.models.messages;

public interface IMessage {
	public int getXid();
	public void setXid(int xid);
	public String getType();
	public String getSourceAddr();
	public void setSourceAddr(String sourceAddr);
}
