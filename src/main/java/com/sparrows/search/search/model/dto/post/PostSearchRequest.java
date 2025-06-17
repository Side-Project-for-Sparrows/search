package com.sparrows.search.search.model.dto.post;

import com.sparrows.search.search.model.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest implements SearchRequest {
    private String query;
    private String domain;
    private Integer boardId;
}
