package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.model.dto.log.LogSearchRequest;
import com.sparrows.search.search.model.dto.log.LogSearchResponse;
import com.sparrows.search.search.model.entity.ElasticLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LogSearchStrategy implements SearchStrategy<LogSearchRequest, LogSearchResponse> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final KafkaProperties kafkaProperties;

    @Override
    public LogSearchResponse search(LogSearchRequest request) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.multiMatch(m -> m
                        .fields("traceId", "message", "method")
                        .query(request.getQuery())))
                .build();

        SearchHits<ElasticLog> hits = elasticsearchOperations.search(query, ElasticLog.class);
        return new LogSearchResponse(hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList()));
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getLog();
    }
}
