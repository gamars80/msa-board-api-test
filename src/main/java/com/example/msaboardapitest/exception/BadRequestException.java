package com.example.msaboardapitest.exception;

public abstract class BadRequestException extends CustomException {
    protected BadRequestException(final String serverMessage, final String clientMessage, final String errorCode) {
        super(serverMessage, clientMessage, errorCode);
    }

    protected BadRequestException(final String serverMessage, final String clientMessage, final String errorCode, final Throwable cause) {
        super(serverMessage, clientMessage, errorCode, cause);
    }

}
