package com.sparrows.search.search.strategy;

import com.sparrows.search.kafka.properties.KafkaProperties;
import com.sparrows.search.search.config.elasticsearch.ConsonantExtractor;
import com.sparrows.search.search.model.dto.school.SchoolSearchRequest;
import com.sparrows.search.search.model.dto.school.SchoolSearchResponse;
import com.sparrows.search.search.model.entity.ElasticSchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SchoolSearchStrategy implements SearchStrategy<SchoolSearchRequest, SchoolSearchResponse> {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    KafkaProperties kafkaProperties;
    @Override
    public SchoolSearchResponse search(SchoolSearchRequest request) {
        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q ->
                        q.match(m -> m
                                .field("chosung")
                                .query(ConsonantExtractor.getConsonant(request.getQuery()))
                                .analyzer("standard")
                        )
                )
                .build();


        SearchHits<ElasticSchool> schoolSearchHits = elasticsearchOperations.search(nativeQuery, ElasticSchool.class);

        return new SchoolSearchResponse(schoolSearchHits
                .getSearchHits()
                .stream()
                .map(hit-> Integer.parseInt(hit.getContent().getId()))
                .collect(Collectors.toList()));
    }

    @Override
    public String getDomain() {
        return kafkaProperties.getAggregateType().getSchool();
    }
}