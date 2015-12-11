package org.moonlightcontroller.managers.models.messages;

public class ErrorMessage implements IResponseMessage {

	private MessageResultType errorType;
	private ErrorSubType errorSubType;
	private int xid;
	
	public ErrorMessage (int xid, MessageResultType errorType, ErrorSubType errorSubType) {
		this.xid = xid;
		this.errorType = errorType;
		this.errorSubType = errorSubType;
		validateErrorSubType();
	}
	
	@Override
	public String toString() {
		return String.format("Error message of type = %s, sub type = %s for xid = %s", errorType.name(), errorSubType.name(), xid);
	}

	private void validateErrorSubType() {
		switch (errorType) {
		case BAD_REQUEST:
			switch (errorSubType) {
			case BAD_VERSION :
			case BAD_TYPE :
			case BAD_GRAPH :
			case BAD_BLOCK :
			case BAD_CONNECTOR :
			case BAD_HEADER_MATCH :
			case BAD_PAYLOAD_MATCH :
			case BAD_FILE :
			case ILLEGAL_ARGUMENT :
			case ILLEGAL_STATE :
				return;
			default:
				errorSubType = ErrorSubType.ILLEGAL_ARGUMENT;
			}
			break;
		case FORBIDDEN :
			switch (errorSubType) {
			case NOT_PERMITTED:
			case NO_ACCESS:
				return;
			default:
				errorSubType = ErrorSubType.NOT_PERMITTED;
			}
		case UNSUPPORTED:
			switch (errorSubType) {
			case UNSUPPORTED_VERSION:
			case UNSUPPORTED_BLOCK:
			case UNSUPPORTED_MESSAGE:
			case UNSUPPORTED_OTHER:
				return;
			default:
				errorSubType = ErrorSubType.UNSUPPORTED_OTHER;
			}
		case INTERNAL_ERROR:
			switch (errorSubType) {
			case ADD_MODULE_FAILED:
			case INTERNAL_ERROR:
				return;
			default:
				errorSubType = ErrorSubType.INTERNAL_ERROR;
			}

		default:
			break;
		}
	}

	@Override
	public int getXid() {
		return xid;
	}

	@Override
	public String getType() {
		return "error";
	}
}
