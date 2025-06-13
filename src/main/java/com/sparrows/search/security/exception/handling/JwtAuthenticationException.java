package com.sparrows.search.security.exception.handling;

import com.sparrows.search.security.exception.BusinessException;
import com.sparrows.search.security.exception.SecurityErrorCode;

public class JwtAuthenticationException extends BusinessException {

    public JwtAuthenticationException(SecurityErrorCode errorCode) {
        super(errorCode);
    }

}
