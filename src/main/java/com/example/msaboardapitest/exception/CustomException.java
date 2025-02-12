package com.example.msaboardapitest.exception;

public class CustomException extends RuntimeException {
    private final String errorCode;
    private final String clientMessage;

    protected CustomException(final String serverMessage, final String clientMessage, final String errorCode) {
        super(serverMessage);
        this.clientMessage = clientMessage;
        this.errorCode = errorCode;
    }

    protected CustomException(final String serverMessage, final String clientMessage, final String errorCode, final Throwable cause) {
        super(serverMessage, cause);
        this.clientMessage = clientMessage;
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getClientMessage() {
        return clientMessage;
    }
}
