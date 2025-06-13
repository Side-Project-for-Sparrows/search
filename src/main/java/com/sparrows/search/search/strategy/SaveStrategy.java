package com.sparrows.search.search.strategy;

import com.sparrows.search.search.model.dto.SaveRequest;
import com.sparrows.search.search.model.dto.SaveResponse;

public interface SaveStrategy<A extends SaveRequest,B extends SaveResponse> {
    default boolean supports(String domain){
        return domain.equals(getDomain());
    }
    B save(A request); // 도메인 이벤트 기반 저장
    String getDomain();

    default SaveRequest fromPayload(Object payload) {
        throw new UnsupportedOperationException("Not implemented");
    }
}