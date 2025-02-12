package com.example.msaboardapitest.exception;

public abstract class NotFoundException extends CustomException {
    protected NotFoundException(final String serverMessage, final String clientMessage, final String errorCode) {
        super(serverMessage, clientMessage, errorCode);
    }

    protected NotFoundException(final String serverMessage, final String clientMessage, final String errorCode, final Throwable cause) {
        super(serverMessage, clientMessage, errorCode, cause);
    }
}
