package com.sparrows.search.search.model.dto.log;

import com.sparrows.search.search.model.dto.SearchResponse;
import com.sparrows.search.search.model.entity.ElasticLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogSearchResponse implements SearchResponse {
    private List<ElasticLog> logs;
}
