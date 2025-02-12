package com.example.msaboardapitest.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorResponse {
    private final String message;
    private final LocalDateTime timestamp;

    public static ErrorResponse from(final String message) {
        return ErrorResponse.builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
