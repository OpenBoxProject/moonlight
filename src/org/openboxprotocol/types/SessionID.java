package org.openboxprotocol.types;

public class SessionID implements ValueType<SessionID> {
	
	public static final SessionID CURRENT_SESSION = new SessionID("#CURRENT#");
	
	private String id;
	
	private SessionID(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public SessionID applyMask(SessionID mask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof SessionID) && ((SessionID)other).id.equals(this.id);
	}
}
