package com.sparrows.search.search.model.dto.board;

import com.sparrows.search.search.model.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchRequest implements SearchRequest {
    private String domain;
    private String query;
}
