package org.moonlightcontroller.managers.models.messages;

public class SuccessMessage implements IResponseMessage {
	@Override 
	public String toString() {
		return MessageResultType.OK.name();
	}
}
