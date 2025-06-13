package com.sparrows.search.search.port.in;

import com.sparrows.search.search.model.dto.SaveResponse;
import com.sparrows.search.search.model.dto.SearchRequest;
import com.sparrows.search.search.model.dto.SearchResponse;

public interface SearchUsecase {
    SearchResponse search(SearchRequest request);
    SaveResponse save(String domain, Object request);
}
