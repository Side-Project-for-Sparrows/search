package com.sparrows.search.search.model.dto.board;

import com.sparrows.search.search.model.dto.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchResponse implements SearchResponse {
    List<Long> ids;
}
