package com.sparrows.search.common.exception.handling;


import com.sparrows.search.common.exception.ErrorCode;
import com.sparrows.search.common.exception.BusinessException;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}