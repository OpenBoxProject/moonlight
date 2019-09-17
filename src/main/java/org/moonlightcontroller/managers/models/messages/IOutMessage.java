package org.moonlightcontroller.managers.models.messages;

public interface IOutMessage {
    int getXid();
    void setXid(int xid);

    String getType();

    String getSourceAddr();
    void setSourceAddr(String sourceAddr);
}
