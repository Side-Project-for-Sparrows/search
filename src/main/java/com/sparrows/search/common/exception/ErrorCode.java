package com.sparrows.search.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Global
    INVALID_REQUEST(HttpStatus.UNPROCESSABLE_ENTITY, "INVALID REQUEST ERROR"),
    INTERNAL_SERVER(HttpStatus.UNPROCESSABLE_ENTITY, "INTERNAL SERVER ERROR"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access is denied."),
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "FAIL AUTHORIZATION"); // 권한 없는 요청

    private final HttpStatus status;
    private final String message;
}
