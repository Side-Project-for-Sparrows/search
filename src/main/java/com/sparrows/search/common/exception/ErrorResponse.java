package com.sparrows.search.common.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private final int status;
    private final String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(BusinessException e) {
        return ErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode e) {
        return ErrorResponse.builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }
}

