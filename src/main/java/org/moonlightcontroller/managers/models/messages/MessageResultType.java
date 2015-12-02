package org.moonlightcontroller.managers.models.messages;

public enum MessageResultType {
	OK("OK"),
	BAD_REQUEST("BAD_REQUEST"),
	FORBIDDEN("FORBIDDEN"),
	UNSUPPORTED("UNSUPPORTED"),
	INTERNAL_ERROR("INTERNAL_ERROR");
	
	private final String text;

    private MessageResultType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}