package com.example.msaboardapitest.exception;


public class ExternalApiErrorException extends InternalServerErrorException {

    private static final String ERROR_CODE = "EXTERNAL_API_ERROR_";
    private static final String SERVER_MESSAGE = "외부 api 통신 오류";

    public ExternalApiErrorException(final ExternalApiErrorMessage message) {
        super(String.format("%s -> %s", SERVER_MESSAGE, message.getDescription()), message.getDescription(), ERROR_CODE + message);
    }

    public ExternalApiErrorException(final ExternalApiErrorMessage message, final Throwable cause) {
        super(String.format("%s -> %s", SERVER_MESSAGE, message.getDescription()), message.getDescription(), ERROR_CODE + message, cause);
    }

    public ExternalApiErrorException(final ExternalApiErrorMessage message, String detailMessage) {
        super(String.format("%s -> %s(%s)", SERVER_MESSAGE, message.getDescription(), detailMessage), message.getDescription(), ERROR_CODE + message);
    }

    public ExternalApiErrorException(final ExternalApiErrorMessage message, String detailMessage, final Throwable cause) {
        super(
                String.format("%s -> %s(%s)", SERVER_MESSAGE, message.getDescription(), detailMessage),
                message.getDescription(),
                ERROR_CODE + message,
                cause
        );
    }
}

