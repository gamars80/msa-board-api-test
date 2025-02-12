package com.example.msaboardapitest.exception;

public abstract class UnauthorizedException extends CustomException {
    protected UnauthorizedException(final String serverMessage, final String clientMessage, final String errorCode) {
        super(serverMessage, clientMessage, errorCode);
    }

    protected UnauthorizedException(final String serverMessage, final String clientMessage, final String errorCode, final Throwable cause) {
        super(serverMessage, clientMessage, errorCode, cause);
    }
}
