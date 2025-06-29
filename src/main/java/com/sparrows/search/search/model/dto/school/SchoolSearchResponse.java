package com.sparrows.search.search.model.dto.school;

import com.sparrows.search.search.model.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolSearchResponse implements SearchResponse {
    List<SchoolDto> schoolDtos;
}
