package com.sparrows.search.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SearchGlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(e);
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error(e.getMessage());
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER);
        return new ResponseEntity<>(errorResponse, ErrorCode.INTERNAL_SERVER.getStatus());
    }
}

