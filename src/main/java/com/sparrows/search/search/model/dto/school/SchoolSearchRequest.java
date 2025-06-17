package com.sparrows.search.search.model.dto.school;

import com.sparrows.search.search.model.dto.SearchRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSearchRequest implements SearchRequest {
    private String query;
    private String domain;
}
