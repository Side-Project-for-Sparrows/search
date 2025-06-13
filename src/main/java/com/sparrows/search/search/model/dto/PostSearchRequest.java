package com.sparrows.search.search.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest implements SearchRequest{
    private String domain;
    private String query;
    private Integer boardId;
}
