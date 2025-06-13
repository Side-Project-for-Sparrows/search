package com.sparrows.search.search.strategy;

import com.sparrows.search.search.model.dto.SearchRequest;
import com.sparrows.search.search.model.dto.SearchResponse;

public interface SearchStrategy<A extends SearchRequest,B extends SearchResponse> {
    B search(A request);
    String getDomain();
}
