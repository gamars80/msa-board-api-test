package com.example.msaboardapitest.exception;

public abstract class InternalServerErrorException extends CustomException {
    protected InternalServerErrorException(final String serverMessage, final String clientMessage, final String errorCode) {
        super(serverMessage, clientMessage, errorCode);
    }

    protected InternalServerErrorException(final String serverMessage, final String clientMessage, final String errorCode, final Throwable cause) {
        super(serverMessage, clientMessage, errorCode, cause);
    }
}
